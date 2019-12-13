package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.ImportInventory;
import com.kzkj.pojo.po.ImportOrder;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.logistics.CEB511Message;
import com.kzkj.pojo.vo.request.logistics.ImportLogistics;
import com.kzkj.pojo.vo.response.invt.CEB622Message;
import com.kzkj.pojo.vo.response.invt.ImportInventoryReturn;
import com.kzkj.pojo.vo.response.logistics.CEB512Message;
import com.kzkj.pojo.vo.response.logistics.LogisticsReturn;
import com.kzkj.pojo.vo.response.order.CEB312Message;
import com.kzkj.pojo.vo.response.order.OrderReturn;
import com.kzkj.service.ImportInventoryService;
import com.kzkj.service.ImportLogisticsService;
import com.kzkj.service.ImportOrderService;
import com.kzkj.utils.BeanMapper;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ImportLogisticsListener extends BaseListener{

    @Autowired
    ImportLogisticsService importLogisticsService;

    @Autowired
    ImportOrderService importOrderService;

    @Autowired
    ImportInventoryService importInventoryService;

    @Subscribe
    public void listener(CEB511Message event){
        CEB512Message ceb512Message=new CEB512Message();
        List<LogisticsReturn> logisticsReturnsList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        List<com.kzkj.pojo.po.ImportLogistics> logisticsList= new ArrayList<com.kzkj.pojo.po.ImportLogistics>();
        List<com.kzkj.pojo.po.ImportLogistics> updateLogisticsList= new ArrayList<com.kzkj.pojo.po.ImportLogistics>();
        Map<String,com.kzkj.pojo.po.ImportLogistics> map = new HashMap<String,com.kzkj.pojo.po.ImportLogistics>();
        for(ImportLogistics logistics:event.getLogistics())
        {
            com.kzkj.pojo.po.ImportLogistics logi = new com.kzkj.pojo.po.ImportLogistics();
            LogisticsReturn logisticsReturn =new LogisticsReturn();
            logisticsReturn.setGuid(logistics.getLogisticsHead().getGuid());
            logisticsReturn.setLogisticsCode(logistics.getLogisticsHead().getLogisticsCode());
            logisticsReturn.setLogisticsNo(logistics.getLogisticsHead().getLogisticsNo());
            String now = sdf.format(new Date());
            logisticsReturn.setReturnTime(now);
            logi.setReturnTime(now);
            BeanMapper.map(logistics.getLogisticsHead(),logi);
            if(map.containsKey(logi.getLogisticsCode()+"_"+logi.getLogisticsNo()))
            {
                logisticsReturn.setReturnInfo("重复申报，业务主键["+logi.getLogisticsCode()+"+"+logi.getLogisticsNo()+"]");
                logisticsReturn.setReturnStatus("100");
                logisticsReturnsList.add(logisticsReturn);
                continue;
            }
            map.put(logi.getLogisticsCode()+"_"+logi.getLogisticsNo(),logi);
            com.kzkj.pojo.po.ImportLogistics oldLogistics =
                    importLogisticsService.getByLogisticsCodeAndNo(logistics.getLogisticsHead().getLogisticsCode(),logistics.getLogisticsHead().getLogisticsNo());

            //数据查重
            if(oldLogistics == null)
            {
                //数据校验
                logisticsReturn = importLogisticsService.checkLogistics(logisticsReturn,logi);
                logi.setReturnStatus(logisticsReturn.getReturnStatus());
                logi.setReturnInfo(logisticsReturn.getReturnInfo());
                logisticsList.add(logi);
            }else {
                if(!oldLogistics.getReturnStatus().equals("2")
                &&!oldLogistics.getReturnStatus().equals("120"))//如果是退单
                {
                    //数据校验
                    logisticsReturn = importLogisticsService.checkLogistics(logisticsReturn,logi);
                    if (logisticsReturn.getReturnStatus().equals("2"))
                    {
                        logi.setId(oldLogistics.getId());
                        logi.setReturnStatus(logisticsReturn.getReturnStatus());
                        logi.setReturnInfo(logisticsReturn.getReturnInfo());
                        updateLogisticsList.add(logi);
                    }
                }else{
                    logisticsReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                            + logistics.getLogisticsHead().getLogisticsCode() + "+" + logistics.getLogisticsHead().getLogisticsNo()
                            + "]，原运单报送时间对应状态为["
                            + now + " : 2-申报;]");
                    logisticsReturn.setReturnStatus("-304001");
                    logi.setReturnStatus(logisticsReturn.getReturnStatus());
                    logi.setReturnInfo(logisticsReturn.getReturnInfo());
                }
            }
            logisticsReturnsList.add(logisticsReturn);
        }

        ceb512Message.setLogisticsReturn(logisticsReturnsList);
        ceb512Message.setGuid(logisticsReturnsList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb512Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB512Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB512Message");
        //插入数据库
        importLogisticsService.insertLogistics(logisticsList);
        //更新退单记录
        if (updateLogisticsList != null && updateLogisticsList.size() > 0)
            importLogisticsService.updateAllColumnBatchById(updateLogisticsList);
        //运单发起三单对碰
        logisticsList.addAll(updateLogisticsList);
        SDDP(logisticsList,baseTransfer.getDxpId());
    }

    /**
     * 运单发起三单对碰
     * @param logisticsList
     * @param dxpid
     */
    private void SDDP(List<com.kzkj.pojo.po.ImportLogistics> logisticsList,String dxpid)
    {
        if (logisticsList == null || logisticsList.size() <= 0) return;
        //订单120回执
        List<OrderReturn> order120ReturnList= new ArrayList<OrderReturn>();
        //运单120回执
        List<LogisticsReturn> logistics120ReturnList= new ArrayList<LogisticsReturn>();
        //清单120回执
        List<ImportInventoryReturn> inventory120ReturnList= new ArrayList<ImportInventoryReturn>();
        //清单399回执
        List<ImportInventoryReturn> inventory399ReturnList= new ArrayList<ImportInventoryReturn>();

        //订单120
        List<ImportOrder> orderUpdateList= new ArrayList<ImportOrder>();
        //运单120
        List<com.kzkj.pojo.po.ImportLogistics> logisticsUpdateList= new ArrayList<com.kzkj.pojo.po.ImportLogistics>();
        //清单399
        List<ImportInventory> inventoryUpdateList= new ArrayList<ImportInventory>();

        String now = sdf.format(new Date());
        for(com.kzkj.pojo.po.ImportLogistics logistics: logisticsList)
        {
            ImportInventory inventory = importInventoryService.getByLogisticsCodeAndNo(logistics.getLogisticsCode(),logistics.getLogisticsNo());
            if(inventory == null || !inventory.getReturnStatus().equals("2")) continue;
            ImportOrder order = importOrderService.getByOrderNoAndEbcCode(inventory.getOrderNo(),inventory.getEbcCode());
            if(order == null || !order.getReturnStatus().equals("2")) continue;
            //订单120回执
            OrderReturn orderReturn = new OrderReturn();
            BeanMapper.map(order,orderReturn);
            orderReturn.setReturnTime(now);
            orderReturn.setReturnStatus("120");
            orderReturn.setReturnInfo("逻辑校验通过["+order.getOrderNo()+"+"+order.getEbcCode()+"]");
            order.setReturnInfo(orderReturn.getReturnInfo());
            order.setReturnStatus(orderReturn.getReturnStatus());
            order.setReturnTime(orderReturn.getReturnTime());
            order120ReturnList.add(orderReturn);
            orderUpdateList.add(order);
            //运单120回执
            LogisticsReturn logisticsReturn = new LogisticsReturn();
            BeanMapper.map(logistics,logisticsReturn);
            logisticsReturn.setReturnTime(now);
            logisticsReturn.setReturnStatus("120");
            logisticsReturn.setReturnInfo("逻辑校验通过["+logistics.getLogisticsCode()+"+"+logistics.getLogisticsNo()+"]");
            logistics.setReturnInfo(logisticsReturn.getReturnInfo());
            logistics.setReturnStatus(logisticsReturn.getReturnStatus());
            logistics.setReturnTime(logisticsReturn.getReturnTime());
            logistics120ReturnList.add(logisticsReturn);
            logisticsUpdateList.add(logistics);
            //清单120回执
            ImportInventoryReturn inventoryReturn = new ImportInventoryReturn();
            BeanMapper.map(inventory,inventoryReturn);
            inventoryReturn.setReturnTime(now);
            inventoryReturn.setReturnStatus("120");
            inventoryReturn.setReturnInfo("逻辑校验通过["+inventory.getEbcCode()+"+"+inventory.getOrderNo()+"]");
            inventory120ReturnList.add(inventoryReturn);
            //清单399回执
            inventoryReturn.setReturnStatus("399");
            inventoryReturn.setReturnInfo("海关审结["+inventory.getEbcCode()+"+"+inventory.getOrderNo()+"]");
            inventory.setReturnInfo(inventoryReturn.getReturnInfo());
            inventory.setReturnStatus(inventoryReturn.getReturnStatus());
            inventory.setReturnTime(inventoryReturn.getReturnTime());
            inventory399ReturnList.add(inventoryReturn);
            inventoryUpdateList.add(inventory);
        }
        //更新数据库
        if (importInventoryService.inventory120Update(orderUpdateList,logisticsUpdateList,inventoryUpdateList))
        {
            //发送订单120回执报文
            CEB312Message ceb312Message=new CEB312Message();
            ceb312Message.setOrderReturn(order120ReturnList);
            ceb312Message.setGuid(order120ReturnList.get(0).getGuid());
            String xml= XMLUtil.convertToXml(ceb312Message);
            String resultXml=customData(xml, dxpid, "CEB312Message");
            String queue = dxpid + "_HZ";
            mqSender.sendMsg(queue, resultXml,"CEB312Message");

            //发送运单120回执报文
            CEB512Message ceb512Message=new CEB512Message();
            ceb512Message.setLogisticsReturn(logistics120ReturnList);
            ceb512Message.setGuid(logistics120ReturnList.get(0).getGuid());
            xml= XMLUtil.convertToXml(ceb512Message);
            resultXml=customData(xml, dxpid, "CEB512Message");
            mqSender.sendMsg(queue, resultXml,"CEB512Message");

            //发送清单120回执报文
            CEB622Message ceb622Message=new CEB622Message();
            ceb622Message.setInventoryReturn(inventory120ReturnList);
            ceb622Message.setGuid(inventory120ReturnList.get(0).getGuid());
            xml = XMLUtil.convertToXml(ceb622Message);
            resultXml = customData(xml, dxpid, "CEB622Message");
            mqSender.sendMsg(queue, resultXml,"CEB622Message");

            //发送清单399回执报文
            ceb622Message.setInventoryReturn(inventory399ReturnList);
            ceb622Message.setGuid(inventory399ReturnList.get(0).getGuid());
            xml = XMLUtil.convertToXml(ceb622Message);
            resultXml = customData(xml, dxpid, "CEB622Message");
            mqSender.sendMsg(queue, resultXml,"CEB622Message");
        }
    }
}
