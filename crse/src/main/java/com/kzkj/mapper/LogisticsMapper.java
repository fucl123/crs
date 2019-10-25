package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.Logistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogisticsMapper extends BaseMapper<Logistics> {

    List<Logistics> getByLogisticsNo(String logisticsNo);
}