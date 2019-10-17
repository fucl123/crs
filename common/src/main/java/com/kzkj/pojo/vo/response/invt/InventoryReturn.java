package com.kzkj.pojo.vo.response.invt;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="InventoryReturn")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		    "guid",
		    "agentCode",
		    "copNo",
		    "preNo",
		    "invtNo",
		    "ebpCode",
		    "ebcCode",
		    "orderNo",
		    "statisticsFlag",
		    "returnStatus",
		    "returnTime",
		    "returnInfo",
})
@Data
public class InventoryReturn implements Serializable{

	private static final long serialVersionUID = 3189629655583439821L;
	
    private String guid;
    private String agentCode;
    private String copNo;
    private String preNo;
    private String invtNo;
    private String ebpCode;
    private String ebcCode;
    private String orderNo;
    private String statisticsFlag;
    private String returnStatus;
    private String returnTime;
    private String returnInfo;
    
}
