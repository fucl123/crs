package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.ImportInventoryDetail;
import com.kzkj.pojo.po.ImportLogistics;
import com.kzkj.pojo.po.ImportOrder;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.invt.CEB621Message;
import com.kzkj.pojo.vo.request.invt.ImportInventory;
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
import com.kzkj.utils.RandomUtil;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ImportInventoryListener extends BaseListener{

    @Autowired
    ImportInventoryService importInventoryService;

    @Autowired
    ImportOrderService importOrderService;

    @Autowired
    ImportLogisticsService importLogisticsService;

    @Subscribe
    public void listener(CEB621Message event){
        CEB622Message ceb622Message=new CEB622Message();
        List<ImportInventoryReturn> inventoryReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        List<com.kzkj.pojo.po.ImportInventory> inventoryList = new ArrayList<>();
        List<com.kzkj.pojo.po.ImportInventory> updateInventoryList = new ArrayList<>();
        Map<String,com.kzkj.pojo.po.ImportInventory> map = new HashMap<String,com.kzkj.pojo.po.ImportInventory>();
        for(ImportInventory inventory : event.getInventory())
        {
            ImportInventoryReturn inventoryReturn =new ImportInventoryReturn();
            BeanMapper.map(inventory.getInventoryHead(),inventoryReturn);
            inventoryReturn.setPreNo("123456789");
            inventory.getInventoryHead().setPreNo("123456789");
            inventoryReturn.setInvtNo(sdf.format(new Date())+ RandomUtil.genRomdom());
            inventory.getInventoryHead().setInvtNo(inventoryReturn.getInvtNo());
            String now = sdf.format(new Date());
            inventoryReturn.setReturnTime(now);

            com.kzkj.pojo.po.ImportInventory inven = new com.kzkj.pojo.po.ImportInventory();
            inven.setReturnTime(now);
            BeanMapper.map(inventory.getInventoryHead(),inven);
            inven.setImportInventoryDetailList(BeanMapper.mapList(inventory.getInventoryList(), ImportInventoryDetail.class));

            if(map.containsKey(inven.getEbcCode()+"_"+inven.getOrderNo()))
            {
                inventoryReturn.setReturnInfo("重复申报，业务主键["+inven.getEbcCode()+"_"+inven.getOrderNo()+"]");
                inventoryReturn.setReturnStatus("100");
                inventoryReturnList.add(inventoryReturn);
                continue;
            }
            map.put(inven.getEbcCode()+"_"+inven.getOrderNo(),inven);
            //数据查重
            com.kzkj.pojo.po.ImportInventory oldInventory
                    = importInventoryService.getByOrderNoAndEbcCode(
                    inventory.getInventoryHead().getOrderNo(),inventory.getInventoryHead().getEbcCode());
            if(oldInventory == null)
            {
                //数据校验
                inventoryReturn = importInventoryService.checkInventory(inventoryReturn,inven);
                inven.setReturnStatus(inventoryReturn.getReturnStatus());
                inven.setReturnInfo(inventoryReturn.getReturnInfo());
                inventoryList.add(inven);
            }else {
                if(!oldInventory.getReturnStatus().equals("2")
                        &&!oldInventory.getReturnStatus().equals("120")
                        &&!oldInventory.getReturnStatus().equals("399")
                        &&!oldInventory.getReturnStatus().equals("899"))
                {
                    //数据校验
                    inventoryReturn = importInventoryService.checkInventory(inventoryReturn,inven);
                    if (inventoryReturn.getReturnStatus().equals("2"))
                    {
                        inven.setId(oldInventory.getId());
                        inven.setReturnStatus(inventoryReturn.getReturnStatus());
                        inven.setReturnInfo(inventoryReturn.getReturnInfo());
                        updateInventoryList.add(inven);
                    }
                }else{
                    inventoryReturn.setReturnInfo("重复申报，业务主键["
                            + inventory.getInventoryHead().getEbcCode()
                            +"+"+inventory.getInventoryHead().getOrderNo()
                            + "]");
                    inventoryReturn.setReturnStatus("100");
                    inven.setReturnStatus(inventoryReturn.getReturnStatus());
                    inven.setReturnInfo(inventoryReturn.getReturnInfo());
                }
            }
            inventoryReturnList.add(inventoryReturn);
        }
        ceb622Message.setInventoryReturn(inventoryReturnList);
        ceb622Message.setGuid(inventoryReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb622Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB622Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB622Message");
        //插入数据库
        importInventoryService.insertInventorys(inventoryList);
        //更新退单记录
        importInventoryService.batchUpdateInventory(updateInventoryList);
        //清单发起3单对碰
        inventoryList.addAll(updateInventoryList);
        SDDP(inventoryList,baseTransfer.getDxpId());
    }


    /**
     * 清单发起三单对碰
     * @param inventoryList
     * @param dxpid
     */
    private void SDDP(List<com.kzkj.pojo.po.ImportInventory> inventoryList,String dxpid)
    {
        if (inventoryList == null || inventoryList.size() <= 0) return;
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
        List<ImportLogistics> logisticsUpdateList= new ArrayList<ImportLogistics>();
        //清单399
        List<com.kzkj.pojo.po.ImportInventory> inventoryUpdateList= new ArrayList<com.kzkj.pojo.po.ImportInventory>();

        String now = sdf.format(new Date());
        for(com.kzkj.pojo.po.ImportInventory inventory: inventoryList)
        {
            ImportOrder order = importOrderService.getByOrderNoAndEbcCode(inventory.getOrderNo(),inventory.getEbcCode());
            if(order == null || !order.getReturnStatus().equals("2")) continue;
            ImportLogistics logistics = importLogisticsService.getByLogisticsCodeAndNo(inventory.getLogisticsCode(),inventory.getLogisticsNo());
            if(logistics == null || !logistics.getReturnStatus().equals("2")) continue;
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
