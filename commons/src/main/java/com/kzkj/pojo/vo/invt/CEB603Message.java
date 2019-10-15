package com.kzkj.pojo.vo.invt;

import com.kzkj.pojo.vo.base.BaseTransfer;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * 发送总署清单请求对象
 * <br>（功能详细描述）
 */

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB603Message")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"Inventory",
	"BaseTransfer"
}) 
@Data
public class CEB603Message implements Serializable{

	private static final long serialVersionUID = -2190549105401767538L;
	
	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";

	private List<Inventory> Inventory;
	
	private BaseTransfer BaseTransfer;
	
	
	
}
