package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@TableName("tb_order")
public class Order extends Model<Order> {
    private Integer id;

    private String guid;

    private String appType;

    private String appTime;

    private String appStatus;

    private String orderType;

    private String orderNo;

    private String ebpCode;

    private String ebpName;

    private String ebcCode;

    private String ebcName;

    private BigDecimal goodsValue;

    private BigDecimal freight;

    private String currency;

    private String note;

    private String returnStatus;

    private String returnInfo;

    private String returnTime;

    @TableField(exist= false)
    private List<OrderDetail> orderDetailList;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}