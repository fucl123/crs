package com.kzkj.pojo.vo.request.waybill;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="WayBillList")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		  "gnum",
		  "totalPackageNo",
		  "logisticsNo",
	      "note",
		
})
@Data
public class WayBillList implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	private String gnum;
	private String totalPackageNo;
	private String logisticsNo;
	private String note;

}
