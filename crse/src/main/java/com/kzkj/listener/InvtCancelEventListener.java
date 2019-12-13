package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.request.InvtCancel.CEB605Message;
import com.kzkj.pojo.vo.request.InvtCancel.InvtCancel;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.logistics.CEB505Message;
import com.kzkj.pojo.vo.request.logistics.Logistics;
import com.kzkj.pojo.vo.response.invtCancel.CEB606Message;
import com.kzkj.pojo.vo.response.invtCancel.InvtCancelReturn;
import com.kzkj.pojo.vo.response.logistics.CEB506Message;
import com.kzkj.pojo.vo.response.logistics.LogisticsReturn;
import com.kzkj.service.InvtCancelService;
import com.kzkj.utils.BeanMapper;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InvtCancelEventListener extends BaseListener{

    @Autowired
    InvtCancelService invtCancelService;

    @Subscribe
    public void listener(CEB605Message event){
        CEB606Message ceb606Message=new CEB606Message();
        List<InvtCancelReturn> invtCancelReturnsList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        List<com.kzkj.pojo.po.InvtCancel> invtCancelList= new ArrayList<com.kzkj.pojo.po.InvtCancel>();
        List<com.kzkj.pojo.po.InvtCancel> updateInvtCancelList= new ArrayList<com.kzkj.pojo.po.InvtCancel>();

        for(InvtCancel invtCancel:event.getInvtCancel())
        {
            com.kzkj.pojo.po.InvtCancel invt = new com.kzkj.pojo.po.InvtCancel();
            InvtCancelReturn invtCancelReturn =new InvtCancelReturn();
            BeanMapper.map(invtCancel,invtCancelReturn);
            invtCancelReturn.setPreNo("123456");
            invtCancelReturn.setMsgSeqNo("1");
            String now = sdf.format(new Date());
            invtCancelReturn.setReturnTime(now);
            invt.setReturnTime(now);
            BeanMapper.map(invtCancel,invt);
            com.kzkj.pojo.po.InvtCancel oldInvtCancel =
                    invtCancelService.getByAgentCodeAndCopNo(invt.getAgentCode(),invt.getCopNo());

            //数据查重
            if(oldInvtCancel == null)
            {
                //数据校验
                invtCancelReturn = invtCancelService.checkInvtCancel(invtCancelReturn,invt);
                invt.setReturnStatus(invtCancelReturn.getReturnStatus());
                invt.setReturnInfo(invtCancelReturn.getReturnInfo());
                invtCancelList.add(invt);
            }else {
                if(!oldInvtCancel.getReturnStatus().equals("2"))//如果是退单
                {
                    //数据校验
                    invtCancelReturn = invtCancelService.checkInvtCancel(invtCancelReturn,invt);
                    if (invtCancelReturn.getReturnStatus().equals("2"))
                    {
                        invt.setId(oldInvtCancel.getId());
                        invt.setReturnStatus(invtCancelReturn.getReturnStatus());
                        invt.setReturnInfo(invtCancelReturn.getReturnInfo());
                        updateInvtCancelList.add(invt);
                    }
                }else{
                    invtCancelReturn.setReturnInfo("重复申报，业务主键["
                            + invt.getAgentCode() + "+" + invt.getCopNo()
                            + "]");
                    invtCancelReturn.setReturnStatus("100");
                    invt.setReturnStatus(invtCancelReturn.getReturnStatus());
                    invt.setReturnInfo(invtCancelReturn.getReturnInfo());
                }
            }
            invtCancelReturnsList.add(invtCancelReturn);
        }

        ceb606Message.setInvtCancelReturn(invtCancelReturnsList);
        ceb606Message.setGuid(invtCancelReturnsList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb606Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB606Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB606Message");
        //插入数据库
        if (invtCancelList.size() > 0)
        invtCancelService.insertBatch(invtCancelList);
        //更新退单记录
        if (updateInvtCancelList.size() > 0)
            invtCancelService.updateAllColumnBatchById(updateInvtCancelList);

    }
}
