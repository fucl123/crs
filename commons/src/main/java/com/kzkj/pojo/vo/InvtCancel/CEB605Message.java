package com.kzkj.pojo.vo.InvtCancel;

import com.kzkj.pojo.vo.base.BaseTransfer;

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
	
	private List<InvtCancel> InvtCancel;
	
	private BaseTransfer BaseTransfer;


	public List<InvtCancel> getInvtCancel() {
		return InvtCancel;
	}


	public void setInvtCancel(List<InvtCancel> invtCancel) {
		InvtCancel = invtCancel;
	}


	public BaseTransfer getBaseTransfer() {
		return BaseTransfer;
	}


	public void setBaseTransfer(BaseTransfer baseTransfer) {
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
