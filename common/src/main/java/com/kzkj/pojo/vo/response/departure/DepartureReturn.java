package com.kzkj.pojo.vo.response.departure;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="DepartureReturn")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"logisticsCode",
		"copNo",
		"preNo",
		"msgSeqNo",
		"returnStatus",
		"returnTime",
		"returnInfo"
})  
@Data
public class DepartureReturn implements Serializable {

	private static final long serialVersionUID = 3912439345454321414L;
	
	private String guid;
	private String logisticsCode;
	private String copNo;
	private String preNo;
	private String msgSeqNo;
	private String returnStatus;
	private String returnTime;
	private String returnInfo;
    
}
