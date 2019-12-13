package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @since 2019-12-11
 */
@Data
@TableName("tb_receipts")
public class Receipts extends Model<Receipts> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String guid;

    @TableField("app_type")
    private String appType;

    @TableField("app_time")
    private String appTime;

    @TableField("app_status")
    private String appStatus;

    @TableField("ebp_code")
    private String ebpCode;

    @TableField("ebp_name")
    private String ebpName;

    @TableField("order_no")
    private String orderNo;

    @TableField("ebc_code")
    private String ebcCode;

    @TableField("ebc_name")
    private String ebcName;

    @TableField("pay_code")
    private String payCode;

    @TableField("pay_name")
    private String payName;

    @TableField("pay_no")
    private String payNo;

    private BigDecimal charge;

    private String currency;

    private String note;

    @TableField("return_info")
    private String returnInfo;

    @TableField("return_status")
    private String returnStatus;

    @TableField("return_time")
    private String returnTime;

    @TableField("accounting_date")
    private String accountingDate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
