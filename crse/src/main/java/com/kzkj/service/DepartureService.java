package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Departure;
import com.kzkj.pojo.vo.response.departure.DepartureReturn;

import java.util.List;

public interface DepartureService extends IService<Departure> {

    boolean insertDepartures(List<Departure> departures);

    DepartureReturn checkDeparture(DepartureReturn departureReturn, Departure departure);

    boolean batchUpdateDeparture(List<Departure> departureList);

    Departure getByLogisticsCodeAndCopNo(String logisticsCode,String copNo);
}
