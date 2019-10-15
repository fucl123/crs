package com.kzkj.pojo.vo.arrival;


import com.supply.listener.request.base.BaseTransfer;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;


/**
 * 
 * 发送总署运抵单请求对象
 * <br>（功能详细描述）
 */

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="CEB507Message")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"Arrival",
	"BaseTransfer"
}) 
@Data
public class CEB507Message implements Serializable{

	
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 * @since 2018年7月18日 下午3:16:01
	 * @author 张爽
	 */
	private static final long serialVersionUID = 904018265412781404L;

	@XmlAttribute
    private String guid;
	
	@XmlAttribute
    private String version = "1.0";

	private List<Arrival> Arrival;
	
	private BaseTransfer BaseTransfer;
	
	
	
}
