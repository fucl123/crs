package com.kzkj.pojo.vo.request.customs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="ReceiverIds")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		  "ReceiverId",
})
public class ReceiverIds implements Serializable{

	private static final long serialVersionUID = -7900511907640738255L;
	
	private List<String> ReceiverId;

	public List<String> getReceiverId() {
		return ReceiverId;
	}

	public void setReceiverId(List<String> receiverId) {
		ReceiverId = receiverId;
	}
	
	

}
