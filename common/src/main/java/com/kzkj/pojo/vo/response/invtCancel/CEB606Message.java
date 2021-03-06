package com.kzkj.pojo.vo.response.invtCancel;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * 接收总署删单申请回执对象
 */
@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB606Message",namespace="http://www.chinaport.gov.cn/ceb")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "InvtCancelReturn",   
}) 
@Data
public class CEB606Message implements Serializable {

	private static final long serialVersionUID = -1001883123432048040L;

	@XmlAttribute
    private String xmlns;
	
	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version;
	
	private List<InvtCancelReturn> InvtCancelReturn ;
	

}
