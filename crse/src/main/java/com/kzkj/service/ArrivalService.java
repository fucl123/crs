package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Arrival;
import com.kzkj.pojo.vo.response.arrival.ArrivalReturn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArrivalService extends IService<Arrival> {

    boolean insertArrival(List<com.kzkj.pojo.vo.request.arrival.Arrival> arrivals);

    Arrival getByOperatorCodeAndCopNo(String operatorCode, String copNo);

    ArrivalReturn checkArrival(ArrivalReturn arrivalReturn, Arrival arrival);
}
