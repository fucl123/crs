package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
@TableName("tb_import_order")
public class ImportOrder extends Model<ImportOrder> {
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

    private BigDecimal discount;

    private BigDecimal taxTotal;

    private BigDecimal acturalPaid;

    private String currency;

    private String buyerRegNo;

    private String buyerName;

    private String buyerTelephone;

    private String buyerIdType;

    private String buyerIdNumber;

    private String payCode;

    private String payName;

    private String payTransactionId;

    private String batchNumbers;

    private String consignee;

    private String consigneeTelephone;

    private String consigneeAddress;

    private String consigneeDistrict;

    private String note;

    private String returnState;

    private String returnTime;

    private String returnInfo;

    private Date createTime;

    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}