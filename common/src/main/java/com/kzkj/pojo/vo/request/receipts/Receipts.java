package com.kzkj.pojo.vo.request.receipts;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="Receipts")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"appType",
		"appTime",
		"appStatus",
		"ebpCode",
		"ebpName",
		"ebcCode",
		"ebcName",
		"orderNo",
		"payCode",
		"payName",
		"payNo",
		"charge",
		"currency",
		"accountingDate",
		"note",
})
@Data
public class Receipts
{
	private String guid;
	private String appType;
	private String appTime;
	private String appStatus;
	private String ebpCode;
	private String ebpName;
	private String ebcCode;
	private String ebcName;
	private String orderNo;
	private String payCode;
	private String payName;
	private String payNo;
	private String charge;
	private String currency;
	private String accountingDate;
	private String note;
}
