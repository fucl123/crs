package com.kzkj.pojo.vo.logistics;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="Logistics")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"appType",
		"appTime",
		"appStatus",
		"logisticsCode",
		"logisticsName",
		"logisticsNo",
		"freight",
		"insuredFee",
		"currency",
		"grossWeight",
		"packNo",
		"goodsInfo",
		"ebcCode",
		"ebcName",
		"ebcTelephone",
		"note",
})
@Data
public class Logistics
{
	private String guid;
	private String appType;
	private String appTime;
	private String appStatus;
	private String logisticsCode;
	private String logisticsName;
	private String logisticsNo;
	private String freight;
	private String insuredFee;
	private String currency;
	private String grossWeight;
	private String packNo;
	private String goodsInfo;
	private String ebcCode;
	private String ebcName;
	private String ebcTelephone;
	private String note;
}
