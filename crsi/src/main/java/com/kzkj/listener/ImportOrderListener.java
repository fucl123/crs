package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.order.CEB311Message;
import com.kzkj.pojo.vo.request.order.Order;
import com.kzkj.pojo.vo.response.order.CEB312Message;
import com.kzkj.pojo.vo.response.order.OrderReturn;
import com.kzkj.service.ImportOrderService;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class ImportOrderListener extends BaseListener{

    @Autowired
    ImportOrderService importOrderService;

    @Subscribe
    public void listener(CEB311Message event){
        CEB312Message ceb312Message=new CEB312Message();
        List<OrderReturn> orderReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        for(Order order:event.getOrder())
        {
            OrderReturn orderReturn =new OrderReturn();
            orderReturn.setGuid(order.getOrderHead().getGuid());
            orderReturn.setEbcCode(order.getOrderHead().getEbcCode());
            orderReturn.setEbpCode(order.getOrderHead().getEbpCode());
            orderReturn.setOrderNo(order.getOrderHead().getOrderNo());
            String now = sdf.format(new Date());
            orderReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                orderReturn.setReturnInfo("新增申报成功["+order.getOrderHead().getGuid()+"]");
                orderReturn.setReturnStatus("2");
            }else {
                orderReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + order.getOrderHead().getEbcCode()
                        + "]，原订单报送时间对应状态为["
                        + now + " : 2-申报;]");
                orderReturn.setReturnStatus("-304001");
            }

            orderReturnList.add(orderReturn);
        }
        ceb312Message.setOrderReturn(orderReturnList);
        ceb312Message.setGuid(orderReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb312Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB312Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB312Message");

        //插入数据库
        //importOrderService.insertOrders(event.getOrder());
    }
}
