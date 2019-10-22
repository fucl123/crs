package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.departure.CEB509Message;
import com.kzkj.pojo.vo.request.departure.Departure;
import com.kzkj.pojo.vo.response.departure.CEB510Message;
import com.kzkj.pojo.vo.response.departure.DepartureReturn;
import com.kzkj.utils.XMLUtil;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DepartureEventListener extends BaseListener{

    @Subscribe
    public void listener(CEB509Message event){
        CEB510Message ceb510Message=new CEB510Message();
        List<DepartureReturn> departureReturnList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        for(Departure departure:event.getDeparture())
        {
            DepartureReturn departureReturn =new DepartureReturn();
            departureReturn.setGuid(departure.getDepartureHead().getGuid());
            departureReturn.setCopNo(departure.getDepartureHead().getCopNo());
            departureReturn.setPreNo("");
            departureReturn.setLogisticsCode(departure.getDepartureHead().getLogisticsCode());
            String now = sdf.format(new Date());
            departureReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                departureReturn.setReturnInfo("新增申报成功["+departure.getDepartureHead().getGuid()+"]");
                departureReturn.setReturnStatus("2");
            }else {
                departureReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + departure.getDepartureHead().getCopNo()
                        + "]，原离境单报送时间对应状态为["
                        + now + " : 2-申报;]");
                departureReturn.setReturnStatus("-304001");
            }

            departureReturnList.add(departureReturn);
        }

        ceb510Message.setDepartureReturn(departureReturnList);
        ceb510Message.setGuid(departureReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb510Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB712Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB712Message");
    }
}
