package com.kzkj.pojo.vo.request.logisticsStatus;

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
@XmlRootElement(name="CEB513Message")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "LogisticsStatus",   
     "BaseTransfer",   
})
@Data
public class CEB513Message implements Serializable{

	private static final long serialVersionUID = -8139436207833041775L;

	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";
	
	@XmlElement
	private List<com.kzkj.pojo.vo.request.logisticsStatus.LogisticsStatus> LogisticsStatus;
	
	private com.kzkj.pojo.vo.request.base.BaseTransfer BaseTransfer;

}
