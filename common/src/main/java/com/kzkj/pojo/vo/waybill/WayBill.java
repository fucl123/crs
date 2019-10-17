package com.kzkj.pojo.vo.waybill;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="WayBill")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"WayBillHead",
	"WayBillList",
}) 
@Data
public class WayBill implements Serializable {

	private static final long serialVersionUID = 6657015019468573418L;
	
	private WayBillHead WayBillHead;
	
	private List<WayBillList> WayBillList;
}
