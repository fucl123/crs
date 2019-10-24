package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.DepartureMapper;
import com.kzkj.mapper.DepatureDetailMapper;
import com.kzkj.pojo.po.Departure;
import com.kzkj.pojo.po.DepatureDetail;
import com.kzkj.service.DepartureService;
import com.kzkj.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartureServiceImpl extends ServiceImpl<DepartureMapper, Departure> implements DepartureService {

    @Autowired
    DepartureMapper departureMapper;

    @Autowired
    DepatureDetailMapper depatureDetailMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertDepartures(List<com.kzkj.pojo.vo.request.departure.Departure> departures)
    {
        if (departures == null || departures.size() <= 0) return false;
        for (com.kzkj.pojo.vo.request.departure.Departure d : departures)
        {
            Departure departure = new Departure();
            BeanMapper.map(d.getDepartureHead(),departure);
            departureMapper.insert(departure);
            if (d.getDepartureList() == null || d.getDepartureList().size() <= 0) continue;
            for (DepatureDetail depatureDetail : BeanMapper.mapList(d.getDepartureList(),DepatureDetail.class))
            {
                depatureDetail.setDepatureId(departure.getId());
                depatureDetailMapper.insert(depatureDetail);
            }
        }
        return true;
    }
}
