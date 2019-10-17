package com.kzkj.pojo.vo.summaryResult;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="SummaryResult")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"SummaryResultHead",
	"SummaryResultList",
}) 
@Data
public class SummaryResult implements Serializable {

	private static final long serialVersionUID = 6657015019468573418L;
	
	private SummaryResultHead SummaryResultHead;
	
	private List<SummaryResultList> SummaryResultList;
}
