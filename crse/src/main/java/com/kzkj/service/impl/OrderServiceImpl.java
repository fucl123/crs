package com.kzkj.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kzkj.mapper.OrderDetailMapper;
import com.kzkj.mapper.OrderMapper;
import com.kzkj.pojo.po.Order;
import com.kzkj.pojo.po.OrderDetail;
import com.kzkj.pojo.vo.response.order.OrderReturn;
import com.kzkj.service.OrderService;
import com.kzkj.utils.BeanMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public boolean insertOrders(List<Order> orders)
    {
        if (orders == null || orders.size() <= 0) return false;
        for (Order o:orders)
        {
            if (o.getOrderDetailList() == null || o.getOrderDetailList().size() <= 0)
                continue;
            orderMapper.insert(o);
            for (OrderDetail orderDetail : o.getOrderDetailList())
            {
                orderDetail.setOrderId(o.getId());
                orderDetailMapper.insert(orderDetail);
            }
        }
        return true;
    }

    @Override
    public Order getByOrderNoAndEbcCode(String orderNo, String ebcCode) {
        return orderMapper.getByOrderNoAndEbcCode(orderNo,ebcCode);
    }

    /**
     * 数据逻辑校验
     * @param orderReturn
     * @param order
     * @return
     */
    @Override
    public OrderReturn checkOrder(OrderReturn orderReturn, Order order)
    {
        String msg = "新增申报成功["+order.getGuid()+"]";
        String status = "2";

        if(StringUtils.isBlank(order.getGuid()))
        {
            msg = "guid不能为空";
            status = "100";
        }else if (StringUtils.isBlank(order.getAppType())){
            msg = "企业报送类型[appType]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(order.getAppTime())){
            msg = "企业报送时间[appTime]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(order.getAppStatus())){
            msg = "企业报送状态[appStatus]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(order.getOrderType())){
            msg = "电商平台的订单类型[orderType]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(order.getOrderNo())){
            msg = "电商平台的订单编号[orderNo]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(order.getEbpCode())){
            msg = "电商平台的海关注册登记编号或社会信用代码[ebpCode]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(order.getEbpName())){
            msg = "电商平台的登记名称[ebpName]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(order.getEbcCode())){
            msg = "电商企业的海关注册登记编码或社会信用代码[ebcCode]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(order.getEbcName())){
            msg = "电商企业的登记名称[ebcName]不能为空";
            status = "100";
        }else if (order.getGoodsValue() == null){
            msg = "商品实际成交价[goodsValue]不能为空";
            status = "100";
        }else if (order.getFreight() == null){
            msg = "运杂费[freight]不能为空";
            status = "100";
        }else if (StringUtils.isBlank(order.getCurrency())){
            msg = "币制[currency]不能为空";
            status = "100";
        }else if(order.getOrderDetailList() == null || order.getOrderDetailList().size() <= 0)
        {
            msg = "订单表体不能为空";
            status = "100";
        }else{
            for(OrderDetail orderDetail: order.getOrderDetailList())
            {
                if(StringUtils.isBlank(orderDetail.getItemNo())){
                    msg = "企业商品货号[itemNo]不能为空";
                    status = "100";
                    break;
                }else if(orderDetail.getGnum() == null)
                {
                    msg = "企业商品序号[gnum]不能为空";
                    status = "100";
                    break;
                }else if(StringUtils.isBlank(orderDetail.getItemName()))
                {
                    msg = "企业商品名称[itemName]不能为空";
                    status = "100";
                    break;
                }else if(StringUtils.isBlank(orderDetail.getUnit()))
                {
                    msg = "计量单位[unit]不能为空";
                    status = "100";
                    break;
                }else if(StringUtils.isBlank(orderDetail.getCurrency()))
                {
                    msg = "币制[currency]不能为空";
                    status = "100";
                    break;
                }else if(orderDetail.getQty() == null)
                {
                    msg = "数量[qty]不能为空";
                    status = "100";
                    break;
                }else if(orderDetail.getPrice() == null)
                {
                    msg = "单价[price]不能为空";
                    status = "100";
                    break;
                }else if(orderDetail.getTotalPrice() == null)
                {
                    msg = "总价[totalPrice]不能为空";
                    status = "100";
                    break;
                }else if(new BigDecimal(orderDetail.getTotalPrice().toString()).
                        compareTo(new BigDecimal(orderDetail.getPrice().toString()).multiply(new BigDecimal(orderDetail.getQty()))) != 0)
                {
                    msg = "数量[qty]乘单价[price]不等于总价[totalPrice]";
                    status = "100";
                    break;
                }
            }
        }
        orderReturn.setReturnInfo(msg);
        orderReturn.setReturnStatus(status);
        return orderReturn;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean batchUpdateOrder(List<Order> orderList) {
        if(orderList == null || orderList.size() <= 0) return false;
        for(Order o: orderList)
        {
            orderMapper.updateById(o);
            if (o.getOrderDetailList() == null || o.getOrderDetailList().size() <= 0)
                continue;
            EntityWrapper<OrderDetail> wrapper = new EntityWrapper<>();
            wrapper.eq("order_id",o.getId());
            orderDetailMapper.delete(wrapper);
            for(OrderDetail od: o.getOrderDetailList())
            {
                od.setOrderId(o.getId());
                orderDetailMapper.insert(od);
            }
        }
        return true;
    }
}
