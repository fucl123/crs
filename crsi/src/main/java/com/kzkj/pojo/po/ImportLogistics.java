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
@TableName("tb_import_logistics")
public class ImportLogistics extends Model<ImportLogistics> {
    private Integer id;

    private String guid;

    private String appType;

    private String appTime;

    private String appStatus;

    private String logisticsCode;

    private String logisticsName;

    private String logisticsNo;

    private String billNo;

    private BigDecimal freight;

    private BigDecimal insuredFee;

    private String currency;

    private BigDecimal weight;

    private Integer packNo;

    private String goodsInfo;

    private String consignee;

    private String consigneeAddress;

    private String consigneeTelephone;

    private String note;

    private String returnStatus;

    private String returnTime;

    private String returnInfo;

    private Date createTime;

    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}