package com.kzkj.pojo.vo.tax;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="TaxListRd")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"gnum",
		"gcode",
		"taxPrice",
		"customsTax",
		"valueAddedTax",
		"consumptionTax"
})
@Data
public class TaxListRd implements Serializable {

	private static final long serialVersionUID = 7108494143728359346L;
	
	private String gnum;
	private String gcode;
	private String taxPrice;
	private String customsTax;
	private String valueAddedTax;
	private String consumptionTax;
}
