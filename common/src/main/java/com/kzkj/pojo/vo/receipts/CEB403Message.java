package com.kzkj.pojo.vo.receipts;

import com.kzkj.pojo.vo.base.BaseTransfer;
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
@XmlRootElement(name="CEB403Message")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "Receipts",   
     "BaseTransfer",   
})
@Data
public class CEB403Message implements Serializable{

	private static final long serialVersionUID = 2549245859588443092L;
	
	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";
	
	@XmlElement
	private List<Receipts> Receipts;
	
	private BaseTransfer BaseTransfer;

}
