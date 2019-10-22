package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}