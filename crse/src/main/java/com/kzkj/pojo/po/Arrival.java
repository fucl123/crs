package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@TableName("tb_arrival")
public class Arrival extends Model<Arrival> {
    private Integer id;

    private String guid;

    private String appType;

    private String appTime;

    private String appStatus;

    private String customsCode;

    private String copNo;

    private String preNo;

    private String operatorCode;

    private String operatorName;

    private String loctNo;

    private String ieFlag;

    private String trafMode;

    private String billNo;

    private String domesticTrafNo;

    private String logisticsCode;

    private String logisticsName;

    private Integer msgCount;

    private Integer msgSeqNo;

    private String returnStatus;

    private String returnInfo;

    private String returnTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}