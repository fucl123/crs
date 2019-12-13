package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
@Getter
@Setter
@TableName("tb_logistics")
public class Logistics extends Model<Logistics> {
    private Integer id;

    private String guid;

    private String appType;

    private String appTime;

    private String appStatus;

    private String logisticsCode;

    private String logisticsName;

    private String logisticsNo;

    private BigDecimal freight;

    private BigDecimal insuredFee;

    private String currency;

    private BigDecimal grossWeight;

    private Integer packNo;

    private String goodsInfo;

    private String ebcCode;

    private String ebcName;

    private String ebcTelephone;

    private String note;

    private String returnStatus;

    private String returnInfo;

    private String returnTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}