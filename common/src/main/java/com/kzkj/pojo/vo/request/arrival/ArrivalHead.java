package com.kzkj.pojo.vo.request.arrival;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="ArrivalList")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"appType",
		"appTime",
		"appStatus",
		"customsCode",
		"copNo",
		"operatorCode",
		"operatorName",
		"loctNo",
		"ieFlag",
		"trafMode",
		"billNo",
		"domesticTrafNo",
		"logisticsCode",
		"logisticsName",
		"msgCount",
		"msgSeqNo",
		"note"
}) 
@Data
public class ArrivalHead implements Serializable{


	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 * @since 2018年7月18日 下午3:24:26
	 * @author 张爽
	 */
	private static final long serialVersionUID = -5437217472423533161L;
	
	private String guid;
    private String appType;
    private String appTime;
    private String appStatus;
    private String customsCode;
    private String copNo;
    private String operatorCode;
    private String operatorName;
    private String loctNo;
    private String ieFlag;
    private String trafMode;
    private String billNo;
    private String domesticTrafNo;
    private String logisticsCode;
    private String logisticsName;
    private Integer msgCount;
    private String msgSeqNo;
    private String note;
}