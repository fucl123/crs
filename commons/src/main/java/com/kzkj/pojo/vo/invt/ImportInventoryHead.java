package com.kzkj.pojo.vo.invt;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="InventoryHead")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
	    "appType",
	    "appTime",
	    "appStatus",
	    "orderNo",
	    "ebpCode",
	    "ebpName",
	    "ebcCode",
	    "ebcName",
	    "logisticsNo",
	    "logisticsCode",
	    "logisticsName",
	    "copNo",
	    "preNo",
	    "assureCode",
	    "emsNo",
	    "invtNo",
	    "ieFlag",
	    "declTime",
	    "customsCode",
	    "portCode",
	    "ieDate",
	    "buyerIdType",
	    "buyerIdNumber",
	    "buyerName",
	    "buyerTelephone",
	    "consigneeAddress",
	    "agentCode",
	    "agentName",
	    "areaCode",
	    "areaName",
	    "tradeMode",
	    "trafMode",
	    "trafNo",
	    "voyageNo",
	    "billNo",
	    "loctNo",
	    "licenseNo",
	    "country",
	    "freight",
	    "insuredFee",
	    "currency",
	    "wrapType",
	    "packNo",
	    "grossWeight",
	    "netWeight",
	    "note"
})
@Data
public class ImportInventoryHead implements Serializable{

	private static final long serialVersionUID = 4115228398731660386L;
	
	private String guid;
    private String appType;
    private String appTime;
    private String appStatus;
    private String orderNo;
    private String ebpCode;
    private String ebpName;
    private String ebcCode;
    private String ebcName;
    private String logisticsNo;
    private String logisticsCode;
    private String logisticsName;
    private String copNo;
    private String preNo;
    private String assureCode;
    private String emsNo;
    private String invtNo;
    private String ieFlag;
    private String declTime;
    private String customsCode;
    private String portCode;
    private String ieDate;
    private String buyerIdType;
    private String buyerIdNumber;
    private String buyerName;
    private String buyerTelephone;
    private String consigneeAddress;
    private String agentCode;
    private String agentName;
    private String areaCode;
    private String areaName;
    private String tradeMode;
    private String trafMode;
    private String trafNo;
    private String voyageNo;
    private String billNo;
    private String loctNo;
    private String licenseNo;
    private String country;
    private String freight;
    private String insuredFee;
    private String currency;
    private String wrapType;
    private String packNo;
    private String grossWeight;
    private String netWeight;
	private String note;
	
	public void setFreight(String freight) {
		this.freight= (double)Integer.valueOf(freight)/1000+"";
	}
	public void setInsuredFee(String insuredFee) {
		this.insuredFee= (double)Integer.valueOf(insuredFee)/1000+"";
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight= (double)Integer.valueOf(grossWeight)/1000+"";
	}
	public void setNetWeight(String netWeight) {
		this.netWeight= (double)Integer.valueOf(netWeight)/1000+"";
	}
    
}
