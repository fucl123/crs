package com.kzkj.pojo.vo.summaryResult;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB792Message")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "SummaryResult",   
})
@Data
public class CEB792Message implements Serializable{

	private static final long serialVersionUID = 2549245859588443092L;
	
	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";
	
	@XmlElement
	private List<SummaryResult> SummaryResult;

}
