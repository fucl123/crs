package com.kzkj.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.DepartureMapper;
import com.kzkj.mapper.DepatureDetailMapper;
import com.kzkj.pojo.po.Departure;
import com.kzkj.pojo.po.DepatureDetail;
import com.kzkj.pojo.vo.response.departure.DepartureReturn;
import com.kzkj.service.DepartureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DepartureServiceImpl extends ServiceImpl<DepartureMapper, Departure> implements DepartureService {

    @Autowired
    DepartureMapper departureMapper;

    @Autowired
    DepatureDetailMapper depatureDetailMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertDepartures(List<Departure> departures)
    {
        if (departures == null || departures.size() <= 0) return false;
        for (Departure d : departures)
        {
            departureMapper.insert(d);
            if (d.getDepatureDetailList() == null || d.getDepatureDetailList().size() <= 0) continue;
            for (DepatureDetail depatureDetail : d.getDepatureDetailList())
            {
                depatureDetail.setDepatureId(d.getId());
                depatureDetailMapper.insert(depatureDetail);
            }
        }
        return true;
    }

    @Override
    public DepartureReturn checkDeparture(DepartureReturn departureReturn, Departure departure)
    {
        departureReturn.setReturnInfo("新增申报成功["+departure.getLogisticsCode()+"+"+departure.getCopNo()+"]");
        departureReturn.setReturnStatus("2");
        return departureReturn;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean batchUpdateDeparture(List<Departure> departureList) {
        if (departureList == null || departureList.size() <= 0) return false;
        for (Departure d : departureList)
        {
            departureMapper.updateById(d);
            if (d.getDepatureDetailList() == null || d.getDepatureDetailList().size() <= 0) continue;
            EntityWrapper<DepatureDetail> wrapper = new EntityWrapper<>();
            wrapper.eq("depature_id",d.getId());
            depatureDetailMapper.delete(wrapper);
            for (DepatureDetail depatureDetail : d.getDepatureDetailList())
            {
                depatureDetail.setDepatureId(d.getId());
                depatureDetailMapper.insert(depatureDetail);
            }
        }
        return true;
    }

    @Override
    public Departure getByLogisticsCodeAndCopNo(String logisticsCode, String copNo) {
        return null;
    }
}
