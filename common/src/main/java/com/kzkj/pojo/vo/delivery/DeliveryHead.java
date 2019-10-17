package com.kzkj.pojo.vo.delivery;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="DeliveryHead")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"appType",
		"appTime",
		"appStatus",
		"customsCode",
		"copNo",
		"preNo",
		"rkdNo",
		"operatorCode",
		"operatorName",
		"ieFlag",
		"trafMode",
		"trafNo",
		"voyageNo",
		"billNo",
		"logisticsCode",
		"logisticsName",
		"unloadLocation",
		"note",
})
@Data
public class DeliveryHead implements Serializable {

	private static final long serialVersionUID = -7906337430910775621L;
	
	private String guid;
	private String appType;
	private String appTime;
	private String appStatus;
	private String customsCode;
	private String copNo;
	private String preNo;
	private String rkdNo;
	private String operatorCode;
	private String operatorName;
	private String ieFlag;
	private String trafMode;
	private String trafNo;
	private String voyageNo;
	private String billNo;
	private String logisticsCode;
	private String logisticsName;
	private String unloadLocation;
	private String note;
}
