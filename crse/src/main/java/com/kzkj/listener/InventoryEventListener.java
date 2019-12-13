package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.InventoryDetail;
import com.kzkj.pojo.po.Logistics;
import com.kzkj.pojo.po.Receipts;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.invt.CEB603Message;
import com.kzkj.pojo.vo.request.invt.Inventory;
import com.kzkj.pojo.vo.response.invt.CEB604Message;
import com.kzkj.pojo.vo.response.invt.InventoryReturn;
import com.kzkj.pojo.vo.response.logistics.CEB506Message;
import com.kzkj.pojo.vo.response.logistics.LogisticsReturn;
import com.kzkj.pojo.vo.response.order.CEB304Message;
import com.kzkj.pojo.vo.response.order.OrderReturn;
import com.kzkj.pojo.vo.response.receipts.CEB404Message;
import com.kzkj.pojo.vo.response.receipts.ReceiptsReturn;
import com.kzkj.service.InventoryService;
import com.kzkj.service.LogisticsService;
import com.kzkj.service.OrderService;
import com.kzkj.service.ReceiptsService;
import com.kzkj.utils.BeanMapper;
import com.kzkj.utils.RandomUtil;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InventoryEventListener extends BaseListener{

    @Autowired
    InventoryService inventoryService;

    @Autowired
    OrderService orderService;

    @Autowired
    LogisticsService logisticsService;

    @Autowired
    ReceiptsService receiptsService;

    @Subscribe
    public void listener(CEB603Message event){
        CEB604Message ceb604Message=new CEB604Message();
        List<InventoryReturn> inventoryReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        //List<String> orderNoList = new ArrayList<>();
        //List<String> logisticsNoList = new ArrayList<>();
        List<com.kzkj.pojo.po.Inventory> inventoryList = new ArrayList<>();
        List<com.kzkj.pojo.po.Inventory> updateInventoryList = new ArrayList<>();
        for(Inventory inventory:event.getInventory())
        {
            InventoryReturn inventoryReturn =new InventoryReturn();
            BeanMapper.map(inventory.getInventoryHead(),inventoryReturn);
            inventoryReturn.setPreNo("123456789");
            inventory.getInventoryHead().setPreNo("123456789");
            inventoryReturn.setInvtNo(sdf.format(new Date())+ RandomUtil.genRomdom());
            inventory.getInventoryHead().setInvtNo(inventoryReturn.getInvtNo());
            //orderNoList.add(inventory.getInventoryHead().getOrderNo());
            //logisticsNoList.add(inventory.getInventoryHead().getLogisticsNo());
            String now = sdf.format(new Date());
            inventoryReturn.setReturnTime(now);

            com.kzkj.pojo.po.Inventory inven = new com.kzkj.pojo.po.Inventory();
            inven.setReturnTime(now);
            BeanMapper.map(inventory.getInventoryHead(),inven);
            inven.setInventoryDetailList(BeanMapper.mapList(inventory.getInventoryList(), InventoryDetail.class));

            //数据查重
            com.kzkj.pojo.po.Inventory oldInventory
                    = inventoryService.getByOrderNoAndEbcCode(
                            inventory.getInventoryHead().getOrderNo(),inventory.getInventoryHead().getEbcCode());
            if(oldInventory == null)
            {
                //数据校验
                inventoryReturn = inventoryService.checkInventory(inventoryReturn,inven);
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
                    inventoryReturn = inventoryService.checkInventory(inventoryReturn,inven);
                    if (inventoryReturn.getReturnStatus().equals("2"))
                    {
                        inven.setId(oldInventory.getId());
                        inven.setReturnStatus(inventoryReturn.getReturnStatus());
                        inven.setReturnInfo(inventoryReturn.getReturnInfo());
                        updateInventoryList.add(inven);
                    }
                }else{
                    inventoryReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                            + inventory.getInventoryHead().getCopNo()
                            + "]，原清单报送时间对应状态为["
                            + now + " : 2-申报;]");
                    inventoryReturn.setReturnStatus("-304001");
                    inven.setReturnStatus(inventoryReturn.getReturnStatus());
                    inven.setReturnInfo(inventoryReturn.getReturnInfo());
                }
            }
            inventoryReturnList.add(inventoryReturn);
        }
        ceb604Message.setInventoryReturn(inventoryReturnList);
        ceb604Message.setGuid(inventoryReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb604Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB604Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB604Message");
        //插入数据库
        inventoryService.insertInventorys(inventoryList);
        //更新退单记录
        inventoryService.batchUpdateInventory(updateInventoryList);
        //清单发起3单对碰
        inventoryList.addAll(updateInventoryList);
        SDDP(inventoryList,baseTransfer.getDxpId());
        //收款单120回执
        send120Receipts(inventoryList,baseTransfer.getDxpId());
    }


    /**
     * 清单发起三单对碰
     * @param inventoryList
     * @param dxpid
     */
    private void SDDP(List<com.kzkj.pojo.po.Inventory> inventoryList,String dxpid)
    {
        if (inventoryList == null || inventoryList.size() <= 0) return;
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
        List<com.kzkj.pojo.po.Inventory> inventoryUpdateList= new ArrayList<com.kzkj.pojo.po.Inventory>();

        String now = sdf.format(new Date());
        for(com.kzkj.pojo.po.Inventory inventory: inventoryList)
        {
            com.kzkj.pojo.po.Order order = orderService.getByOrderNoAndEbcCode(inventory.getOrderNo(),inventory.getEbcCode());
            if(order == null || !order.getReturnStatus().equals("2")) continue;
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
            InventoryReturn inventoryReturn1= new InventoryReturn();
            BeanMapper.map(inventory,inventoryReturn1);
            inventoryReturn1.setReturnTime(now);
            inventoryReturn1.setReturnStatus("399");
            inventoryReturn1.setReturnInfo("海关审结["+inventory.getEbcCode()+"+"+inventory.getOrderNo()+"]");
            inventory.setReturnInfo(inventoryReturn1.getReturnInfo());
            inventory.setReturnStatus(inventoryReturn1.getReturnStatus());
            inventory.setReturnTime(inventoryReturn1.getReturnTime());
            inventory399ReturnList.add(inventoryReturn1);
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
     * @param inventoryList
     * @param dxpid
     */
    private void send120Receipts(List<com.kzkj.pojo.po.Inventory> inventoryList,String dxpid)
    {
        if (inventoryList == null || inventoryList.size() <= 0) return;
        ///收款单120回执
        List<ReceiptsReturn> receipts120ReturnList= new ArrayList<ReceiptsReturn>();
        //收款单120更新
        List<com.kzkj.pojo.po.Receipts> receiptsUpdateList= new ArrayList<com.kzkj.pojo.po.Receipts>();
        String now = sdf.format(new Date());
        for(com.kzkj.pojo.po.Inventory inventory: inventoryList)
        {
            com.kzkj.pojo.po.Inventory invent = inventoryService.selectById(inventory.getId());
            if(invent == null || !invent.getReturnStatus().equals("399")) continue;
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
