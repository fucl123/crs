package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.Waybill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WaybillMapper extends BaseMapper<Waybill> {

    Waybill getByAgentCodeAndCopNo(@Param("agentCode")String agentCode, @Param("copNo")String copNo);
}