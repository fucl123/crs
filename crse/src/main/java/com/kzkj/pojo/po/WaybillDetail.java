package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@TableName("tb_waybill_detail")
public class WaybillDetail extends Model<WaybillDetail> {
    private Integer id;

    private Integer waybillId;

    private Integer gnum;

    private String totalPackageNo;

    private String logisticsNo;

    private String invtNo;

    private String note;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}