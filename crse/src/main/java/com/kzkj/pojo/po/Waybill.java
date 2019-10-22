package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
@Getter
@Setter
@TableName("tb_waybill")
public class Waybill extends Model<Waybill> {
    private Integer id;

    private String guid;

    private String appType;

    private String appTime;

    private String appStatus;

    private String customsCode;

    private String copNo;

    private String preNo;

    private String agentCode;

    private String agentName;

    private String loctNo;

    private String trafMode;

    private String trafName;

    private String voyageNo;

    private String billNo;

    private String domesticTrafNo;

    private BigDecimal grossWeight;

    private String logisticsCode;

    private String logisticsName;

    private Integer msgCount;

    private Integer msgSeqNo;

    private String note;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}