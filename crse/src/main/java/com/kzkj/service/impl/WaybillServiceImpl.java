package com.kzkj.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.WaybillDetailMapper;
import com.kzkj.mapper.WaybillMapper;
import com.kzkj.pojo.po.Waybill;
import com.kzkj.pojo.po.WaybillDetail;
import com.kzkj.pojo.vo.request.waybill.WayBill;
import com.kzkj.pojo.vo.response.waybill.WayBillReturn;
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
    public boolean imsertWaybill(List<Waybill> waybillList) {
        if(waybillList == null || waybillList.size() <= 0) return false;
        for(Waybill w : waybillList)
        {
            waybillMapper.insert(w);
            if (w.getWaybillDetailList() == null || w.getWaybillDetailList().size() <= 0) continue;
            for(WaybillDetail wd : BeanMapper.mapList(w.getWaybillDetailList(),WaybillDetail.class))
            {
                wd.setWaybillId(w.getId());
                waybillDetailMapper.insert(wd);
            }
        }
        return true;
    }

    @Override
    public Waybill getByAgentCodeAndCopNo(String agentCode, String copNo) {
        return waybillMapper.getByAgentCodeAndCopNo(agentCode,copNo);
    }

    @Override
    public WayBillReturn checkWaybill(WayBillReturn wayBillReturn, Waybill waybill) {
        wayBillReturn.setReturnInfo("新增申报成功["+waybill.getAgentCode()+"+"+waybill.getCopNo()+"]");
        wayBillReturn.setReturnStatus("2");
        return wayBillReturn;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean batchUpdateWaybill(List<Waybill> waybillList) {
        if(waybillList == null || waybillList.size() <= 0) return false;
        for(Waybill w : waybillList)
        {
            waybillMapper.updateById(w);
            if (w.getWaybillDetailList() == null || w.getWaybillDetailList().size() <= 0) continue;
            EntityWrapper<Waybill> wrapper = new EntityWrapper<Waybill>();
            wrapper.eq("waybill_id",w.getId());
            waybillMapper.delete(wrapper);
            for(WaybillDetail wd : w.getWaybillDetailList())
            {
                wd.setWaybillId(w.getId());
                waybillDetailMapper.insert(wd);
            }
        }
        return true;
    }
}
