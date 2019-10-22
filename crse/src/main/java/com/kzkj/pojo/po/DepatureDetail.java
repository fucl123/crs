package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@TableName("tb_depature_detail")
public class DepatureDetail extends Model<DepatureDetail> {
    private Integer id;

    private Integer depatureId;

    private Integer gnum;

    private String totalPackageNo;

    private String logisticsNo;

    private String note;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}