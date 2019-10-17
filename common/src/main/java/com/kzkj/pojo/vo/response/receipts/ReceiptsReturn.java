package com.kzkj.pojo.vo.response.receipts;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="ReceiptsReturn")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	    "guid",
	    "ebcCode",
	    "orderNo",
	    "payNo",
	    "returnStatus",
	    "returnTime",
	    "returnInfo",
})  
@Data
public class ReceiptsReturn
{
	private String guid;
    private String ebcCode;
    private String orderNo;
    private String payNo;
    private String returnStatus;
    private String returnTime;
    private String returnInfo;
}
