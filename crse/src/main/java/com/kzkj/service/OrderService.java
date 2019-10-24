package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.Order;

import java.util.List;

public interface OrderService extends IService<Order> {

    boolean insertOrders(List<com.kzkj.pojo.vo.request.order.Order> orders);
}
