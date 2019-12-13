package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.ImportLogistics;
import com.kzkj.pojo.vo.response.logistics.LogisticsReturn;

import java.util.List;

public interface ImportLogisticsService extends IService<ImportLogistics> {
    boolean insertLogistics(List<ImportLogistics> logisticsList);

    List<ImportLogistics> getByLogisticsNo(String logisticsNo);

    ImportLogistics getByLogisticsCodeAndNo(String logisticsCode, String logisticsNo);

    LogisticsReturn checkLogistics(LogisticsReturn logisticsReturn, ImportLogistics logistics);
}
