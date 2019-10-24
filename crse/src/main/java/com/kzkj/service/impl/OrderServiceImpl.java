package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.OrderDetailMapper;
import com.kzkj.mapper.OrderMapper;
import com.kzkj.pojo.po.Order;
import com.kzkj.pojo.po.OrderDetail;
import com.kzkj.service.OrderService;
import com.kzkj.utils.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertOrders(List<com.kzkj.pojo.vo.request.order.Order> orders)
    {
        if (orders == null || orders.size() <= 0) return false;
        for (com.kzkj.pojo.vo.request.order.Order o:orders)
        {
            Order order = new Order();
            BeanMapper.map(o.getOrderHead(),order);
            if (o.getOrderList() == null || o.getOrderList().size() <= 0)
                continue;
            orderMapper.insert(order);
            for (OrderDetail orderDetail : BeanMapper.mapList(o.getOrderList(),OrderDetail.class))
            {
                orderDetail.setOrderId(order.getId());
                orderDetailMapper.insert(orderDetail);
            }
        }
        return true;
    }
}
