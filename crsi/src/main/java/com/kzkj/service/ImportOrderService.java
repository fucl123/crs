package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.ImportOrder;
import com.kzkj.pojo.vo.request.order.Order;

import java.util.List;

public interface ImportOrderService extends IService<ImportOrder> {
    boolean insertOrders(List<Order> orders);
}
