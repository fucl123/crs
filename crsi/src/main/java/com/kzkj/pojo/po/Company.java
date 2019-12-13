package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@TableName("tb_company")
public class Company extends Model<Company> {
    @TableId
    private int id;

    @TableField("company_code")
    private String companyCode;

    @TableField("company_name")
    private String companyName;

    @TableField("dxp_id_e")
    private String dxpIdE;

    @TableField("dxp_id_i")
    private String dxpIdI;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
