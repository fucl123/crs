package com.kzkj.pojo.vo.response.customs;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * 
 * （发送海关终端报文）
 * <br>（功能详细描述）
 * @author 张爽
 * @version V1.0
 * @see [相关类/方法]
 * @since 2017年9月14日 下午4:01:31
 */
@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="DxpMsg",namespace="http://www.chinaport.gov.cn/dxp")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
"TransInfo",   
"Data",   
}) 
public class CustomResponse implements Serializable{

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
