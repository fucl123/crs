package com.kzkj.pojo.vo.request.waybill;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="WayBillHead")  
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
		"loctNo",
		"trafMode",
		"trafName",
		"voyageNo",
		"billNo",
		"domesticTrafNo",
		"grossWeight",
		"logisticsCode",
		"logisticsName",
		"msgCount",
		"msgSeqNo",
		"note",
})
@Data
public class WayBillHead implements Serializable{

	private static final long serialVersionUID = -8607382274363193987L;
	private String guid;
	private String appType;
	private String appTime;
	private String appStatus;
	private String customsCode;
	private String copNo;
	private String agentCode;
	private String agentName;
	private String loctNo;
	private String trafMode;
	private String trafName;
	private String voyageNo;
	private String billNo;
	private String domesticTrafNo;
	private String grossWeight;
	private String logisticsCode;
	private String logisticsName;
	private String msgCount;
	private String msgSeqNo;
	private String note;
	
	
}
