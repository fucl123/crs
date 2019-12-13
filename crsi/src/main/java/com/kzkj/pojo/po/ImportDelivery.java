package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@TableName("tb_import_delivery")
public class ImportDelivery extends Model<ImportDelivery> {
    private Integer id;

    private String guid;

    private String appType;

    private String appTime;

    private String appStatus;

    private String customsCode;

    private String copNo;

    private String rkdNo;

    private String preNo;

    private String operatorCode;

    private String operatorName;

    private String ieFlag;

    private String trafMode;

    private String trafNo;

    private String voyageNo;

    private String billNo;

    private String logisticsCode;

    private String logisticsName;

    private String unloadLocation;

    private String note;

    private String returnStatus;

    private String returnTime;

    private String returnInfo;

    private Date createTime;

    private Date updateTime;

    @TableField(exist= false)
    private List<ImportDeliveryDetail> importDeliveryDetailList;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}