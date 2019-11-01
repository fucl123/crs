package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.logistics.CEB511Message;
import com.kzkj.pojo.vo.request.logistics.ImportLogistics;
import com.kzkj.pojo.vo.response.logistics.CEB512Message;
import com.kzkj.pojo.vo.response.logistics.LogisticsReturn;
import com.kzkj.service.ImportLogisticsService;
import com.kzkj.service.ImportOrderService;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class ImportLogisticsListener extends BaseListener{

    @Autowired
    ImportLogisticsService importLogisticsService;

    @Subscribe
    public void listener(CEB511Message event){
        CEB512Message ceb512Message=new CEB512Message();
        List<LogisticsReturn> logisticsReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        for(ImportLogistics importLogistics : event.getLogistics())
        {
            LogisticsReturn logisticsReturn =new LogisticsReturn();
            logisticsReturn.setGuid(importLogistics.getLogisticsHead().getGuid());
            logisticsReturn.setLogisticsNo(importLogistics.getLogisticsHead().getLogisticsNo());
            logisticsReturn.setLogisticsCode(importLogistics.getLogisticsHead().getLogisticsCode());
            String now = sdf.format(new Date());
            logisticsReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                logisticsReturn.setReturnInfo("新增申报成功["+importLogistics.getLogisticsHead().getGuid()+"]");
                logisticsReturn.setReturnStatus("2");
            }else {
                logisticsReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + importLogistics.getLogisticsHead().getLogisticsNo()
                        + "]，原运单报送时间对应状态为["
                        + now + " : 2-申报;]");
                logisticsReturn.setReturnStatus("-304001");
            }

            logisticsReturnList.add(logisticsReturn);
        }

        ceb512Message.setLogisticsReturn(logisticsReturnList);
        ceb512Message.setGuid(logisticsReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb512Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB512Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB512Message");
        //插入数据库
        //importLogisticsService.insertLogistics(event.getLogistics());
    }
}
