package com.kzkj.pojo.vo.request.arrival;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="Arrival")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"ArrivalHead",
	"ArrivalList",
}) 
@Data
public class Arrival implements Serializable{/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 * @since 2018年7月18日 下午3:17:20
	 * @author 张爽
	 */
	private static final long serialVersionUID = -7576989521818540863L;
	
	private com.kzkj.pojo.vo.request.arrival.ArrivalHead ArrivalHead;

	private List<com.kzkj.pojo.vo.request.arrival.ArrivalList> ArrivalList;

}
