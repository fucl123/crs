package com.kzkj.pojo.vo.request.invtRefund;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="InvtRefundHead")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"appType",
		"appTime",
		"appStatus",
		"customsCode",
		"orderNo",
		"ebpCode",
		"ebpName",
		"ebcCode",
		"ebcName",
		"logisticsNo",
		"logisticsCode",
		"logisticsName",
		"copNo",
		"preNo",
		"invtNo",
		"buyerIdType",
		"buyerIdNumber",
		"buyerName",
		"buyerTelephone",
		"agentCode",
		"agentName",
		"reason",
		"note",
})
@Data
public class InvtRefundHead implements Serializable {

	private static final long serialVersionUID = 4626828608313424281L;

	private String guid;
	private String appType;
	private String appTime;
	private String appStatus;
	private String customsCode;
	private String orderNo;
	private String ebpCode;
	private String ebpName;
	private String ebcCode;
	private String ebcName;
	private String logisticsNo;
	private String logisticsCode;
	private String logisticsName;
	private String copNo;
	private String preNo;
	private String invtNo;
	private String buyerIdType;
	private String buyerIdNumber;
	private String buyerName;
	private String buyerTelephone;
	private String agentCode;
	private String agentName;
	private String reason;
	private String note;
}
