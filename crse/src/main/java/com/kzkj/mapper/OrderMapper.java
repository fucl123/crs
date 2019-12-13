package com.kzkj.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kzkj.pojo.po.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    Order getByOrderNoAndEbcCode(@Param("orderNo") String orderNo,@Param("ebcCode") String ebcCode);

    void updateReturnStatus(Order order);
}