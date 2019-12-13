package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yaobin
 * @since 2019-12-12
 */
@Data
@TableName("tb_import_invt_cancel")
public class ImportInvtCancel extends Model<ImportInvtCancel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String guid;

    @TableField("app_type")
    private String appType;

    @TableField("app_time")
    private String appTime;

    @TableField("app_status")
    private String appStatus;

    @TableField("customs_code")
    private String customsCode;

    @TableField("order_no")
    private String orderNo;

    @TableField("ebp_code")
    private String ebpCode;

    @TableField("ebp_name")
    private String ebpName;

    @TableField("logistics_no")
    private String logisticsNo;

    @TableField("logistics_code")
    private String logisticsCode;

    @TableField("logistics_name")
    private String logisticsName;

    @TableField("buyer_id_type")
    private String buyerIdType;

    @TableField("buyer_id_number")
    private String buyerIdNumber;

    @TableField("buyer_telephone")
    private String buyerTelephone;

    @TableField("buyer_name")
    private String buyerName;

    @TableField("cop_no")
    private String copNo;

    @TableField("pre_no")
    private String preNo;

    @TableField("invt_no")
    private String invtNo;

    private String reason;

    @TableField("agent_code")
    private String agentCode;

    @TableField("agent_name")
    private String agentName;

    @TableField("ebc_code")
    private String ebcCode;

    @TableField("ebc_name")
    private String ebcName;

    private String note;

    @TableField("return_info")
    private String returnInfo;

    @TableField("return_status")
    private String returnStatus;

    @TableField("return_time")
    private String returnTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
