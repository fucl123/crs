package com.kzkj.listener;

import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.waybill.CEB607Message;
import com.kzkj.pojo.vo.request.waybill.WayBill;
import com.kzkj.pojo.vo.response.waybill.CEB608Message;
import com.kzkj.pojo.vo.response.waybill.WayBillReturn;
import com.kzkj.service.WaybillService;
import com.kzkj.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class WaybillEventListener extends BaseListener{

    @Autowired
    WaybillService waybillService;

    @Subscribe
    public void listener(CEB607Message event){
        CEB608Message ceb608Message=new CEB608Message();
        List<WayBillReturn> wayBillReturnsList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        for(WayBill wayBill:event.getWayBill())
        {
            WayBillReturn wayBillReturn =new WayBillReturn();
            wayBillReturn.setGuid(wayBill.getWayBillHead().getGuid());
            wayBillReturn.setCopNo(wayBill.getWayBillHead().getCopNo());
            wayBillReturn.setAgentCode(wayBill.getWayBillHead().getCustomsCode());
            wayBillReturn.setPreNo("");
            wayBillReturn.setBillNo(wayBill.getWayBillHead().getBillNo());
            wayBillReturn.setLogisticsCode(wayBill.getWayBillHead().getLogisticsCode());

            String now = sdf.format(new Date());
            wayBillReturn.setReturnTime(now);

            //数据查重
            boolean flag=true;
            if(flag)
            {
                wayBillReturn.setReturnInfo("新增申报成功["+wayBill.getWayBillHead().getGuid()+"]");
                wayBillReturn.setReturnStatus("2");
            }else {
                wayBillReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                        + wayBill.getWayBillHead().getCopNo() + "+" + wayBill.getWayBillHead().getLogisticsCode()
                        + "]，原清单总分单报送时间对应状态为["
                        + now + " : 2-申报;]");
                wayBillReturn.setReturnStatus("-304001");
            }

            wayBillReturnsList.add(wayBillReturn);
        }

        ceb608Message.setWayBillReturn(wayBillReturnsList);
        ceb608Message.setGuid(wayBillReturnsList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb608Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB608Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB608Message");
        //插入数据库
        waybillService.imsertWaybill(event.getWayBill());
    }
}
