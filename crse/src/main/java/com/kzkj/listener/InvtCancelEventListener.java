package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.request.InvtCancel.CEB605Message;
import com.kzkj.pojo.vo.request.InvtCancel.InvtCancel;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.response.invtCancel.CEB606Message;
import com.kzkj.pojo.vo.response.invtCancel.InvtCancelReturn;
import com.kzkj.utils.XMLUtil;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InvtCancelEventListener extends BaseListener{
    @Subscribe
    public void listener(CEB605Message event){
        CEB606Message ceb606Message=new CEB606Message();
        List<InvtCancelReturn> invtCancelReturnsList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        for(InvtCancel invtCancel:event.getInvtCancel())
        {
            InvtCancelReturn invtCancelReturn =new InvtCancelReturn();
            invtCancelReturn.setGuid(invtCancel.getGuid());
            invtCancelReturn.setCopNo(invtCancel.getCopNo());
            invtCancelReturn.setAgentCode(invtCancel.getAgentCode());
            invtCancelReturn.setPreNo("123456");
            invtCancelReturn.setInvtNo(invtCancel.getInvtNo());
            invtCancelReturn.setMsgSeqNo("1");
            String now = sdf.format(new Date());
            invtCancelReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                invtCancelReturn.setReturnInfo("新增申报成功["+invtCancel.getGuid()+"]");
                invtCancelReturn.setReturnStatus("2");
            }else {
                invtCancelReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + invtCancel.getCopNo() + "+" + invtCancel.getInvtNo()
                        + "]，原清单撤销单报送时间对应状态为["
                        + now + " : 2-申报;]");
                invtCancelReturn.setReturnStatus("-304001");
            }

            invtCancelReturnsList.add(invtCancelReturn);
        }

        ceb606Message.setInvtCancelReturn(invtCancelReturnsList);
        ceb606Message.setGuid(invtCancelReturnsList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb606Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB606Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB606Message");
    }
}
