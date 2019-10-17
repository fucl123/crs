package com.kzkj.pojo.vo.message;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 接收总署入库明细返回
 */
@XmlAccessorType(XmlAccessType.FIELD)
//XML文件中的根标识
@XmlRootElement(name = "CEB900Message")
//控制JAXB 绑定类中属性和字段的排序
@XmlType(propOrder = {
    "MessageReturn",
})
@Data
public class CEB900Message implements Serializable {
    @XmlAttribute
    private String xmlns;

    @XmlAttribute
    private String guid;

    @XmlAttribute
    private String version;

    private List<MessageReturn> MessageReturn;

}
