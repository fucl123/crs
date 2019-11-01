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
@TableName("tb_import_inventory")
public class ImportInventory extends Model<ImportInventory> {
    private Integer id;

    private String guid;

    private String appType;

    private String appTime;

    private String appStatus;

    private String orderNo;

    private String ebpCode;

    private String ebpName;

    private String ebcCode;

    private String ebcName;

    private String logisticsNo;

    private String logisticsCode;

    private String logisticsName;

    private String copNo;

    private String preNo;

    private String assureCode;

    private String emsNo;

    private String invtNo;

    private String ieFlag;

    private String declTime;

    private String portCode;

    private String ieDate;

    private String buyerIdType;

    private String buyerIdNumber;

    private String buyerName;

    private String buyerTelephone;

    private String consigneeAddress;

    private String agentCode;

    private String agentName;

    private String areaCode;

    private String areaName;

    private String tradeMode;

    private String trafMode;

    private String trafNo;

    private String voyageNo;

    private String billNo;

    private String loctNo;

    private String licenseNo;

    private String country;

    private String pod;

    private BigDecimal freight;

    private BigDecimal insuredFee;

    private String currency;

    private String wrapType;

    private Integer packNo;

    private BigDecimal grossWeight;

    private BigDecimal netWeight;

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