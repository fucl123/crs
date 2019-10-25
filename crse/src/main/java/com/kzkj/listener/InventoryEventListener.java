package com.kzkj.listener;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.Logistics;
import com.kzkj.pojo.po.Order;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.invt.CEB603Message;
import com.kzkj.pojo.vo.request.invt.Inventory;
import com.kzkj.pojo.vo.response.invt.CEB604Message;
import com.kzkj.pojo.vo.response.invt.InventoryReturn;
import com.kzkj.pojo.vo.response.logistics.CEB506Message;
import com.kzkj.pojo.vo.response.logistics.LogisticsReturn;
import com.kzkj.pojo.vo.response.order.CEB304Message;
import com.kzkj.pojo.vo.response.order.OrderReturn;
import com.kzkj.service.InventoryService;
import com.kzkj.service.LogisticsService;
import com.kzkj.service.OrderService;
import com.kzkj.utils.BeanMapper;
import com.kzkj.utils.RandomUtil;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Wrapper;
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

    @Subscribe
    public void listener(CEB603Message event){
        CEB604Message ceb604Message=new CEB604Message();
        List<InventoryReturn> inventoryReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        List<String> orderNoList = new ArrayList<>();
        List<String> logisticsNoList = new ArrayList<>();
        for(Inventory inventory:event.getInventory())
        {
            InventoryReturn inventoryReturn =new InventoryReturn();
            inventoryReturn.setGuid(inventory.getInventoryHead().getGuid());
            inventoryReturn.setCopNo(inventory.getInventoryHead().getCopNo());
            inventoryReturn.setAgentCode(inventory.getInventoryHead().getAgentCode());
            inventoryReturn.setPreNo("123456789");
            inventory.getInventoryHead().setPreNo("123456789");
            inventoryReturn.setEbcCode(inventory.getInventoryHead().getEbcCode());
            inventoryReturn.setInvtNo(sdf.format(new Date())+ RandomUtil.genRomdom());
            inventory.getInventoryHead().setInvtNo(inventoryReturn.getInvtNo());
            inventoryReturn.setOrderNo(inventory.getInventoryHead().getOrderNo());
            inventoryReturn.setEbpCode(inventory.getInventoryHead().getEbpCode());
            inventoryReturn.setEbpCode(inventory.getInventoryHead().getEbcCode());

            orderNoList.add(inventory.getInventoryHead().getOrderNo());
            logisticsNoList.add(inventory.getInventoryHead().getLogisticsNo());
            String now = sdf.format(new Date());
            inventoryReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                inventoryReturn.setReturnInfo("新增申报成功["+inventory.getInventoryHead().getGuid()+"]");
                inventoryReturn.setReturnStatus("2");
            }else {
                inventoryReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + inventory.getInventoryHead().getCopNo()
                        + "]，原清单报送时间对应状态为["
                        + now + " : 2-申报;]");
                inventoryReturn.setReturnStatus("-304001");
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
        inventoryService.insertInventorys(event.getInventory());
        //回执订单120
        returnOrder120(orderNoList,baseTransfer.getDxpId());
        //回执运单120
        returnLogistics120(logisticsNoList,baseTransfer.getDxpId());
        //回执清单120
        returnInvt120(event);
        //回执订单399
        //returnOrder399(orderNoList,baseTransfer.getDxpId());
        //回执运单399
        //returnLogistics399(logisticsNoList,baseTransfer.getDxpId());
        //回执清单399
        returnInvt399(event);
    }

    /**
     * 订单逻辑校验通过报文回执
     * @param orderNoList
     * @param dxpId
     */
    public void returnOrder120(List<String> orderNoList,String dxpId)
    {
        if (orderNoList == null || orderNoList.size() <= 0 || StringUtils.isEmpty(dxpId)) return;
        EntityWrapper<Order> ew = new EntityWrapper<>();
        ew.in("order_no",orderNoList);
        List<Order> orderList = orderService.selectList(ew);
        if (orderList == null) return;
        List<OrderReturn> orderReturnList= new ArrayList<OrderReturn>();
        for(Order order: orderList)
        {
            OrderReturn orderReturn = new OrderReturn();
            BeanMapper.map(order,orderReturn);
            orderReturn.setReturnInfo("逻辑校验通过["+orderReturn.getGuid()+"]");
            orderReturn.setReturnStatus("120");
            orderReturn.setReturnTime(sdf.format(new Date()));
            orderReturnList.add(orderReturn);
        }
        CEB304Message ceb304Message=new CEB304Message();
        ceb304Message.setGuid(orderReturnList.get(0).getGuid());
        ceb304Message.setOrderReturn(orderReturnList);
        String xml= XMLUtil.convertToXml(ceb304Message);
        String resultXml=customData(xml, dxpId, "CEB304Message");
        String queue= dxpId+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB304Message");
    }

    /**
     * 订单审核通过报文回执
     * @param orderNoList
     * @param dxpId
     */
    public void returnOrder399(List<String> orderNoList,String dxpId)
    {
        if (orderNoList == null || orderNoList.size() <= 0 || StringUtils.isEmpty(dxpId)) return;
        EntityWrapper<Order> ew = new EntityWrapper<>();
        ew.in("order_no",orderNoList);
        List<Order> orderList = orderService.selectList(ew);
        if (orderList == null) return;
        List<OrderReturn> orderReturnList= new ArrayList<OrderReturn>();
        for(Order order: orderList)
        {
            OrderReturn orderReturn = new OrderReturn();
            BeanMapper.map(order,orderReturn);
            orderReturn.setReturnInfo("审核通过["+orderReturn.getGuid()+"]");
            orderReturn.setReturnStatus("399");
            orderReturn.setReturnTime(sdf.format(new Date()));
            orderReturnList.add(orderReturn);
        }
        CEB304Message ceb304Message=new CEB304Message();
        ceb304Message.setGuid(orderReturnList.get(0).getGuid());
        ceb304Message.setOrderReturn(orderReturnList);
        String xml= XMLUtil.convertToXml(ceb304Message);
        String resultXml=customData(xml, dxpId, "CEB304Message");
        String queue= dxpId+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB304Message");
    }

    /**
     * 运单逻辑校验通过回执报文
     * @param logisticsNoList
     * @param dxpId
     */
    public void returnLogistics120(List<String> logisticsNoList,String dxpId)
    {
        if (logisticsNoList == null || logisticsNoList.size() <= 0 || StringUtils.isEmpty(dxpId)) return;
        EntityWrapper<Logistics> ew = new EntityWrapper<>();
        ew.in("logistics_no",logisticsNoList);
        List<Logistics> logisticsList = logisticsService.selectList(ew);
        if (logisticsNoList == null) return;
        List<LogisticsReturn> logisticsReturnList= new ArrayList<>();
        for(Logistics logistics: logisticsList)
        {
            LogisticsReturn logisticsReturn = new LogisticsReturn();
            BeanMapper.map(logistics,logisticsReturn);
            logisticsReturn.setReturnInfo("逻辑校验通过["+logisticsReturn.getGuid()+"]");
            logisticsReturn.setReturnStatus("120");
            logisticsReturn.setReturnTime(sdf.format(new Date()));
            logisticsReturnList.add(logisticsReturn);
        }
        CEB506Message ceb506Message=new CEB506Message();
        ceb506Message.setGuid(logisticsReturnList.get(0).getGuid());
        ceb506Message.setLogisticsReturn(logisticsReturnList);
        String xml= XMLUtil.convertToXml(ceb506Message);
        String resultXml=customData(xml, dxpId, "CEB506Message");
        String queue= dxpId+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB506Message");
    }
    /**
     * 运单审核通过回执报文
     * @param logisticsNoList
     * @param dxpId
     */
    public void returnLogistics399(List<String> logisticsNoList,String dxpId)
    {
        if (logisticsNoList == null || logisticsNoList.size() <= 0 || StringUtils.isEmpty(dxpId)) return;
        EntityWrapper<Logistics> ew = new EntityWrapper<>();
        ew.in("logistics_no",logisticsNoList);
        List<Logistics> logisticsList = logisticsService.selectList(ew);
        if (logisticsNoList == null) return;
        List<LogisticsReturn> logisticsReturnList= new ArrayList<>();
        for(Logistics logistics: logisticsList)
        {
            LogisticsReturn logisticsReturn = new LogisticsReturn();
            BeanMapper.map(logistics,logisticsReturn);
            logisticsReturn.setReturnInfo("审核通过["+logisticsReturn.getGuid()+"]");
            logisticsReturn.setReturnStatus("399");
            logisticsReturn.setReturnTime(sdf.format(new Date()));
            logisticsReturnList.add(logisticsReturn);
        }
        CEB506Message ceb506Message=new CEB506Message();
        ceb506Message.setGuid(logisticsReturnList.get(0).getGuid());
        ceb506Message.setLogisticsReturn(logisticsReturnList);
        String xml= XMLUtil.convertToXml(ceb506Message);
        String resultXml=customData(xml, dxpId, "CEB506Message");
        String queue= dxpId+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB506Message");
    }

    /**
     * 清单逻辑校验通过回执报文
     * @param event
     */
    public void returnInvt120(CEB603Message event)
    {
        CEB604Message ceb604Message=new CEB604Message();
        List<InventoryReturn> inventoryReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        for(Inventory inventory:event.getInventory())
        {
            InventoryReturn inventoryReturn =new InventoryReturn();
            BeanMapper.map(inventory.getInventoryHead(),inventoryReturn);
            String now = sdf.format(new Date());
            inventoryReturn.setReturnTime(now);
            //逻辑校验
            inventory.getInventoryList();

            boolean flag=true;
            List<Logistics> logisticsList
                    = logisticsService.getByLogisticsNo(inventory.getInventoryHead().getLogisticsNo());
            if (logisticsList == null || logisticsList.size() <= 0) flag = false;
            if(!inventory.getInventoryHead().getLogisticsCode().equals(logisticsList.get(0).getLogisticsCode()))
            {
                inventoryReturn.setReturnInfo("处理失败，清单["+inventory.getInventoryHead().getInvtNo()+"]与运单的物流企业编码不一致;");
                inventoryReturn.setReturnStatus("100");
            }else if (!inventory.getInventoryHead().getLogisticsNo().equals(logisticsList.get(0).getLogisticsNo())){
                inventoryReturn.setReturnInfo("处理失败，清单["+inventory.getInventoryHead().getInvtNo()+"]与运单的物流运单号不一致;");
                inventoryReturn.setReturnStatus("100");
            }else if (!inventory.getInventoryHead().getGrossWeight().equals(logisticsList.get(0).getGrossWeight().toString())){
                inventoryReturn.setReturnInfo("处理失败，清单["+inventory.getInventoryHead().getInvtNo()+"]与运单的重量不一致;");
                inventoryReturn.setReturnStatus("100");
            }else{
                inventoryReturn.setReturnInfo("逻辑校验通过["+inventory.getInventoryHead().getGuid()+"]");
                inventoryReturn.setReturnStatus("120");
            }
            /*if(flag)
            {
                inventoryReturn.setReturnInfo("逻辑校验通过["+inventory.getInventoryHead().getGuid()+"]");
                inventoryReturn.setReturnStatus("120");
            }else {
                inventoryReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + inventory.getInventoryHead().getCopNo()
                        + "]，原清单报送时间对应状态为["
                        + now + " : 2-申报;]");
                inventoryReturn.setReturnStatus("-304001");
            }*/
            inventoryReturnList.add(inventoryReturn);
        }
        ceb604Message.setInventoryReturn(inventoryReturnList);
        ceb604Message.setGuid(inventoryReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb604Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB604Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB604Message");
    }

    /**
     * 清单审核通过回执报文
     * @param event
     */
    public void returnInvt399(CEB603Message event)
    {
        CEB604Message ceb604Message=new CEB604Message();
        List<InventoryReturn> inventoryReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        for(Inventory inventory:event.getInventory())
        {
            InventoryReturn inventoryReturn =new InventoryReturn();
            BeanMapper.map(inventory.getInventoryHead(),inventoryReturn);
            String now = sdf.format(new Date());
            inventoryReturn.setReturnTime(now);
            //审核校验
            boolean flag=true;
            if(flag)
            {
                inventoryReturn.setReturnInfo("审核通过["+inventory.getInventoryHead().getGuid()+"]");
                inventoryReturn.setReturnStatus("399");
            }else {
                inventoryReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + inventory.getInventoryHead().getCopNo()
                        + "]，原清单报送时间对应状态为["
                        + now + " : 2-申报;]");
                inventoryReturn.setReturnStatus("-304001");
            }
            inventoryReturnList.add(inventoryReturn);
        }
        ceb604Message.setInventoryReturn(inventoryReturnList);
        ceb604Message.setGuid(inventoryReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb604Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB604Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB604Message");
    }
}
