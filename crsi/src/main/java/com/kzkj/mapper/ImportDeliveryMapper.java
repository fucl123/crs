package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.ImportDelivery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ImportDeliveryMapper extends BaseMapper<ImportDelivery> {

    ImportDelivery getByOperatorCodeAndCopNo(@Param("operatorCode")String operatorCode, @Param("copNo")String copNo);

}