package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.mapper.ArrivalMapper;
import com.kzkj.pojo.vo.request.arrival.Arrival;
import com.kzkj.pojo.vo.request.arrival.CEB507Message;
import com.kzkj.pojo.vo.response.arrival.ArrivalReturn;
import com.kzkj.pojo.vo.response.arrival.CEB508Message;
import com.kzkj.service.ArrivalService;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ArrivalEventListener extends BaseListener{

    @Autowired
    ArrivalService arrivalService;

    @Subscribe
    public void listener(CEB507Message event){
        CEB508Message ceb508Message = new CEB508Message();
        List<ArrivalReturn> arrivalReturnList = new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();
        for(Arrival arrival:event.getArrival())
        {
            ArrivalReturn arrivalReturn =new ArrivalReturn();
            arrivalReturn.setGuid(arrival.getArrivalHead().getGuid());
            arrivalReturn.setCopNo(arrival.getArrivalHead().getCopNo());
            arrivalReturn.setPreNo("");
            arrivalReturn.setBillNo(arrival.getArrivalHead().getBillNo());
            arrivalReturn.setLogisticsCode(arrival.getArrivalHead().getLogisticsCode());
            arrivalReturn.setOperatorCode(arrival.getArrivalHead().getOperatorCode());
            String now = sdf.format(new Date());
            arrivalReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                arrivalReturn.setReturnInfo("新增申报成功["+arrival.getArrivalHead().getGuid()+"]");
                arrivalReturn.setReturnStatus("2");
            }else {
                arrivalReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + arrival.getArrivalHead().getCopNo()
                        + "]，原运抵单报送时间对应状态为["
                        + now + " : 2-申报;]");
                arrivalReturn.setReturnStatus("-304001");
            }

            arrivalReturnList.add(arrivalReturn);
        }
        ceb508Message.setArrivalReturn(arrivalReturnList);
        ceb508Message.setGuid(arrivalReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb508Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB508Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB508Message");

        //插入数据库
        arrivalService.insertArrival(event.getArrival());
    }
}
