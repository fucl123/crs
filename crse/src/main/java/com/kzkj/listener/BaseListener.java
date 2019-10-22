package com.kzkj.listener;


import com.kzkj.mq.MqSender;
import com.kzkj.pojo.vo.request.customs.Custom;
import com.kzkj.pojo.vo.response.customs.CustomTransInfo;
import com.kzkj.pojo.vo.response.customs.ReceiverIds;
import com.kzkj.pojo.vo.response.customs.CustomResponse;
import com.kzkj.utils.CXMLUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class BaseListener {
    public static final String customsDxpId = "DXPLGS0000000001";
    public static final String customsDxpIdIm = "DXPDSWIMPCEB0001";

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    public MqSender mqSender;


    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    Logger logger = LoggerFactory.getLogger(BaseListener.class);



    public String customData(String resultXml, String receiveId, String msgType) {
        CustomResponse custom = new CustomResponse();
        CustomTransInfo transInfo = new CustomTransInfo();
        String data = null;
        transInfo.setCopMsgId(UUID.randomUUID().toString());//报文ID
        transInfo.setSenderId(customsDxpId);//发送方id
        ReceiverIds receiverIds = new ReceiverIds();
        List<String> receiverIdList = new ArrayList<>();
        receiverIdList.add(receiveId);
        receiverIds.setReceiverId(receiverIdList);
        transInfo.setReceiverIds(receiverIds);//接收方id
        transInfo.setCreatTime(new Date());
        transInfo.setMsgType(msgType);//报文类型
        custom.setTransInfo(transInfo);
        try {
            data = new String(Base64.encodeBase64(resultXml.getBytes()), "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("发送报文错误，错误信息：{}", e.getMessage());
        }
        custom.setData(data);
        return CXMLUtil.toXML(custom);
    }
}
