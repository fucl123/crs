package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.ImportLogistics;
import com.kzkj.pojo.vo.request.logistics.Logistics;

import java.util.List;

public interface ImportLogisticsService extends IService<ImportLogistics> {
    boolean insertLogistics(List<com.kzkj.pojo.vo.request.logistics.ImportLogistics> logisticsList);

    List<ImportLogistics> getByLogisticsNo(String logisticsNo);
}
