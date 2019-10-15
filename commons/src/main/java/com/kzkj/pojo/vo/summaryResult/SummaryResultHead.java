package com.kzkj.pojo.vo.summaryResult;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="SummaryResultHead")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"customsCode",
		"sumNo",
		"opDate",
		"declSeqNo",
		"decState",
		"agentCode",
		"agentName",
		"ebcCode",
		"ebcName",
		"declAgentCode",
		"declAgentName",
		"grossWeight",
		"netWeight",
		"msgCount",
		"msgSeqNo",
})  
@Data
public class SummaryResultHead
{
	private String guid;
	private String customsCode;
	private String sumNo;
	private String opDate;
	private String declSeqNo;
	private String decState;
	private String agentCode;
	private String agentName;
	private String ebcCode;
	private String ebcName;
	private String declAgentCode;
	private String declAgentName;
	private String grossWeight;
	private String netWeight;
	private String msgCount;
	private String msgSeqNo;
}
