package com.kzkj.pojo.vo.request.taxStatus;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="TaxHeadStatus")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"returnTime",
		"invtNo",
		"taxNo",
		"status",
		"entDutyNo",
		"assureCode",
})
@Data
public class TaxHeadStatus implements Serializable {
	
	private static final long serialVersionUID = 5559104438268033506L;
	
	private String guid;
	private String returnTime;
	private String invtNo;
	private String taxNo;
	private String status;
	private String entDutyNo;
	private String assureCode;
}
