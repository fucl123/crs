package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.Arrival;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArrivalMapper extends BaseMapper<Arrival> {

    Arrival getByOperatorCodeAndCopNo(@Param("operatorCode") String operatorCode, @Param("copNo") String copNo);
}