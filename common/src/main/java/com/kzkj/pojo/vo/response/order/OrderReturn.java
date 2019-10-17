package com.kzkj.pojo.vo.response.order;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="OrderReturn")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	    "guid",
	    "ebpCode",
	    "ebcCode",
	    "orderNo",
	    "returnStatus",
	    "returnTime",
	    "returnInfo",
})  
@Data
public class OrderReturn implements Serializable {

	private static final long serialVersionUID = 7088551939878985071L;
	
    private String guid;
    private String ebpCode;
    private String ebcCode;
    private String orderNo;
    private String returnStatus;
    private String returnTime;
    private String returnInfo;
    
}
