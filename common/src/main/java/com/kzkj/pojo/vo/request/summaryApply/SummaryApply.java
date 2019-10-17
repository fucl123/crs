package com.kzkj.pojo.vo.request.summaryApply;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="SummaryApply")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"SummaryApplyHead",
	"SummaryApplyList",
}) 
@Data
public class SummaryApply implements Serializable {

	private static final long serialVersionUID = 6657015019468573418L;
	
	private com.kzkj.pojo.vo.request.summaryApply.SummaryApplyHead SummaryApplyHead;

	private List<com.kzkj.pojo.vo.request.summaryApply.SummaryApplyList> SummaryApplyList;
}

