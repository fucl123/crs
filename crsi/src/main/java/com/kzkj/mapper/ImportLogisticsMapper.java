package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.ImportLogistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImportLogisticsMapper extends BaseMapper<ImportLogistics> {
    List<ImportLogistics> getByLogisticsNo(String logisticsNo);

    ImportLogistics getByLogisticsCodeAndNo(@Param("logisticsCode") String logisticsCode, @Param("logisticsNo")String logisticsNo);

    void updateReturnStatus(ImportLogistics logistics);
}