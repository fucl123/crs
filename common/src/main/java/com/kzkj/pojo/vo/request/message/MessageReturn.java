package com.kzkj.pojo.vo.request.message;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识
@XmlRootElement(name="MessageReturn")
//控制JAXB 绑定类中属性和字段的排序
@XmlType(propOrder = {
    "guid",
    "returnStatus",
    "returnInfo",
    "returnTime",
    "oriCopMsgId",
    "oriMessageType",
    "oriBizMessage"
})
@Data
public class MessageReturn {
    private String guid;
    private String returnStatus;
    private String returnInfo;
    private String returnTime;
    private String oriCopMsgId;
    private String oriMessageType;
    private String oriBizMessage;
}
