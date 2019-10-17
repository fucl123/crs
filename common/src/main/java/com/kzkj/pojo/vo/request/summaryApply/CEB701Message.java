package com.kzkj.pojo.vo.request.summaryApply;

import com.kzkj.pojo.vo.request.base.BaseTransfer;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB701Message")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "SummaryApply",   
     "BaseTransfer",   
})
@Data
public class CEB701Message implements Serializable{

	private static final long serialVersionUID = 2549245859588443092L;
	
	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";
	
	@XmlElement
	private List<com.kzkj.pojo.vo.request.summaryApply.SummaryApply> SummaryApply;
	
	private BaseTransfer BaseTransfer;

}
