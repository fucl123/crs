package com.kzkj.pojo.vo.request.taxStatus;

import com.kzkj.pojo.vo.request.base.BaseTransfer;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

;

/**
 * 
 * 发送总署入库明细申请对象
 * <br>（功能详细描述）
 */

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB818Message")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
   "Tax",   
   "BaseTransfer",   
}) 
@Data
public class CEB818Message implements Serializable {

	private static final long serialVersionUID = -3890500874362808083L;

	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";
	
	private List<com.kzkj.pojo.vo.request.taxStatus.Tax> Tax;
	
	private BaseTransfer BaseTransfer;
}
