package com.kzkj.pojo.vo.response.summaryResult;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "SummaryReturn")
// 控制JAXB 绑定类中属性和字段的排序
@XmlType(propOrder =
{
		"guid",
		"agentCode",
		"ebcCode",
		"copNo",
		"preNo",
		"sumNo",
		"msgSeqNo",
		"returnStatus",
		"returnTime",
		"returnInfo",
})
@Data
public class SummaryReturn implements Serializable
{
	private static final long serialVersionUID = 3189629655583439821L;

	private String guid;
	private String agentCode;
	private String ebcCode;
	private String copNo;
	private String preNo;
	private String sumNo;
	private String msgSeqNo;
	private String returnStatus;
	private String returnTime;
	private String returnInfo;
}
