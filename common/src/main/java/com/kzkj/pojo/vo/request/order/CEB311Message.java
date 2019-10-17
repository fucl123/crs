package com.kzkj.pojo.vo.request.order;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

;

/**
 * 
 * 发送总署进口订单请求对象
 * <br>（功能详细描述）
 */


@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB311Message")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "Order",   
     "BaseTransfer",   
})
@Data
public class CEB311Message implements Serializable {

	private static final long serialVersionUID = 199988981944529907L;

	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";
	
	@XmlElement
	private List<com.kzkj.pojo.vo.request.order.Order> Order;
	
	private com.kzkj.pojo.vo.request.base.BaseTransfer BaseTransfer;
}
