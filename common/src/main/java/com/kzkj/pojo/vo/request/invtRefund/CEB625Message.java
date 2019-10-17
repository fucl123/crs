package com.kzkj.pojo.vo.request.invtRefund;


import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * 发送总署退货申请请求对象
 * <br>（功能详细描述）
 */

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB625Message")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
   "InvtRefund",   
   "BaseTransfer",   
}) 
@Data
public class CEB625Message implements Serializable {

	private static final long serialVersionUID = 4661480495963476188L;

	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";
	
	private List<com.kzkj.pojo.vo.request.invtRefund.InvtRefund> InvtRefund;
	
	private com.kzkj.pojo.vo.request.base.BaseTransfer BaseTransfer;
}
