package com.kzkj.pojo.vo.summaryApply;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="SummaryApplyHead")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"appType",
		"appTime",
		"appStatus",
		"customsCode",
		"copNo",
		"agentCode",
		"agentName",
		"ebcCode",
		"ebcName",
		"declAgentCode",
		"declAgentName",
		"summaryFlag",
		"itemNameFlag",
		"msgCount",
		"msgSeqNo",
})  
@Data
public class SummaryApplyHead
{
	private String guid;
	private String appType;
	private String appTime;
	private String appStatus;
	private String customsCode;
	private String copNo;
	private String agentCode;
	private String agentName;
	private String ebcCode;
	private String ebcName;
	private String declAgentCode;
	private String declAgentName;
	private String summaryFlag;
	private String itemNameFlag;
	private String msgCount;
	private String msgSeqNo;
}
