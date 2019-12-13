package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.ImportInventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ImportInventoryMapper extends BaseMapper<ImportInventory> {
    ImportInventory getByOrderNoAndEbcCode(@Param("orderNo") String orderNo, @Param("ebcCode") String ebcCode);

    void updateReturnStatus(ImportInventory inventory);

    ImportInventory getByLogisticsCodeAndNo(@Param("logisticsCode")String logisticsCode, @Param("logisticsNo")String logisticsNo);

}