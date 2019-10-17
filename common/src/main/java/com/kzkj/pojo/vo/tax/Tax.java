package com.kzkj.pojo.vo.tax;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="Tax")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"TaxHeadRd",
	"TaxListRd",
}) 
@Data
public class Tax implements Serializable {

	private static final long serialVersionUID = 5667871416017710142L;

	private TaxHeadRd TaxHeadRd;
	
	private List<TaxListRd> TaxListRd;
}
