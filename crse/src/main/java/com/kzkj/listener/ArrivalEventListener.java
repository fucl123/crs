package com.kzkj.listener;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.eventbus.Subscribe;
import com.kzkj.mapper.ArrivalMapper;
import com.kzkj.pojo.po.Waybill;
import com.kzkj.pojo.vo.request.arrival.Arrival;
import com.kzkj.pojo.vo.request.arrival.CEB507Message;
import com.kzkj.pojo.vo.response.arrival.ArrivalReturn;
import com.kzkj.pojo.vo.response.arrival.CEB508Message;
import com.kzkj.pojo.vo.response.waybill.CEB608Message;
import com.kzkj.pojo.vo.response.waybill.WayBillReturn;
import com.kzkj.service.ArrivalService;
import com.kzkj.service.WaybillService;
import com.kzkj.utils.BeanMapper;
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

    @Autowired
    WaybillService waybillService;

    @Subscribe
    public void listener(CEB507Message event){
        CEB508Message ceb508Message = new CEB508Message();
        List<ArrivalReturn> arrivalReturnList = new ArrayList<>();
        BaseTransfer baseTransfer=event.getBaseTransfer();

        if(event.getArrival() == null || event.getArrival().size() <= 0) return;
        com.kzkj.pojo.vo.request.arrival.ArrivalHead arrivalHead = event.getArrival().get(0).getArrivalHead();
        int msgCount = arrivalHead.getMsgCount();
        int msgSeqNo = Integer.parseInt(arrivalHead.getMsgSeqNo());

        List<com.kzkj.pojo.po.Arrival> arrivalList= new ArrayList<com.kzkj.pojo.po.Arrival>();
        List<com.kzkj.pojo.po.Arrival> updateArrivalList= new ArrayList<com.kzkj.pojo.po.Arrival>();
        for(Arrival arrival:event.getArrival())
        {
            com.kzkj.pojo.po.Arrival arri = new com.kzkj.pojo.po.Arrival();
            ArrivalReturn arrivalReturn =new ArrivalReturn();
            BeanMapper.map(arrival.getArrivalHead(),arrivalReturn);
            arrivalReturn.setPreNo("123456789");
            String now = sdf.format(new Date());
            arrivalReturn.setReturnTime(now);
            BeanMapper.map(arrival.getArrivalHead(),arri);
            arri.setReturnTime(now);

            //数据查重
            com.kzkj.pojo.po.Arrival oldArrival =
                    arrivalService.getByOperatorCodeAndCopNo(
                            arrival.getArrivalHead().getOperatorCode(),arrival.getArrivalHead().getCopNo());

            if(oldArrival == null)
            {
                arrivalReturn = arrivalService.checkArrival(arrivalReturn,arri);
                arrivalReturn.setReturnInfo("新增申报成功["+arrival.getArrivalHead().getGuid()+"]");
                arrivalReturn.setReturnStatus("2");
                arri.setReturnStatus(arrivalReturn.getReturnStatus());
                arri.setReturnInfo(arrivalReturn.getReturnInfo());
                arrivalList.add(arri);
            }else {
                if (!oldArrival.getReturnStatus().equals("2")
                       &&!oldArrival.getReturnStatus().equals("399"))
                {
                    arrivalReturn = arrivalService.checkArrival(arrivalReturn,arri);
                    if (arrivalReturn.getReturnStatus().equals("2"))
                    {
                        arri.setId(oldArrival.getId());
                        arri.setReturnInfo(arrivalReturn.getReturnInfo());
                        arri.setReturnStatus(arrivalReturn.getReturnStatus());
                        updateArrivalList.add(arri);
                    }
                }else{
                    arrivalReturn.setReturnInfo("处理失败，业务主键重复入库失败，报文业务主键["
                            + arrival.getArrivalHead().getCopNo()
                            + "]，原运抵单报送时间对应状态为["
                            + now + " : 2-申报;]");
                    arrivalReturn.setReturnStatus("-304001");
                }
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
        arrivalService.insertBatch(arrivalList);

        //更新没有新增申报成功的记录
        if (updateArrivalList.size() > 0)
        arrivalService.updateBatchById(updateArrivalList);
        //判断是否是拆分的最后一个报文
        if (msgCount == msgSeqNo) {
            //运抵单399回执报文
            returnArrval399(arrivalHead.getBillNo(),baseTransfer.getDxpId());
            //清单800回执报文
            returnInvt800(arrivalHead.getBillNo(),baseTransfer.getDxpId());
        }

    }

    /**
     * 运抵单399回执报文
     * @param billNo
     * @param dxpId
     */
    private void returnArrval399(String billNo, String dxpId){
        CEB508Message ceb508Message = new CEB508Message();
        List<ArrivalReturn> arrivalReturnList = new ArrayList<>();
        EntityWrapper<com.kzkj.pojo.po.Arrival> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("bill_no", billNo);
        entityWrapper.eq("return_status", "2");
        List<com.kzkj.pojo.po.Arrival> arrivalList = arrivalService.selectList(entityWrapper);
        if(arrivalList == null || arrivalList.size() <= 0) return;
        for(com.kzkj.pojo.po.Arrival a : arrivalList)
        {
            ArrivalReturn arrivalReturn =new ArrivalReturn();
            BeanMapper.map(a,arrivalReturn);
            String now = sdf.format(new Date());
            arrivalReturn.setReturnTime(now);
            arrivalReturn.setReturnInfo("审核成功["+a.getOperatorCode()+"+"+a.getCopNo()+"]");
            arrivalReturn.setReturnStatus("399");
            arrivalReturnList.add(arrivalReturn);
            a.setReturnInfo(arrivalReturn.getReturnInfo());
            a.setReturnStatus(arrivalReturn.getReturnStatus());
        }
        ceb508Message.setArrivalReturn(arrivalReturnList);
        ceb508Message.setGuid(arrivalReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb508Message);
        String resultXml=customData(xml, dxpId, "CEB508Message");
        String queue=dxpId+"_HZ";
        if(arrivalService.updateBatchById(arrivalList))
        mqSender.sendMsg(queue, resultXml,"CEB508Message");
    }

    /**
     * 清单总分单800回执报文
     * @param billNo
     * @param dxpId
     */
    private void returnInvt800(String billNo, String dxpId){
        CEB608Message ceb608Message=new CEB608Message();
        List<WayBillReturn> wayBillReturnList = new ArrayList<>();
        EntityWrapper<com.kzkj.pojo.po.Waybill> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("bill_no", billNo);
        entityWrapper.eq("return_status", "399");
        List<com.kzkj.pojo.po.Waybill> waybillList = waybillService.selectList(entityWrapper);
        if(waybillList == null || waybillList.size() <= 0) return;
        for(com.kzkj.pojo.po.Waybill a : waybillList)
        {
            WayBillReturn wayBillReturn =new WayBillReturn();
            BeanMapper.map(a,wayBillReturn);
            String now = sdf.format(new Date());
            wayBillReturn.setReturnTime(now);
            wayBillReturn.setReturnInfo("放行["+a.getAgentCode()+"+"+a.getCopNo()+"]");
            wayBillReturn.setReturnStatus("800");
            wayBillReturnList.add(wayBillReturn);
            a.setReturnInfo(wayBillReturn.getReturnInfo());
            a.setReturnStatus(wayBillReturn.getReturnStatus());
        }
        ceb608Message.setWayBillReturn(wayBillReturnList);
        ceb608Message.setGuid(wayBillReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb608Message);
        String resultXml=customData(xml, dxpId, "ceb608Message");
        String queue=dxpId+"_HZ";
        if(waybillService.updateBatchById(waybillList));
        mqSender.sendMsg(queue, resultXml,"ceb608Message");
    }

    /**
     * 清单500回执报文
     * @param billNo
     * @param dxpId
     */
    private void returnInvt500(String billNo, String dxpId){
        CEB608Message ceb608Message=new CEB608Message();
        List<WayBillReturn> wayBillReturnList = new ArrayList<>();
        EntityWrapper<com.kzkj.pojo.po.Waybill> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("bill_no", billNo);
        List<com.kzkj.pojo.po.Waybill> waybillList = waybillService.selectList(entityWrapper);
        if(waybillList == null || waybillList.size() <= 0) return;
        for(com.kzkj.pojo.po.Waybill a : waybillList)
        {
            WayBillReturn wayBillReturn =new WayBillReturn();
            BeanMapper.map(a,wayBillReturn);
            String now = sdf.format(new Date());
            wayBillReturn.setReturnTime(now);
            wayBillReturn.setReturnInfo("查验["+a.getAgentCode()+"+"+a.getCopNo()+"]");
            wayBillReturn.setReturnStatus("500");
            wayBillReturnList.add(wayBillReturn);
        }
        ceb608Message.setWayBillReturn(wayBillReturnList);
        ceb608Message.setGuid(wayBillReturnList.get(0).getGuid());
        String xml= XMLUtil.convertToXml(ceb608Message);
        String resultXml=customData(xml, dxpId, "ceb608Message");
        String queue=dxpId+"_HZ";
        mqSender.sendMsg(queue, resultXml,"ceb608Message");
    }
}
