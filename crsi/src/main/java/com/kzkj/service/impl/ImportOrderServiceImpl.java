package com.kzkj.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.ImportOrderDetailMapper;
import com.kzkj.mapper.ImportOrderMapper;
import com.kzkj.pojo.po.ImportOrder;
import com.kzkj.pojo.po.ImportOrderDetail;
import com.kzkj.pojo.vo.response.order.OrderReturn;
import com.kzkj.service.ImportOrderService;
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
    public boolean insertOrders(List<ImportOrder> orders)
    {
        if (orders == null || orders.size() <= 0) return false;
        for (ImportOrder o:orders)
        {
            if (o.getImportOrderDetailList() == null || o.getImportOrderDetailList().size() <= 0)
                continue;
            importOrderMapper.insert(o);
            for (ImportOrderDetail orderDetail : o.getImportOrderDetailList())
            {
                orderDetail.setImportOrderId(o.getId());
                importOrderDetailMapper.insert(orderDetail);
            }
        }
        return true;
    }

    @Override
    public ImportOrder getByOrderNoAndEbcCode(String orderNo, String ebcCode) {
        return importOrderMapper.getByOrderNoAndEbcCode(orderNo,ebcCode);
    }

    /**
     * 数据逻辑校验
     * @param orderReturn
     * @param order
     * @return
     */
    @Override
    public OrderReturn checkOrder(OrderReturn orderReturn, ImportOrder order)
    {
        String msg = "新增申报成功["+order.getGuid()+"]";
        orderReturn.setReturnInfo(msg);
        orderReturn.setReturnStatus("2");
        /*if()
        {

        }
        if()
        {

        }
        if()
        {

        }
        if()
        {

        }*/

        return orderReturn;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean batchUpdateOrder(List<ImportOrder> orderList) {
        if(orderList == null || orderList.size() <= 0) return false;
        for(ImportOrder o: orderList)
        {
            importOrderMapper.updateById(o);
            if (o.getImportOrderDetailList() == null || o.getImportOrderDetailList().size() <= 0)
                continue;
            EntityWrapper<ImportOrderDetail> wrapper = new EntityWrapper<>();
            wrapper.eq("order_id",o.getId());
            importOrderDetailMapper.delete(wrapper);
            for(ImportOrderDetail od: o.getImportOrderDetailList())
            {
                od.setImportOrderId(o.getId());
                importOrderDetailMapper.insert(od);
            }
        }
        return true;
    }
}
