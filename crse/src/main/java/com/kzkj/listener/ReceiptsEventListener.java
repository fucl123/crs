package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.receipts.CEB403Message;
import com.kzkj.pojo.vo.request.receipts.Receipts;
import com.kzkj.pojo.vo.response.receipts.CEB404Message;
import com.kzkj.pojo.vo.response.receipts.ReceiptsReturn;
import com.kzkj.utils.XMLUtil;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ReceiptsEventListener extends BaseListener{
    @Subscribe
    public void listener(CEB403Message event){
        CEB404Message ceb404Message=new CEB404Message();
        List<ReceiptsReturn> receiptsReturnsList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        for(Receipts receipts:event.getReceipts())
        {
            ReceiptsReturn receiptsReturn =new ReceiptsReturn();
            receiptsReturn.setGuid(receipts.getGuid());
            receiptsReturn.setEbcCode(receipts.getEbcCode());
            receiptsReturn.setOrderNo(receipts.getOrderNo());
            receiptsReturn.setPayNo(receipts.getPayNo());

            String now = sdf.format(new Date());
            receiptsReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                receiptsReturn.setReturnInfo("新增申报成功["+receipts.getGuid()+"]");
                receiptsReturn.setReturnStatus("2");
            }else {
                receiptsReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + receipts.getEbcCode() + "+" + receipts.getOrderNo()
                        + "]，原收款单报送时间对应状态为["
                        + now + " : 2-申报;]");
                receiptsReturn.setReturnStatus("-304001");
            }

            receiptsReturnsList.add(receiptsReturn);
        }

        ceb404Message.setReceiptsReturn(receiptsReturnsList);
        ceb404Message.setGuid(receiptsReturnsList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb404Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB404Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB404Message");
    }
}
