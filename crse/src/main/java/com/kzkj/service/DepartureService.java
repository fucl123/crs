package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Departure;

import java.util.List;

public interface DepartureService extends IService<Departure> {

    boolean insertDepartures(List<com.kzkj.pojo.vo.request.departure.Departure> departures);
}
