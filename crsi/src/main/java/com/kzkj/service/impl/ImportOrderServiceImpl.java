package com.kzkj.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.ImportOrderDetailMapper;
import com.kzkj.mapper.ImportOrderMapper;
import com.kzkj.pojo.po.ImportOrder;
import com.kzkj.pojo.po.ImportOrderDetail;
import com.kzkj.pojo.vo.request.order.Order;
import com.kzkj.service.ImportOrderService;
import com.kzkj.utils.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImportOrderServiceImpl extends ServiceImpl<ImportOrderMapper, ImportOrder> implements ImportOrderService {
    @Autowired
    ImportOrderMapper importOrderMapper;

    @Autowired
    ImportOrderDetailMapper importOrderDetailMapper;

    Logger logger = LoggerFactory.getLogger(ImportOrderServiceImpl.class);

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean insertOrders(List<Order> orders)
    {
        if (orders == null || orders.size() <= 0) return false;
        for (com.kzkj.pojo.vo.request.order.Order o:orders)
        {
            ImportOrder order = new ImportOrder();
            BeanMapper.map(o.getOrderHead(),order);
            if (o.getOrderList() == null || o.getOrderList().size() <= 0)
                continue;
            importOrderMapper.insert(order);
            for (ImportOrderDetail orderDetail : BeanMapper.mapList(o.getOrderList(), ImportOrderDetail.class))
            {
                orderDetail.setImportOrderId(order.getId());
                importOrderDetailMapper.insert(orderDetail);
            }
        }
        return true;
    }
}
