package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@TableName("tb_import_delivery_detail")
public class ImportDeliveryDetail extends Model<ImportDeliveryDetail> {
    private Integer id;

    private Integer deliveryId;

    private Integer gnum;

    private String logisticsNo;

    private String note;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}