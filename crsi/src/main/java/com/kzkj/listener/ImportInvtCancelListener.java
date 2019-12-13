package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.request.InvtCancel.CEB623Message;
import com.kzkj.pojo.vo.request.InvtCancel.ImportInvtCancel;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.response.invtCancel.CEB624Message;
import com.kzkj.pojo.vo.response.invtCancel.ImportInvtCancelReturn;
import com.kzkj.service.ImportInvtCancelService;
import com.kzkj.utils.BeanMapper;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ImportInvtCancelListener extends BaseListener{

    @Autowired
    ImportInvtCancelService importInvtCancelService;

    @Subscribe
    public void listener(CEB623Message event){
        CEB624Message ceb624Message=new CEB624Message();
        List<ImportInvtCancelReturn> importInvtCancelReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        List<com.kzkj.pojo.po.ImportInvtCancel> importInvtCancelList= new ArrayList<com.kzkj.pojo.po.ImportInvtCancel>();
        List<com.kzkj.pojo.po.ImportInvtCancel> updateImportInvtCancelList= new ArrayList<com.kzkj.pojo.po.ImportInvtCancel>();
        Map<String, com.kzkj.pojo.po.ImportInvtCancel> map = new HashMap<String, com.kzkj.pojo.po.ImportInvtCancel>();
        for(ImportInvtCancel importInvtCancel : event.getInvtCancel())
        {
            com.kzkj.pojo.po.ImportInvtCancel invtc = new com.kzkj.pojo.po.ImportInvtCancel();
            ImportInvtCancelReturn importInvtCancelReturn =new ImportInvtCancelReturn();
            BeanMapper.map(importInvtCancel,importInvtCancelReturn);
            importInvtCancelReturn.setPreNo("123456");
            String now = sdf.format(new Date());
            importInvtCancelReturn.setReturnTime(now);
            invtc.setReturnTime(now);
            BeanMapper.map(importInvtCancel,invtc);
            if(map.containsKey(invtc.getAgentCode()+"_"+invtc.getCopNo()))
            {
                importInvtCancelReturn.setReturnInfo("重复申报，业务主键["+invtc.getAgentCode()+"+"+invtc.getCopNo()+"]");
                importInvtCancelReturn.setReturnStatus("100");
                importInvtCancelReturnList.add(importInvtCancelReturn);
                continue;
            }
            map.put(invtc.getAgentCode()+"_"+invtc.getCopNo(),invtc);
            com.kzkj.pojo.po.ImportInvtCancel oldImportInvtCancel =
                    importInvtCancelService.getByAgentCodeAndCopNo(invtc.getAgentCode(),invtc.getCopNo());
            //数据查重
            if(oldImportInvtCancel == null)
            {
                //数据校验
                importInvtCancelReturn = importInvtCancelService.checkImportInvtCancel(importInvtCancelReturn,invtc);
                invtc.setReturnStatus(importInvtCancelReturn.getReturnStatus());
                invtc.setReturnInfo(importInvtCancelReturn.getReturnInfo());
                importInvtCancelList.add(invtc);
            }else {
                if(!oldImportInvtCancel.getReturnStatus().equals("2"))//如果是退单
                {
                    //数据校验
                    importInvtCancelReturn = importInvtCancelService.checkImportInvtCancel(importInvtCancelReturn,invtc);
                    if (importInvtCancelReturn.getReturnStatus().equals("2"))
                    {
                        invtc.setId(oldImportInvtCancel.getId());
                        invtc.setReturnStatus(importInvtCancelReturn.getReturnStatus());
                        invtc.setReturnInfo(importInvtCancelReturn.getReturnInfo());
                        updateImportInvtCancelList.add(invtc);
                    }
                }else{
                    importInvtCancelReturn.setReturnInfo("重复申报，业务主键["
                            + invtc.getAgentCode() + "+" + invtc.getCopNo()
                            + "]");
                    importInvtCancelReturn.setReturnStatus("-304001");
                    invtc.setReturnStatus(importInvtCancelReturn.getReturnStatus());
                    invtc.setReturnInfo(importInvtCancelReturn.getReturnInfo());
                }
            }
            importInvtCancelReturnList.add(importInvtCancelReturn);
        }

        ceb624Message.setInvtCancelReturn(importInvtCancelReturnList);
        ceb624Message.setGuid(importInvtCancelReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb624Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB624Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB624Message");
        //插入数据库
        importInvtCancelService.insertBatch(importInvtCancelList);
        //更新退单记录
        if (updateImportInvtCancelList.size() > 0)
            importInvtCancelService.updateAllColumnBatchById(updateImportInvtCancelList);
    }
}
