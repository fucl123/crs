package com.kzkj.pojo.vo.request.delivery;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="Delivery")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"DeliveryHead",
	"DeliveryList",
}) 
@Data
public class Delivery implements Serializable {

	private static final long serialVersionUID = -6859738510981018133L;

	private com.kzkj.pojo.vo.request.delivery.DeliveryHead DeliveryHead;

	private List<com.kzkj.pojo.vo.request.delivery.DeliveryList> DeliveryList;
}
