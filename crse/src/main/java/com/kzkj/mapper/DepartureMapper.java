package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.Departure;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DepartureMapper extends BaseMapper<Departure> {

    Departure getByLogisticsCodeAndCopNo(@Param("logisticsCode") String logisticsCode, @Param("copNo")String copNo);
}