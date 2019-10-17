package com.kzkj.pojo.vo.request.order;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="OrderList")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		  "gnum",
		  "itemNo",
	      "itemName",
	      "itemDescribe",
	      "barCode",
	      "unit",
	      "currency",
	      "qty",
	      "price",
	      "totalPrice",
	      "note",
})
@Data
public class OrderList implements Serializable{

	private static final long serialVersionUID = 6442072468206236418L;
	
	private Integer gnum;
	
	private String itemNo;
	
	private String itemName;
	
	private String itemDescribe;
	
	private String barCode;
	
	private String unit;
	
	private String currency;
	
	private int qty;//商品实际数量
	
	private String price;
	
	private String totalPrice;
	
	private String note;
    
}
