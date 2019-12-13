package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.ImportLogisticsMapper;
import com.kzkj.pojo.po.ImportLogistics;
import com.kzkj.pojo.vo.response.logistics.LogisticsReturn;
import com.kzkj.service.ImportLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ImportLogisticsServiceImpl extends ServiceImpl<ImportLogisticsMapper, ImportLogistics> implements ImportLogisticsService {
    @Autowired
    ImportLogisticsMapper importLogisticsMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertLogistics(List<ImportLogistics> logisticsList) {
        if(logisticsList == null || logisticsList.size() <= 0) return false;
        for (ImportLogistics l : logisticsList)
        {
            importLogisticsMapper.insert(l);
        }
        return true;
    }

    @Override
    public List<ImportLogistics> getByLogisticsNo(String logisticsNo) {
        return importLogisticsMapper.getByLogisticsNo(logisticsNo);
    }

    @Override
    public ImportLogistics getByLogisticsCodeAndNo(String logisticsCode, String logisticsNo) {
        return importLogisticsMapper.getByLogisticsCodeAndNo(logisticsCode,logisticsNo);
    }

    @Override
    public LogisticsReturn checkLogistics(LogisticsReturn logisticsReturn, ImportLogistics logistics) {

        logisticsReturn.setReturnStatus("2");
        logisticsReturn.setReturnInfo("新增申报成功["+logistics.getLogisticsCode()+"+"+logistics.getLogisticsNo()+"]");
        return logisticsReturn;
    }
}
