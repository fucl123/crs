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
 * 撤销申请单
 * </p>
 * @since 2019-12-11
 */
@Data
@TableName("tb_invt_cancel")
public class InvtCancel extends Model<InvtCancel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 系统唯一序号
     */
    private String guid;

    /**
     * 报送类型 
     */
    @TableField("app_type")
    private String appType;

    /**
     * 报送时间 
     */
    @TableField("app_time")
    private String appTime;

    /**
     * 报送状态 
     */
    @TableField("app_status")
    private String appStatus;

    /**
     * 申报地海关代码 
     */
    @TableField("customs_code")
    private String customsCode;

    /**
     * 企业唯一编号 
     */
    @TableField("cop_no")
    private String copNo;

    /**
     * 电子口岸编号 
     */
    @TableField("pre_no")
    private String preNo;

    /**
     * 原清单编号 
     */
    @TableField("invt_no")
    private String invtNo;

    /**
     * 撤单原因 
     */
    private String reason;

    /**
     * 申报企业代码 
     */
    @TableField("agent_code")
    private String agentCode;

    /**
     * 申报企业名称 
     */
    @TableField("agent_name")
    private String agentName;

    /**
     * 收发货人代码 
     */
    @TableField("ebc_code")
    private String ebcCode;

    /**
     * 收发货人名称 
     */
    @TableField("ebc_name")
    private String ebcName;

    /**
     * 备注 
     */
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
