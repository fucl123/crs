package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.OrderDetail;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.order.CEB303Message;
import com.kzkj.pojo.vo.request.order.Order;
import com.kzkj.pojo.vo.request.order.OrderHead;
import com.kzkj.pojo.vo.request.order.OrderList;
import com.kzkj.pojo.vo.response.order.CEB304Message;
import com.kzkj.pojo.vo.response.order.OrderReturn;
import com.kzkj.service.OrderDetailService;
import com.kzkj.service.OrderService;
import com.kzkj.utils.BeanMapper;
import com.kzkj.utils.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderEventListener extends BaseListener{

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    Logger logger = LoggerFactory.getLogger(OrderEventListener.class);

    @Subscribe
    public void listener(CEB303Message event){
        CEB304Message ceb304Message=new CEB304Message();
        List<OrderReturn> orderReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        List<OrderHead> orderHeads = new ArrayList<>();
        List<OrderList> orderLists = new ArrayList<>();
        for(Order order:event.getOrder())
        {
            OrderReturn orderReturn =new OrderReturn();
            orderReturn.setGuid(order.getOrderHead().getGuid());
            orderReturn.setEbcCode(order.getOrderHead().getEbcCode());
            orderReturn.setEbpCode(order.getOrderHead().getEbpCode());
            orderReturn.setOrderNo(order.getOrderHead().getOrderNo());
            String now = sdf.format(new Date());
            orderReturn.setReturnTime(now);
            //订单头部
            orderHeads.add(order.getOrderHead());
            //订单表体
            orderLists.addAll(order.getOrderList());
            //数据查重
            boolean flag=true;
            if(flag)
            {
                orderReturn.setReturnInfo("新增申报成功["+order.getOrderHead().getGuid()+"]");
                orderReturn.setReturnStatus("2");
            }else {
                orderReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + order.getOrderHead().getOrderNo() + "+"
                        + order.getOrderHead().getEbcCode()
                        + "]，原订单报送时间对应状态为["
                        + now + " : 2-申报;]");
                orderReturn.setReturnStatus("-304001");
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
            orderService.insertOrders(event.getOrder());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
