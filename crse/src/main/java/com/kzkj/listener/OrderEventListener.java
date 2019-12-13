package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.Inventory;
import com.kzkj.pojo.po.Logistics;
import com.kzkj.pojo.po.OrderDetail;
import com.kzkj.pojo.po.Receipts;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.order.CEB303Message;
import com.kzkj.pojo.vo.request.order.Order;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderEventListener extends BaseListener{

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    LogisticsService logisticsService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ReceiptsService receiptsService;

    Logger logger = LoggerFactory.getLogger(OrderEventListener.class);

    @Subscribe
    public void listener(CEB303Message event){
        CEB304Message ceb304Message=new CEB304Message();
        List<OrderReturn> orderReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        List<com.kzkj.pojo.po.Order> orderList= new ArrayList<com.kzkj.pojo.po.Order>();
        List<com.kzkj.pojo.po.Order> updateOrderList= new ArrayList<com.kzkj.pojo.po.Order>();
        Map<String,com.kzkj.pojo.po.Order> map = new HashMap<String,com.kzkj.pojo.po.Order>();
        for(Order order:event.getOrder())
        {
            com.kzkj.pojo.po.Order ord = new com.kzkj.pojo.po.Order();
            OrderReturn orderReturn =new OrderReturn();
            orderReturn.setGuid(order.getOrderHead().getGuid());
            orderReturn.setEbcCode(order.getOrderHead().getEbcCode());
            orderReturn.setEbpCode(order.getOrderHead().getEbpCode());
            orderReturn.setOrderNo(order.getOrderHead().getOrderNo());
            String now = sdf.format(new Date());
            orderReturn.setReturnTime(now);
            ord.setReturnTime(now);
            BeanMapper.map(order.getOrderHead(),ord);
            ord.setOrderDetailList(BeanMapper.mapList(order.getOrderList(),OrderDetail.class));

//            //校验申报订单是否重复
//            if(map.containsKey(ord.getEbpCode()+"_"+ord.getOrderNo()))
//            {
//                orderReturn.setReturnStatus();
//                orderReturn.setReturnInfo("");
//            }
//            map.put(ord.getEbpCode()+"_"+ord.getOrderNo(),ord);

            com.kzkj.pojo.po.Order oldOrder =
                    orderService.getByOrderNoAndEbcCode(order.getOrderHead().getOrderNo(),order.getOrderHead().getEbcCode());
            //数据查重
            if(oldOrder == null)
            {
                //数据校验
                orderReturn = orderService.checkOrder(orderReturn,ord);
                ord.setReturnInfo(orderReturn.getReturnInfo());
                ord.setReturnStatus(orderReturn.getReturnStatus());
                orderList.add(ord);
            }else {
                if(!oldOrder.getReturnStatus().equals("2")
                &&!oldOrder.getReturnStatus().equals("120"))//如果是退单，则重新校验
                {
                    //数据校验
                    orderReturn = orderService.checkOrder(orderReturn,ord);
                    if (orderReturn.getReturnStatus().equals("2"))
                    {
                        ord.setId(oldOrder.getId());
                        ord.setReturnInfo(orderReturn.getReturnInfo());
                        ord.setReturnStatus(orderReturn.getReturnStatus());
                        updateOrderList.add(ord);
                    }
                }else{
                    String msg = "处理失败，业务主键重复入库失败，报文业务主键["
                            + order.getOrderHead().getOrderNo() + "+"
                            + order.getOrderHead().getEbcCode()
                            + "]，原订单报送时间对应状态为["
                            + oldOrder.getReturnTime() + " : "+oldOrder.getReturnStatus()+"申报;]";
                    orderReturn.setReturnInfo(msg);
                    orderReturn.setReturnStatus("-304001");
                    ord.setReturnInfo(msg);
                    ord.setReturnStatus("-304001");
                }
            }
            orderReturnList.add(orderReturn);
        }

        ceb304Message.setOrderReturn(orderReturnList);
        ceb304Message.setGuid(orderReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb304Message);
        logger.info(xml);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB304Message");
        logger.info(resultXml);
        String queue=baseTransfer.getDxpId()+"_HZ";
        logger.info("发送队列:"+queue);
        try {
            mqSender.sendMsg(queue, resultXml,"CEB304Message");
            //插入数据库
            orderService.insertOrders(orderList);
            //修改退单记录
            orderService.batchUpdateOrder(updateOrderList);
            //三单对碰
            orderList.addAll(updateOrderList);
            SDDP(orderList,baseTransfer.getDxpId());
            //收款单120回执
            send120Receipts(orderList,baseTransfer.getDxpId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 订单发起三单对碰
     * @param orderList
     * @param dxpid
     */
    private void SDDP(List<com.kzkj.pojo.po.Order> orderList,String dxpid)
    {
        if (orderList == null || orderList.size() <= 0) return;
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
        List<Logistics> logisticsUpdateList= new ArrayList<Logistics>();
        //清单399
        List<Inventory> inventoryUpdateList= new ArrayList<Inventory>();

        String now = sdf.format(new Date());
        for(com.kzkj.pojo.po.Order order: orderList)
        {
            Inventory inventory = inventoryService.getByOrderNoAndEbcCode(order.getOrderNo(),order.getEbcCode());
            if(inventory == null || !inventory.getReturnStatus().equals("2")) continue;
            Logistics logistics = logisticsService.getByLogisticsCodeAndNo(inventory.getLogisticsCode(),inventory.getLogisticsNo());
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
     * @param orderList
     * @param dxpid
     */
    private void send120Receipts(List<com.kzkj.pojo.po.Order> orderList,String dxpid)
    {
        if (orderList == null || orderList.size() <= 0) return;
        ///收款单120回执
        List<ReceiptsReturn> receipts120ReturnList= new ArrayList<ReceiptsReturn>();
        //收款单120更新
        List<com.kzkj.pojo.po.Receipts> receiptsUpdateList= new ArrayList<com.kzkj.pojo.po.Receipts>();
        String now = sdf.format(new Date());
        for(com.kzkj.pojo.po.Order order: orderList)
        {
            Inventory inventory = inventoryService.getByOrderNoAndEbcCode(order.getOrderNo(),order.getEbcCode());
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
