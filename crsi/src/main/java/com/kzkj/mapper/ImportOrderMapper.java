package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.ImportOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ImportOrderMapper extends BaseMapper<ImportOrder> {

    ImportOrder getByOrderNoAndEbcCode(@Param("orderNo") String orderNo, @Param("ebcCode") String ebcCode);

    void updateReturnStatus(ImportOrder order);
}