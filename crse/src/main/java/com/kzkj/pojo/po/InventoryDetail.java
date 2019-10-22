package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
@Getter
@Setter
@TableName("tb_inventory_detail")
public class InventoryDetail extends Model<InventoryDetail> {
    private Integer id;

    private Integer inventoryId;

    private Integer gnum;

    private String itemNo;

    private String itemRecordNo;

    private String itemName;

    private String gcode;

    private String gname;

    private String gmodel;

    private String barCode;

    private String country;

    private String currency;

    private BigDecimal qty;

    private BigDecimal qty1;

    private BigDecimal qty2;

    private String unit;

    private String unit1;

    private String unit2;

    private BigDecimal price;

    private BigDecimal totalPrice;

    private String note;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}