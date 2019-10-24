package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.WaybillDetailMapper;
import com.kzkj.mapper.WaybillMapper;
import com.kzkj.pojo.po.Waybill;
import com.kzkj.pojo.po.WaybillDetail;
import com.kzkj.pojo.vo.request.waybill.WayBill;
import com.kzkj.service.WaybillService;
import com.kzkj.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WaybillServiceImpl extends ServiceImpl<WaybillMapper, Waybill> implements WaybillService {

    @Autowired
    WaybillMapper waybillMapper;

    @Autowired
    WaybillDetailMapper waybillDetailMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean imsertWaybill(List<WayBill> wayBills) {
        if(wayBills == null || wayBills.size() <= 0) return false;
        for(WayBill w : wayBills)
        {
            Waybill waybill = new Waybill();
            BeanMapper.map(w.getWayBillHead(),waybill);
            waybillMapper.insert(waybill);
            if (w.getWayBillList() == null || w.getWayBillList().size() <= 0) continue;
            for(WaybillDetail wd : BeanMapper.mapList(w.getWayBillList(),WaybillDetail.class))
            {
                wd.setWaybillId(waybill.getId());
                waybillDetailMapper.insert(wd);
            }
        }
        return false;
    }
}
