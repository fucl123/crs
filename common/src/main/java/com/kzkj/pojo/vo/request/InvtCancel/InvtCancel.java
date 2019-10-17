package com.kzkj.pojo.vo.request.InvtCancel;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="InvtCancel")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		"guid",
		"appType",
		"appTime",
		"appStatus",
		"customsCode",
		"copNo",
		"preNo",
		"invtNo",
		"reason",
		"agentCode",
		"agentName",
		"ebcCode",
		"ebcName",
		"note"
})
public class InvtCancel implements Serializable{

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 * @since 2018年7月24日 下午9:39:08
	 * @author 张爽
	 */
	private static final long serialVersionUID = 928666887446619960L;
	private String guid;
	private String appType;
	private String appTime;
	private String appStatus;
	private String customsCode;
	private String copNo;
	private String preNo;
	private String invtNo;
	private String reason;
	private String agentCode;
	private String agentName;
	private String ebcCode;
	private String ebcName;
	private String note;
	
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getAppTime() {
		return appTime;
	}
	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	public String getCustomsCode() {
		return customsCode;
	}
	public void setCustomsCode(String customsCode) {
		this.customsCode = customsCode;
	}
	public String getEbcCode() {
		return ebcCode;
	}
	public void setEbcCode(String ebcCode) {
		this.ebcCode = ebcCode;
	}
	public String getEbcName() {
		return ebcName;
	}
	public void setEbcName(String ebcName) {
		this.ebcName = ebcName;
	}
	public String getCopNo() {
		return copNo;
	}
	public void setCopNo(String copNo) {
		this.copNo = copNo;
	}
	public String getPreNo() {
		return preNo;
	}
	public void setPreNo(String preNo) {
		this.preNo = preNo;
	}
	public String getInvtNo() {
		return invtNo;
	}
	public void setInvtNo(String invtNo) {
		this.invtNo = invtNo;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}
