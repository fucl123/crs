package com.kzkj.pojo.vo.logistics;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识
@XmlRootElement(name="LogisticsHead")
//控制JAXB 绑定类中属性和字段的排序
@XmlType(propOrder = {
        "guid",
        "appType",
        "appTime",
        "appStatus",
        "logisticsCode",
        "logisticsName",
        "logisticsNo",
        "billNo",//新增
        "orderNo",//新增
        "freight",
        "insuredFee",
        "currency",
        "weight",
        //"grossWeight",
        "packNo",
        "goodsInfo",

        "consignee",
        "consigneeAddress",
        "consigneeTelephone",

        "note",
})
@Data
public class ImportLogisticsHead
{
    private String guid;
    private String appType;
    private String appTime;
    private String appStatus;
    private String logisticsCode;
    private String logisticsName;
    private String logisticsNo;
    private String billNo;//新增
    private String orderNo;//新增
    private String freight;
    private String insuredFee;
    private String currency;
    private String weight;
    private String packNo;
    //9610 必填
    private String goodsInfo;

    //private String consingee; //1210
    //9610 必填
    private String consignee;//9610

    private String consigneeAddress;
    private String consigneeTelephone;
    private String note;
}