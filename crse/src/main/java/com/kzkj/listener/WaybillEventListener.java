package com.kzkj.listener;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.eventbus.Subscribe;
import com.kzkj.pojo.po.Waybill;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
import com.kzkj.pojo.vo.request.waybill.CEB607Message;
import com.kzkj.pojo.vo.request.waybill.WayBill;
import com.kzkj.pojo.vo.response.waybill.CEB608Message;
import com.kzkj.pojo.vo.response.waybill.WayBillReturn;
import com.kzkj.service.WaybillService;
import com.kzkj.utils.BeanMapper;
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
            wayBillReturn.setPreNo("123456789");
            wayBillReturn.setBillNo(wayBill.getWayBillHead().getBillNo());
            wayBillReturn.setLogisticsCode(wayBill.getWayBillHead().getLogisticsCode());
            wayBillReturn.setMsgSeqNo(wayBill.getWayBillHead().getMsgSeqNo());
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
        //waybillService.imsertWaybill(event.getWayBill());
        //清单总分单399回执报文
        //returnWaybill399(event);
    }

    /**
     * 清单总分单399回执报文
     * @param event
     */
    private void returnWaybill399(CEB607Message event)
    {
        CEB608Message ceb608Message=new CEB608Message();
        List<WayBillReturn> wayBillReturnsList =new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();
        if(event.getWayBill() == null || event.getWayBill().size() <= 0) return;
        com.kzkj.pojo.vo.request.waybill.WayBillHead wayBillHead = event.getWayBill().get(0).getWayBillHead();
        int msgCount = Integer.parseInt(wayBillHead.getMsgCount());
        int msgSeqNo = Integer.parseInt(wayBillHead.getMsgSeqNo());
        //判断是否是拆分的最后一个报文
        if(msgCount != msgSeqNo) return;
        EntityWrapper<Waybill> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("bill_no", wayBillHead.getBillNo());
        List<Waybill> waybillList = waybillService.selectList(entityWrapper);
        if(waybillList == null || waybillList.size() <= 0) return;
        for(Waybill wb:waybillList)
        {
            WayBillReturn wayBillReturn = new WayBillReturn();
            BeanMapper.map(wb,wayBillReturn);
            String now = sdf.format(new Date());
            wayBillReturn.setReturnTime(now);
            wayBillReturn.setReturnInfo("审核通过["+wb.getGuid()+"]");
            wayBillReturn.setReturnStatus("399");
            wayBillReturnsList.add(wayBillReturn);
        }
        ceb608Message.setWayBillReturn(wayBillReturnsList);
        ceb608Message.setGuid(wayBillReturnsList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb608Message);
        String resultXml=customData(xml, baseTransfer.getDxpId(), "CEB608Message");
        String queue=baseTransfer.getDxpId()+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB608Message");
    }
}
