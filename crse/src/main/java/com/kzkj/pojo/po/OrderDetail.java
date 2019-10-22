package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@TableName("tb_order_detail")
public class OrderDetail extends Model<OrderDetail> {
    private Integer id;

    private Integer orderId;

    private Integer gnum;

    private String itemNo;

    private String itemName;

    private String itemDescribe;

    private String barCode;

    private String unit;

    private String currency;

    private Integer qty;

    private Double price;

    private Double totalPrice;

    private String note;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}