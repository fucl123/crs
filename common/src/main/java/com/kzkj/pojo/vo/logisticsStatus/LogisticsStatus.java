package com.kzkj.pojo.vo.logisticsStatus;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="LogisticsStatus")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"appType",
		"appTime",
		"appStatus",
		"logisticsCode",
		"logisticsName",
		"logisticsNo",
		"logisticsStatus",
		"logisticsTime",
		"note",
})
@Data
public class LogisticsStatus {

	private String guid;
	private String appType;
	private String appTime;
	private String appStatus;
	private String logisticsCode;
	private String logisticsName;
	private String logisticsNo;
	private String logisticsStatus;
	private String logisticsTime;
	private String note;
}
