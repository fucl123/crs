package com.kzkj.pojo.vo.departure;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="DepartureHead")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"appType",
		"appTime",
		"appStatus",
		"customsCode",
		"copNo",
		"logisticsCode",
		"logisticsName",
		"trafMode",
		"trafName",
		"voyageNo",
		"billNo",
		"leaveTime",
		"msgCount",
		"msgSeqNo",
		"note",
}) 
@Data
public class DepartureHead implements Serializable{

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 * @since 2018年7月31日 下午3:00:01
	 * @author 张爽
	 */
	private static final long serialVersionUID = 2158894921038550426L;
	private String guid;
	private String appType;
	private String appTime;
	private String appStatus;
	private String customsCode;
	private String copNo;
	private String logisticsCode;
	private String logisticsName;
	private String trafMode;
	private String trafName;
	private String voyageNo;
	private String billNo;
	private String leaveTime;
	private Integer msgCount;
	private String msgSeqNo;
	private String note;
}