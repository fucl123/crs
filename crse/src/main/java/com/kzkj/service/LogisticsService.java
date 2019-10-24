package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Logistics;

import java.util.List;

public interface LogisticsService extends IService<Logistics> {

    boolean insertLogistics(List<com.kzkj.pojo.vo.request.logistics.Logistics> logisticsList);
}
