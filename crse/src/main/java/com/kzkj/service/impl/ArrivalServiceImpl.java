package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.ArrivalMapper;
import com.kzkj.pojo.po.Arrival;
import com.kzkj.service.ArrivalService;
import com.kzkj.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArrivalServiceImpl extends ServiceImpl<ArrivalMapper, Arrival> implements ArrivalService {

    @Autowired
    ArrivalMapper arrivalMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertArrival(List<com.kzkj.pojo.vo.request.arrival.Arrival> arrivals)
    {
        if(arrivals == null || arrivals.size() <= 0) return false;
        for (com.kzkj.pojo.vo.request.arrival.Arrival arrival : arrivals)
        {
            arrivalMapper.insert(BeanMapper.map(arrival.getArrivalHead(),Arrival.class));
        }
        return true;
    }
}
