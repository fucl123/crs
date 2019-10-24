package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.LogisticsMapper;
import com.kzkj.pojo.po.Logistics;
import com.kzkj.service.LogisticsService;
import com.kzkj.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {

    @Autowired
    LogisticsMapper logisticsMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertLogistics(List<com.kzkj.pojo.vo.request.logistics.Logistics> logisticsList) {
        if(logisticsList == null || logisticsList.size() <= 0) return false;
        List<Logistics> logistics = new ArrayList<>();
        logistics.addAll(BeanMapper.mapList(logisticsList,Logistics.class));
        if (logistics == null || logistics.size() <= 0) return false;
        for (Logistics l : logistics)
        {
            logisticsMapper.insert(l);
        }
        return true;
    }
}
