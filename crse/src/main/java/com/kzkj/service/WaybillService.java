package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Waybill;
import com.kzkj.pojo.vo.request.waybill.WayBill;
import com.kzkj.pojo.vo.response.waybill.WayBillReturn;

import java.io.Serializable;
import java.util.List;

public interface WaybillService extends IService<Waybill> {

    boolean imsertWaybill(List<Waybill> waybillList);

    Waybill getByAgentCodeAndCopNo(String agentCode,String copNo);

    WayBillReturn checkWaybill(WayBillReturn wayBillReturn,Waybill waybill);

    boolean batchUpdateWaybill(List<Waybill> waybillList);
}
