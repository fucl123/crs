package com.kzkj.pojo.vo.request.invtRefund;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="InvtRefundList")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		  "gnum",
		  "gcode",
		  "gname",
		  "qty",
	      "unit",
	      "note"	
})
@Data
public class InvtRefundList implements Serializable {

	private static final long serialVersionUID = -8648875203852050030L;

	private String gnum;
	private String gcode;
	private String gname;
	private String qty;
	private String unit;
	private String note;
}
