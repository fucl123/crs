package com.kzkj.pojo.vo.request.InvtCancel;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * 发送总署删单请求对象
 * <br>（功能详细描述）
 */

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB605Message")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
   "InvtCancel",   
   "BaseTransfer",   
}) 
public class CEB605Message implements Serializable{

	

	private static final long serialVersionUID = 8892979421531457108L;

	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";
	
	private List<com.kzkj.pojo.vo.request.InvtCancel.InvtCancel> InvtCancel;

	private com.kzkj.pojo.vo.request.base.BaseTransfer BaseTransfer;


	public List<com.kzkj.pojo.vo.request.InvtCancel.InvtCancel> getInvtCancel() {
		return InvtCancel;
	}


	public void setInvtCancel(List<com.kzkj.pojo.vo.request.InvtCancel.InvtCancel> invtCancel) {
		InvtCancel = invtCancel;
	}


	public com.kzkj.pojo.vo.request.base.BaseTransfer getBaseTransfer() {
		return BaseTransfer;
	}


	public void setBaseTransfer(com.kzkj.pojo.vo.request.base.BaseTransfer baseTransfer) {
		BaseTransfer = baseTransfer;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getGuid() {
		return guid;
	}


	public void setGuid(String guid) {
		this.guid = guid;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}
	
	

}
