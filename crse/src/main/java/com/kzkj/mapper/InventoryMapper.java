package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {

    Inventory getByOrderNoAndEbcCode(@Param("orderNo") String orderNo, @Param("ebcCode") String ebcCode);

    void updateReturnStatus(Inventory inventory);

    Inventory getByLogisticsCodeAndNo(@Param("logisticsCode")String logisticsCode, @Param("logisticsNo")String logisticsNo);
}