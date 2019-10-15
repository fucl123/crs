package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Company extends Model<Company> {
    private String dxpId;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
