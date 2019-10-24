package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Waybill;
import com.kzkj.pojo.vo.request.waybill.WayBill;

import java.util.List;

public interface WaybillService extends IService<Waybill> {

    boolean imsertWaybill(List<WayBill> wayBills);
}
