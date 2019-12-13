package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.ImportInventory;
import com.kzkj.pojo.po.ImportLogistics;
import com.kzkj.pojo.po.ImportOrder;
import com.kzkj.pojo.po.ImportOrderDetail;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.order.CEB311Message;
import com.kzkj.pojo.vo.request.order.Order;
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
public class ImportOrderListener extends BaseListener{

    @Autowired
    ImportOrderService importOrderService;

    @Autowired
    ImportInventoryService importInventoryService;

    @Autowired
    ImportLogisticsService importLogisticsService;

    @Subscribe
    public void listener(CEB311Message event){
        CEB312Message ceb312Message=new CEB312Message();
        List<OrderReturn> orderReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        List<ImportOrder> importOrderList= new ArrayList<ImportOrder>();
        List<ImportOrder> updateImportOrderList= new ArrayList<ImportOrder>();
        Map<String,ImportOrder> map = new HashMap<String,ImportOrder>();
        for(Order order:event.getOrder())
        {
            ImportOrder ord = new ImportOrder();
            OrderReturn orderReturn =new OrderReturn();
            orderReturn.setGuid(order.getOrderHead().getGuid());
            orderReturn.setEbcCode(order.getOrderHead().getEbcCode());
            orderReturn.setEbpCode(order.getOrderHead().getEbpCode());
            orderReturn.setOrderNo(order.getOrderHead().getOrderNo());
            String now = sdf.format(new Date());
            orderReturn.setReturnTime(now);
            ord.setReturnTime(now);
            BeanMapper.map(order.getOrderHead(),ord);
            ord.setImportOrderDetailList(BeanMapper.mapList(order.getOrderList(), ImportOrderDetail.class));

            if(map.containsKey(ord.getEbcCode()+"_"+ord.getOrderNo()))
            {
                orderReturn.setReturnInfo("重复申报，业务主键["+ord.getEbcCode()+"+"+ord.getOrderNo()+"]");
                orderReturn.setReturnStatus("100");
                orderReturnList.add(orderReturn);
                continue;
            }
            map.put(ord.getEbcCode()+"_"+ord.getOrderNo(),ord);
            ImportOrder oldOrder =
                    importOrderService.getByOrderNoAndEbcCode(order.getOrderHead().getOrderNo(),order.getOrderHead().getEbcCode());
            //数据查重
            if(oldOrder == null)
            {
                //数据校验
                orderReturn = importOrderService.checkOrder(orderReturn,ord);
                ord.setReturnInfo(orderReturn.getReturnInfo());
                ord.setReturnStatus(orderReturn.getReturnStatus());
                importOrderList.add(ord);
            }else {
                if(!oldOrder.getReturnStatus().equals("2")
                        &&!oldOrder.getReturnStatus().equals("120"))//如果是退单，则重新校验
                {
                    //数据校验
                    orderReturn = importOrderService.checkOrder(orderReturn,ord);
                    if (orderReturn.getReturnStatus().equals("2"))
                    {
                        ord.setId(oldOrder.getId());
                        ord.setReturnInfo(orderReturn.getReturnInfo());
                        ord.setReturnStatus(orderReturn.getReturnStatus());
                        updateImportOrderList.add(ord);
                    }
                }else{
                    String msg = "重复申报，业务主键["
                            + order.getOrderHead().getEbcCode() + "+"
                            + order.getOrderHead().getOrderNo() + "]";
                    orderReturn.setReturnInfo(msg);
                    orderReturn.setReturnStatus("100");
                    ord.setReturnInfo(msg);
                    ord.setReturnStatus("100");
                }
            }
            orderReturnList.add(orderReturn);
        }

        ceb312Message.setOrderReturn(orderReturnList);
        ceb312Message.setGuid(orderReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb312Message);
        logger.info(xml);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB312Message");
        logger.info(resultXml);
        String queue=baseTransfer.getDxpId()+"_HZ";
        logger.info("发送队列:"+queue);
        try {
            mqSender.sendMsg(queue, resultXml,"CEB312Message");
            //插入数据库
            importOrderService.insertOrders(importOrderList);
            //修改退单记录
            importOrderService.batchUpdateOrder(updateImportOrderList);
            //三单对碰
            importOrderList.addAll(updateImportOrderList);
            SDDP(importOrderList,baseTransfer.getDxpId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 订单发起三单对碰
     * @param orderList
     * @param dxpid
     */
    private void SDDP(List<ImportOrder> orderList,String dxpid)
    {
        if (orderList == null || orderList.size() <= 0) return;
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
        List<ImportInventory> inventoryUpdateList= new ArrayList<ImportInventory>();

        String now = sdf.format(new Date());
        for(ImportOrder order: orderList)
        {
            ImportInventory inventory = importInventoryService.getByOrderNoAndEbcCode(order.getOrderNo(),order.getEbcCode());
            if(inventory == null || !inventory.getReturnStatus().equals("2")) continue;
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
