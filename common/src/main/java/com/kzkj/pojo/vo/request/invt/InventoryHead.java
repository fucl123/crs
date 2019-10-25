package com.kzkj.pojo.vo.request.invt;

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
	    "customsCode",
	    "ebpCode",
	    "ebpName",
	    "orderNo",
	    "logisticsCode",
	    "logisticsName",
	    "logisticsNo",
	    "copNo",
	    "ieFlag",
	    "portCode",
	    "ieDate",
	    "statisticsFlag",
	    "agentCode",
	    "agentName",
	    "ebcCode",
	    "ebcName",
	    "ownerCode",
	    "ownerName",
	    "iacCode",
	    "iacName",
	    "emsNo",
	    "tradeMode",
	    "trafMode",
	    "trafName",
	    "voyageNo",
	    "billNo",
	    "totalPackageNo",
	    "loctNo",
	    "licenseNo",
	    "country",
	    "POD",
	    "freight",
	    "fCurrency",
	    "fFlag",
	    "insuredFee",
	    "iCurrency",
	    "iFlag",
	    "wrapType",
	    "packNo",
	    "grossWeight",
	    "netWeight",
		"note",
		"invtNo",
        "preNo",
})
@Data
public class InventoryHead implements Serializable{

	private static final long serialVersionUID = -8607382274363193987L;
	private String guid;
    private String appType;
    private String appTime;
    private String appStatus;
    private String customsCode;
    private String ebpCode;
    private String ebpName;
    private String orderNo;
    private String logisticsCode;
    private String logisticsName;
    private String logisticsNo;
    private String copNo;
    private String ieFlag;
    private String portCode;
    private String ieDate;
    private String statisticsFlag;
    private String agentCode;
    private String agentName;
    private String ebcCode;
    private String ebcName;
    private String ownerCode;
    private String ownerName;
    private String iacCode;
    private String iacName;
    private String emsNo;
    private String tradeMode;
    private String trafMode;
    private String trafName;
    private String voyageNo;
    private String billNo;
    private String totalPackageNo;
    private String loctNo;
    private String licenseNo;
    private String country;
    private String POD;
    private String freight;
    private String fCurrency;
    private String fFlag;
    private String insuredFee;
    private String iCurrency;
    private String iFlag;
    private String wrapType;
    private String packNo;
    private String grossWeight;
    private String netWeight;
	private String note;
    //非xml字段
	private String invtNo;
	private String preNo;
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
