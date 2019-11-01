package com.kzkj.pojo.vo.request.order;

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
		"discount",
		"taxTotal",
		"acturalPaid",
		"buyerRegNo",
		"buyerName",
		"buyerTelephone",
		"buyerIdType",
		"buyerIdNumber",
		"payCode",
		"payName",
		"payTransactionId",
		"batchNumbers",
		"consignee",
		"consigneeTelephone",
		"consigneeAddress",
		"consigneeDistrict",
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

    //进口字段
    private String discount;

    private String taxTotal;

	private String acturalPaid;

	private String buyerRegNo;

	private String buyerName;

	private String buyerTelephone;

	private String buyerIdType;

	private String buyerIdNumber;

	private String payCode;

	private String payName;

	private String payTransactionId;

	private String batchNumbers;

	private String consignee;

	private String consigneeTelephone;

	private String consigneeAddress;

	private String consigneeDistrict;

}

