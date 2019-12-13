package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Order;
import com.kzkj.pojo.vo.response.order.OrderReturn;

import java.util.List;

public interface OrderService extends IService<Order> {

    boolean insertOrders(List<Order> orders);

    Order getByOrderNoAndEbcCode(String orderNo, String ebcCode);

    OrderReturn checkOrder(OrderReturn orderReturn, Order order);

    boolean batchUpdateOrder(List<Order> orderList);
}
