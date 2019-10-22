package com.kzkj.pojo.vo.request.order;

import com.kzkj.pojo.vo.request.base.BaseTransfer;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * 发送总署订单请求对象
 * <br>（功能详细描述）
 */


@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB303Message")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "Order",   
     "BaseTransfer",
})
@Data
public class CEB303Message implements Serializable{

	private static final long serialVersionUID = 2549245859588443092L;
	
	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";
	
	@XmlElement
	private List<Order> Order;
	
	private BaseTransfer BaseTransfer;

}
