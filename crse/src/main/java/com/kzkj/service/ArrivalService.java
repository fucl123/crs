package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Arrival;

import java.util.List;

public interface ArrivalService extends IService<Arrival> {

    boolean insertArrival(List<com.kzkj.pojo.vo.request.arrival.Arrival> arrivals);
}
