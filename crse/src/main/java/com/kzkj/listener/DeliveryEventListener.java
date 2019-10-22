package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.delivery.CEB711Message;
import com.kzkj.pojo.vo.request.delivery.Delivery;
import com.kzkj.pojo.vo.response.delivery.CEB712Message;
import com.kzkj.pojo.vo.response.delivery.DeliveryReturn;
import com.kzkj.utils.XMLUtil;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DeliveryEventListener extends BaseListener{
    @Subscribe
    public void listener(CEB711Message event){
        CEB712Message ceb712Message=new CEB712Message();
        List<DeliveryReturn> deliveryReturnsList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        for(Delivery delivery:event.getDelivery())
        {
            DeliveryReturn deliveryReturn =new DeliveryReturn();
            deliveryReturn.setGuid(delivery.getDeliveryHead().getGuid());
            deliveryReturn.setCopNo(delivery.getDeliveryHead().getCopNo());
            deliveryReturn.setCustomsCode(delivery.getDeliveryHead().getCustomsCode());
            deliveryReturn.setPreNo(delivery.getDeliveryHead().getPreNo());
            deliveryReturn.setRkdNo(delivery.getDeliveryHead().getRkdNo());
            deliveryReturn.setOperatorCode(delivery.getDeliveryHead().getOperatorCode());
            String now = sdf.format(new Date());
            deliveryReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                deliveryReturn.setReturnInfo("新增申报成功["+delivery.getDeliveryHead().getGuid()+"]");
                deliveryReturn.setReturnStatus("2");
            }else {
                deliveryReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + delivery.getDeliveryHead().getCopNo()
                        + "]，原订单报送时间对应状态为["
                        + now + " : 2-申报;]");
                deliveryReturn.setReturnStatus("-304001");
            }

            deliveryReturnsList.add(deliveryReturn);
        }

        ceb712Message.setDeliveryReturn(deliveryReturnsList);
        ceb712Message.setGuid(deliveryReturnsList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb712Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB712Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB712Message");
    }
}
