package com.kzkj.pojo.vo.response.delivery;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="DeliveryReturn")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"customsCode",
		"operatorCode",
		"copNo",
		"preNo",
		"rkdNo",
		"returnStatus",
		"returnTime",
		"returnInfo"
})  
@Data
public class DeliveryReturn implements Serializable {
	
	private static final long serialVersionUID = 1188739444058420330L;
	
	private String guid;
	private String customsCode;
	private String operatorCode;
	private String copNo;
	private String preNo;
	private String rkdNo;
	private String returnStatus;
	private String returnTime;
	private String returnInfo;
    
}
