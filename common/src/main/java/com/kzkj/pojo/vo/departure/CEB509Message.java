package com.kzkj.pojo.vo.departure;


import com.kzkj.pojo.vo.base.BaseTransfer;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;


/**
 * 
 * 发送总署离境单请求对象
 * <br>（功能详细描述）
 */

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB509Message")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"Departure",
	"BaseTransfer"
}) 
@Data
public class CEB509Message implements Serializable{

	private static final long serialVersionUID = -2190549105401767538L;
	
	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";

	private List<Departure> Departure;
	
	private BaseTransfer BaseTransfer;
	
	
	
}
