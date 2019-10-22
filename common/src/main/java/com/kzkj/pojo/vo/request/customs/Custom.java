package com.kzkj.pojo.vo.request.customs;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="DxpMsg")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
"TransInfo",   
"Data",   
}) 
public class Custom implements Serializable{

	private static final long serialVersionUID = -1339250052858321681L;
	
	@XmlAttribute
    private String ver = "1.0";
	
	private CustomTransInfo TransInfo;

	private String Data;

	public CustomTransInfo getTransInfo() {
		return TransInfo;
	}

	public void setTransInfo(CustomTransInfo transInfo) {
		TransInfo = transInfo;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}
	
	

}
