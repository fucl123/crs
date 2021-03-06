package com.kzkj.pojo.vo.request.invt;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="Inventory")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"InventoryHead",
	"InventoryList",
}) 
@Data
public class Inventory implements Serializable{

	private static final long serialVersionUID = 1063635751093011504L;

	private com.kzkj.pojo.vo.request.invt.InventoryHead InventoryHead;

	private List<com.kzkj.pojo.vo.request.invt.InventoryList> InventoryList;
	
}
