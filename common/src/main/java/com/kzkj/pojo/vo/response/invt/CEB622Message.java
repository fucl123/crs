package com.kzkj.pojo.vo.response.invt;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * 接收总署订单回执对象
 */
@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB622Message",namespace="http://www.chinaport.gov.cn/ceb")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
     "InventoryReturn",   
}) 
@Data
public class CEB622Message implements Serializable {
	
	private static final long serialVersionUID = 86842686017963544L;

	@XmlAttribute
    private String xmlns;
	
	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version;
	
	private List<ImportInventoryReturn> InventoryReturn ;
	

}
