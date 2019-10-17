package com.kzkj.pojo.vo.customs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;


@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识  
@XmlRootElement(name="TransInfo")
//控制JAXB 绑定类中属性和字段的排序  
@XmlType(propOrder = {   
		  "CopMsgId",
	      "SenderId",
	      "ReceiverIds",
	      "CreatTime",
	      "MsgType",
})
public class CustomTransInfo implements Serializable{

	private static final long serialVersionUID = -8525775733198204717L;
	
	private String CopMsgId;
	
	private String SenderId;
	
	private ReceiverIds ReceiverIds;
	
	private Date CreatTime;
	
	private String MsgType;

	public String getCopMsgId() {
		return CopMsgId;
	}

	public void setCopMsgId(String copMsgId) {
		CopMsgId = copMsgId;
	}

	public String getSenderId() {
		return SenderId;
	}

	public void setSenderId(String senderId) {
		SenderId = senderId;
	}

	public ReceiverIds getReceiverIds() {
		return ReceiverIds;
	}

	public void setReceiverIds(ReceiverIds receiverIds) {
		ReceiverIds = receiverIds;
	}

	public Date getCreatTime() {
		return CreatTime;
	}

	public void setCreatTime(Date creatTime) {
		CreatTime = creatTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	
	

}
