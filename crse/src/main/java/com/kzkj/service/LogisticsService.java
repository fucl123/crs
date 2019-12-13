package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Logistics;
import com.kzkj.pojo.vo.response.logistics.LogisticsReturn;

import java.util.List;

public interface LogisticsService extends IService<Logistics> {

    boolean insertLogistics(List<Logistics> logisticsList);

    List<Logistics> getByLogisticsNo(String logisticsNo);

    Logistics getByLogisticsCodeAndNo(String logisticsCode, String logisticsNo);

    LogisticsReturn checkLogistics(LogisticsReturn logisticsReturn,Logistics logistics);
}
