package com.kzkj.pojo.vo.request.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="BaseTransfer")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		  "copCode",
	      "copName",
	      "dxpMode",
	      "dxpId",
	      "note",
})
public class BaseTransfer implements Serializable{

	private static final long serialVersionUID = -8963266361004920016L;

	private String copCode;

	private String copName;

	private String dxpMode;

	private String dxpId;

	private String note;

	public String getCopCode() {
		return copCode;
	}

	public void setCopCode(String copCode) {
		this.copCode = copCode;
	}

	public String getCopName() {
		return copName;
	}

	public void setCopName(String copName) {
		this.copName = copName;
	}

	public String getDxpMode() {
		return dxpMode;
	}

	public void setDxpMode(String dxpMode) {
		this.dxpMode = dxpMode;
	}

	public String getDxpId() {
		return dxpId;
	}

	public void setDxpId(String dxpId) {
		this.dxpId = dxpId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


    

	
}
