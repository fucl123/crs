package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@TableName("tb_departure")
public class Departure extends Model<Departure> {
    private Integer id;

    private String guid;

    private String appType;

    private String appTime;

    private String appStatus;

    private String customsCode;

    private String copNo;

    private String preNo;

    private String logisticsCode;

    private String logisticsName;

    private String trafMode;

    private String trafName;

    private String voyageNo;

    private String billNo;

    private String leaveTime;

    private Integer msgCount;

    private Integer msgSeqNo;

    private String note;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}