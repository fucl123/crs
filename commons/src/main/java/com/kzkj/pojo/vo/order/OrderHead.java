package com.kzkj.pojo.vo.order;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="OrderHead")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		  "guid",
	      "appType",
	      "appTime",
	      "appStatus",
	      "orderType",
	      "orderNo",
	      "ebpCode",
	      "ebpName",
	      "ebcCode",
	      "ebcName",
	      "goodsValue",
	      "freight",
	      "currency",
	      "note",
})  
@Data
public class OrderHead {
	private String guid;
	
    private String appType;
    
    private String appTime;
    
    private String appStatus;
    
    private String orderType;
    
    private String orderNo;
    
    private String ebpCode;
    
    private String ebpName;
    
    private String ebcCode;
    
    private String ebcName;
    
    private String goodsValue;
    
    private String freight;
    
    private String currency;
    
    private String note;
    
}

