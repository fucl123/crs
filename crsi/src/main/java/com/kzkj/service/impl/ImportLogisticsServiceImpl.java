package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.ImportLogisticsMapper;
import com.kzkj.pojo.po.ImportLogistics;
import com.kzkj.pojo.vo.request.logistics.Logistics;
import com.kzkj.service.ImportLogisticsService;
import com.kzkj.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImportLogisticsServiceImpl extends ServiceImpl<ImportLogisticsMapper, ImportLogistics> implements ImportLogisticsService {
    @Autowired
    ImportLogisticsMapper logisticsMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertLogistics(List<com.kzkj.pojo.vo.request.logistics.ImportLogistics> logisticsList) {
        if(logisticsList == null || logisticsList.size() <= 0) return false;
        List<ImportLogistics> logistics = new ArrayList<>();
        logistics.addAll(BeanMapper.mapList(logisticsList,ImportLogistics.class));
        if (logistics == null || logistics.size() <= 0) return false;
        for (ImportLogistics l : logistics)
        {
            logisticsMapper.insert(l);
        }
        return true;
    }

    @Override
    public List<ImportLogistics> getByLogisticsNo(String logisticsNo) {
        return logisticsMapper.getByLogisticsNo(logisticsNo);
    }
}
