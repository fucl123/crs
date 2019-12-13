package com.kzkj.service;

import com.baomidou.mybatisplus.service.IService;
import com.kzkj.pojo.po.ImportOrder;
import com.kzkj.pojo.vo.response.order.OrderReturn;

import java.util.List;

public interface ImportOrderService extends IService<ImportOrder> {
    boolean insertOrders(List<ImportOrder> orders);

    ImportOrder getByOrderNoAndEbcCode(String orderNo, String ebcCode);

    OrderReturn checkOrder(OrderReturn orderReturn, ImportOrder order);

    boolean batchUpdateOrder(List<ImportOrder> orderList);
}
