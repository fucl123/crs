package com.kzkj.pojo.vo.request.delivery;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import com.kzkj.pojo.vo.request.base.BaseTransfer;
/**
 * 
 * 发送总署入库明细申请对象
 * <br>（功能详细描述）
 */

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB711Message")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
   "Delivery",   
   "BaseTransfer",   
}) 
@Data
public class CEB711Message implements Serializable {

	private static final long serialVersionUID = 4031344973031588107L;

	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";
	
	private List<com.kzkj.pojo.vo.request.delivery.Delivery> Delivery;
	
	private BaseTransfer BaseTransfer;
}
