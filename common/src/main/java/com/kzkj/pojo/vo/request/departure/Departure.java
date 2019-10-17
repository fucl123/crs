package com.kzkj.pojo.vo.request.departure;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="Departure")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"DepartureHead",
	"DepartureList",
}) 
@Data
public class Departure implements Serializable{
	private static final long serialVersionUID = -7576989521818540863L;
	
	private com.kzkj.pojo.vo.request.departure.DepartureHead DepartureHead;

	private List<com.kzkj.pojo.vo.request.departure.DepartureList> DepartureList;

}
