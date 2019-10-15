package com.kzkj.pojo.vo.arrival;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="ArrivalList")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
	"gnum",
	"logisticsNo",
	"totalPackageNo",
	"note"
}) 
@Data
public class ArrivalList implements Serializable{

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 * @since 2018年7月18日 下午3:22:33
	 * @author 张爽
	 */
	private static final long serialVersionUID = -2934925994313628498L;

    private Integer gnum;

    private String logisticsNo;

    private String totalPackageNo;

    private String note;

}