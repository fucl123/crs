package com.kzkj.pojo.vo.response.arrival;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="ArrivalReturn")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"operatorCode",
		"logisticsCode",
		"preNo",
		"copNo",
		"billNo",
		"msgSeqNo",
		"returnStatus",
		"returnTime",
		"returnInfo"
})  
@Data
public class ArrivalReturn implements Serializable {

	private static final long serialVersionUID = 7088551939878985071L;
	
	private String guid;
	private String operatorCode;
	private String logisticsCode;
	private String preNo;
	private String copNo;
	private String billNo;
	private String msgSeqNo;
	private String returnStatus;
	private String returnTime;
	private String returnInfo;
    
}
