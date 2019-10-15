package com.kzkj.pojo.vo.delivery;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="DeliveryList")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"logisticsNo",
		"note",
})
@Data
public class DeliveryList implements Serializable {

	private static final long serialVersionUID = 3318789164426748254L;
	
	private String logisticsNo;
	private String note;
}
