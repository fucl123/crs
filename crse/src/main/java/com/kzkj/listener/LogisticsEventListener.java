package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.Inventory;
import com.kzkj.pojo.po.Receipts;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.logistics.CEB505Message;
import com.kzkj.pojo.vo.request.logistics.Logistics;
import com.kzkj.pojo.vo.response.invt.CEB604Message;
import com.kzkj.pojo.vo.response.invt.InventoryReturn;
import com.kzkj.pojo.vo.response.logistics.CEB506Message;
import com.kzkj.pojo.vo.response.logistics.LogisticsReturn;
import com.kzkj.pojo.vo.response.order.CEB304Message;
import com.kzkj.pojo.vo.response.order.OrderReturn;
import com.kzkj.pojo.vo.response.receipts.CEB404Message;
import com.kzkj.pojo.vo.response.receipts.ReceiptsReturn;
import com.kzkj.service.*;
import com.kzkj.utils.BeanMapper;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class LogisticsEventListener extends BaseListener{

    @Autowired
    LogisticsService logisticsService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    OrderService orderService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ReceiptsService receiptsService;

    @Subscribe
    public void listener(CEB505Message event){
        CEB506Message ceb506Message=new CEB506Message();
        List<LogisticsReturn> logisticsReturnsList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        List<com.kzkj.pojo.po.Logistics> logisticsList= new ArrayList<com.kzkj.pojo.po.Logistics>();
        List<com.kzkj.pojo.po.Logistics> updateLogisticsList= new ArrayList<com.kzkj.pojo.po.Logistics>();

        for(Logistics logistics:event.getLogistics())
        {
            com.kzkj.pojo.po.Logistics logi = new com.kzkj.pojo.po.Logistics();
            LogisticsReturn logisticsReturn =new LogisticsReturn();
            logisticsReturn.setGuid(logistics.getGuid());
            logisticsReturn.setLogisticsCode(logistics.getLogisticsCode());
            logisticsReturn.setLogisticsNo(logistics.getLogisticsNo());
            String now = sdf.format(new Date());
            logisticsReturn.setReturnTime(now);
            logi.setReturnTime(now);
            BeanMapper.map(logistics,logi);
            com.kzkj.pojo.po.Logistics oldLogistics =
                    logisticsService.getByLogisticsCodeAndNo(logistics.getLogisticsCode(),logistics.getLogisticsNo());

            //数据查重
            if(oldLogistics == null)
            {
                //数据校验
                logisticsReturn = logisticsService.checkLogistics(logisticsReturn,logi);
                logi.setReturnStatus(logisticsReturn.getReturnStatus());
                logi.setReturnInfo(logisticsReturn.getReturnInfo());
                logisticsList.add(logi);
            }else {
                if(!oldLogistics.getReturnStatus().equals("2")
                &&!oldLogistics.getReturnStatus().equals("120"))//如果是退单
                {
                    //数据校验
                    logisticsReturn = logisticsService.checkLogistics(logisticsReturn,logi);
                    if (logisticsReturn.getReturnStatus().equals("2"))
                    {
                        logi.setId(oldLogistics.getId());
                        logi.setReturnStatus(logisticsReturn.getReturnStatus());
                        logi.setReturnInfo(logisticsReturn.getReturnInfo());
                        updateLogisticsList.add(logi);
                    }
                }else{
                    logisticsReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                            + logistics.getLogisticsCode() + "+" + logistics.getLogisticsNo()
                            + "]，原运单报送时间对应状态为["
                            + now + " : 2-申报;]");
                    logisticsReturn.setReturnStatus("-304001");
                    logi.setReturnStatus(logisticsReturn.getReturnStatus());
                    logi.setReturnInfo(logisticsReturn.getReturnInfo());
                }
            }
            logisticsReturnsList.add(logisticsReturn);
        }

        ceb506Message.setLogisticsReturn(logisticsReturnsList);
        ceb506Message.setGuid(logisticsReturnsList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb506Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB506Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB506Message");
        //插入数据库
        logisticsService.insertLogistics(logisticsList);
        //更新退单记录
        if (updateLogisticsList != null && updateLogisticsList.size() > 0)
        logisticsService.updateAllColumnBatchById(updateLogisticsList);
        //运单发起三单对碰
        logisticsList.addAll(updateLogisticsList);
        SDDP(logisticsList,baseTransfer.getDxpId());
        //收款单120回执
        send120Receipts(logisticsList,baseTransfer.getDxpId());
    }

    /**
     * 运单发起三单对碰
     * @param logisticsList
     * @param dxpid
     */
    private void SDDP(List<com.kzkj.pojo.po.Logistics> logisticsList,String dxpid)
    {
        if (logisticsList == null || logisticsList.size() <= 0) return;
        //订单120回执
        List<OrderReturn> order120ReturnList= new ArrayList<OrderReturn>();
        //运单120回执
        List<LogisticsReturn> logistics120ReturnList= new ArrayList<LogisticsReturn>();
        //清单120回执
        List<InventoryReturn> inventory120ReturnList= new ArrayList<InventoryReturn>();
        //清单399回执
        List<InventoryReturn> inventory399ReturnList= new ArrayList<InventoryReturn>();

        //订单120
        List<com.kzkj.pojo.po.Order> orderUpdateList= new ArrayList<com.kzkj.pojo.po.Order>();
        //运单120
        List<com.kzkj.pojo.po.Logistics> logisticsUpdateList= new ArrayList<com.kzkj.pojo.po.Logistics>();
        //清单399
        List<Inventory> inventoryUpdateList= new ArrayList<Inventory>();

        String now = sdf.format(new Date());
        for(com.kzkj.pojo.po.Logistics logistics: logisticsList)
        {
            Inventory inventory = inventoryService.getByLogisticsCodeAndNo(logistics.getLogisticsCode(),logistics.getLogisticsNo());
            if(inventory == null || !inventory.getReturnStatus().equals("2")) continue;
            com.kzkj.pojo.po.Order order = orderService.getByOrderNoAndEbcCode(inventory.getOrderNo(),inventory.getEbcCode());
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
            InventoryReturn inventoryReturn = new InventoryReturn();
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
        if (inventoryService.inventory120Update(orderUpdateList,logisticsUpdateList,inventoryUpdateList))
        {
            //发送订单120回执报文
            CEB304Message ceb304Message=new CEB304Message();
            ceb304Message.setOrderReturn(order120ReturnList);
            ceb304Message.setGuid(order120ReturnList.get(0).getGuid());
            String xml= XMLUtil.convertToXml(ceb304Message);
            String resultXml=customData(xml, dxpid, "CEB304Message");
            String queue = dxpid + "_HZ";
            mqSender.sendMsg(queue, resultXml,"CEB304Message");

            //发送运单120回执报文
            CEB506Message ceb506Message=new CEB506Message();
            ceb506Message.setLogisticsReturn(logistics120ReturnList);
            ceb506Message.setGuid(logistics120ReturnList.get(0).getGuid());
            xml= XMLUtil.convertToXml(ceb506Message);
            resultXml=customData(xml, dxpid, "CEB506Message");
            mqSender.sendMsg(queue, resultXml,"CEB506Message");

            //发送清单120回执报文
            CEB604Message ceb604Message=new CEB604Message();
            ceb604Message.setInventoryReturn(inventory120ReturnList);
            ceb604Message.setGuid(inventory120ReturnList.get(0).getGuid());
            xml = XMLUtil.convertToXml(ceb604Message);
            resultXml = customData(xml, dxpid, "CEB604Message");
            mqSender.sendMsg(queue, resultXml,"CEB604Message");

            //发送清单399回执报文
            ceb604Message.setInventoryReturn(inventory399ReturnList);
            ceb604Message.setGuid(inventory399ReturnList.get(0).getGuid());
            xml = XMLUtil.convertToXml(ceb604Message);
            resultXml = customData(xml, dxpid, "CEB604Message");
            mqSender.sendMsg(queue, resultXml,"CEB604Message");
        }
    }

    /**
     * 订运清三单对碰成功后发送收款单120回执
     * @param logisticsList
     * @param dxpid
     */
    private void send120Receipts(List<com.kzkj.pojo.po.Logistics> logisticsList,String dxpid)
    {
        if (logisticsList == null || logisticsList.size() <= 0) return;
        ///收款单120回执
        List<ReceiptsReturn> receipts120ReturnList= new ArrayList<ReceiptsReturn>();
        //收款单120更新
        List<com.kzkj.pojo.po.Receipts> receiptsUpdateList= new ArrayList<com.kzkj.pojo.po.Receipts>();
        String now = sdf.format(new Date());
        for(com.kzkj.pojo.po.Logistics logistics: logisticsList)
        {
            Inventory inventory = inventoryService.getByLogisticsCodeAndNo(logistics.getLogisticsCode(),logistics.getLogisticsNo());
            if(inventory == null || !inventory.getReturnStatus().equals("399")) continue;
            Receipts receipts = receiptsService.getByEbcCodeAndOrderNo(inventory.getEbcCode(),inventory.getOrderNo());
            if(receipts == null || !receipts.getReturnStatus().equals("2")) continue;
            //收款单120回执
            ReceiptsReturn receiptsReturn =new ReceiptsReturn();
            BeanMapper.map(receipts,receiptsReturn);
            receiptsReturn.setReturnInfo("逻辑校验通过["+receipts.getOrderNo()+"+"+receipts.getEbcCode()+"]");
            receiptsReturn.setReturnStatus("120");
            receiptsReturn.setReturnTime(now);
            receipts.setReturnInfo(receiptsReturn.getReturnInfo());
            receipts.setReturnStatus(receiptsReturn.getReturnStatus());
            receipts.setReturnTime(receiptsReturn.getReturnTime());
            receipts120ReturnList.add(receiptsReturn);
            receiptsUpdateList.add(receipts);
        }
        //更新数据库
        if (receiptsUpdateList.size() > 0 && receiptsService.updateBatchById(receiptsUpdateList))
        {
            String queue = dxpid + "_HZ";
            //发送收款单120回执报文
            CEB404Message ceb404Message=new CEB404Message();
            ceb404Message.setReceiptsReturn(receipts120ReturnList);
            ceb404Message.setGuid(receipts120ReturnList.get(0).getGuid());
            String xml = XMLUtil.convertToXml(ceb404Message);
            String resultXml = customData(xml, dxpid, "CEB404Message");
            mqSender.sendMsg(queue, resultXml,"CEB404Message");
        }
    }
}
