package com.kzkj.pojo.vo.response.logisticsStatus;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="LogisticsStatusReturn")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	    "guid",
	    "logisticsCode",
	    "logisticsNo",
	    "logisticsStatus",
	    "returnStatus",
	    "returnTime",
	    "returnInfo",
})  
@Data
public class LogisticsStatusReturn
{
	private String guid;
    private String logisticsCode;
    private String logisticsNo;
    private String logisticsStatus;
    private String returnStatus;
    private String returnTime;
    private String returnInfo;
}
