package com.kzkj.pojo.vo.invtRefund;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="InvtRefund")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"InvtRefundHead",
	"InvtRefundList",
}) 
@Data
public class InvtRefund implements Serializable {

	private static final long serialVersionUID = -6761950289491608709L;

	private InvtRefundHead InvtRefundHead;
	
	private List<InvtRefundList> InvtRefundList;
}
