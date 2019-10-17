package com.kzkj.pojo.vo.request.invt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


@XmlAccessorType(XmlAccessType.FIELD)  
//XML文件中的根标识  
@XmlRootElement(name="InventoryList")  
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		  "gnum",
		  "itemNo",
		  "itemRecordNo",
		  "itemName",
	      "gcode",
	      "gname",
	      "gmodel",
	      "barCode",
	      "country",
	      "currency",
	      "qty",
	      "qty1",
	      "qty2",
	      "unit",
	      "unit1",
	      "unit2",
	      "price",
	      "totalPrice",
	      "note",
		
})
public class InventoryList implements Serializable{

	private static final long serialVersionUID = -2765014879841656718L;
	
	private Integer gnum;
	private String itemNo;
	private String itemRecordNo;
	private String itemName;
    private String gcode;
    private String gname;
    private String gmodel;
    private String barCode;
    private String country;
    private String currency;
    private String qty;
    private String qty1;
    private String qty2;
    private String unit;
    private String unit1;
    private String unit2;
    private String price;
    private String totalPrice;
    private String note;
	public Integer getGnum() {
		return gnum;
	}
	public void setGnum(Integer gnum) {
		this.gnum = gnum;
	}
	public String getItemRecordNo() {
		return itemRecordNo;
	}
	public void setItemRecordNo(String itemRecordNo) {
		this.itemRecordNo = itemRecordNo;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getGcode() {
		return gcode;
	}
	public void setGcode(String gcode) {
		this.gcode = gcode;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getGmodel() {
		return gmodel;
	}
	public void setGmodel(String gmodel) {
		this.gmodel = gmodel;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getQty1() {
		return qty1;
	}
	public void setQty1(String qty1) {
		this.qty1 = qty1;
	}
	public String getUnit1() {
		return unit1;
	}
	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}
	public String getQty2() {
		return qty2;
	}
	public void setQty2(String qty2) {
		this.qty2 = qty2;
	}
	public String getUnit2() {
		return unit2;
	}
	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = (double)Integer.valueOf(price)/1000+"";
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice =  (double)Integer.valueOf(totalPrice)/1000+"";
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    

}
