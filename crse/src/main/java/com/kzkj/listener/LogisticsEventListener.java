package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.logistics.CEB505Message;
import com.kzkj.pojo.vo.request.logistics.Logistics;
import com.kzkj.pojo.vo.response.logistics.CEB506Message;
import com.kzkj.pojo.vo.response.logistics.LogisticsReturn;
import com.kzkj.service.LogisticsService;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class LogisticsEventListener extends BaseListener{

    @Autowired
    LogisticsService logisticsService;

    @Subscribe
    public void listener(CEB505Message event){
        CEB506Message ceb506Message=new CEB506Message();
        List<LogisticsReturn> logisticsReturnsList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        for(Logistics logistics:event.getLogistics())
        {
            LogisticsReturn logisticsReturn =new LogisticsReturn();
            logisticsReturn.setGuid(logisticsReturn.getGuid());
            logisticsReturn.setLogisticsCode(logisticsReturn.getLogisticsCode());
            logisticsReturn.setLogisticsNo(logisticsReturn.getLogisticsNo());
            String now = sdf.format(new Date());
            logisticsReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                logisticsReturn.setReturnInfo("新增申报成功["+logistics.getGuid()+"]");
                logisticsReturn.setReturnStatus("2");
            }else {
                logisticsReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + logistics.getEbcCode() + "+" + logistics.getLogisticsNo()
                        + "]，原运单报送时间对应状态为["
                        + now + " : 2-申报;]");
                logisticsReturn.setReturnStatus("-304001");
            }

            logisticsReturnsList.add(logisticsReturn);
        }

        ceb506Message.setLogisticsReturn(logisticsReturnsList);
        ceb506Message.setGuid(logisticsReturnsList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb506Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB506Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB506Message");
        //插入数据库
        logisticsService.insertLogistics(event.getLogistics());
    }
}
