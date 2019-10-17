package com.kzkj.pojo.vo.tax;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="TaxHeadRd")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"returnTime",
		"invtNo",
		"taxNo",
		"customsTax",
		"valueAddedTax",
		"consumptionTax",
		"status",
		"entDutyNo",
		"note",
		"assureCode",
		"ebcCode",
		"logisticsCode",
		"agentCode",
		"customsCode",
})
@Data
public class TaxHeadRd implements Serializable {
	
	private static final long serialVersionUID = 1158480765468201971L;
	
	private String guid;
	private String returnTime;
	private String invtNo;
	private String taxNo;
	private String customsTax;
	private String valueAddedTax;
	private String consumptionTax;
	private String status;
	private String entDutyNo;
	private String note;
	private String assureCode;
	private String ebcCode;
	private String logisticsCode;
	private String agentCode;
	private String customsCode;
}
