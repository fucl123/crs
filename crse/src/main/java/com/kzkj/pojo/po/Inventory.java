package com.kzkj.pojo.po;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@TableName("tb_inventory")
public class Inventory extends Model<Inventory> {
    private Integer id;

    private String guid;

    private String appType;

    private String appTime;

    private String appStatus;

    private String customsCode;

    private String ebpCode;

    private String ebpName;

    private String orderNo;

    private String logisticsCode;

    private String logisticsName;

    private String logisticsNo;

    private String copNo;

    private String preNo;

    private String invtNo;

    private String ieFlag;

    private String portCode;

    private String ieDate;

    private String statisticsFlag;

    private String agentCode;

    private String agentName;

    private String ebcCode;

    private String ebcName;

    private String ownerCode;

    private String ownerName;

    private String iacCode;

    private String iacName;

    private String emsNo;

    private String tradeMode;

    private String trafMode;

    private String trafName;

    private String voyageNo;

    private String billNo;

    private String totalPackageNo;

    private String loctNo;

    private String licenseNo;

    private String country;

    private String pod;

    private BigDecimal freight;

    private String fCurrency;

    private String fFlag;

    private BigDecimal insuredFee;

    private String iCurrency;

    private String iFlag;

    private String wrapType;

    private Integer packNo;

    private BigDecimal grossWeight;

    private BigDecimal netWeight;

    private String note;

    private String returnStatus;

    private String returnInfo;

    private String returnTime;

    @TableField(exist= false)
    private List<InventoryDetail> inventoryDetailList;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}