package com.kzkj.mq;


import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.google.common.eventbus.AsyncEventBus;
import com.kzkj.enums.EnumBillType;
import com.kzkj.listener.BaseListener;
import com.kzkj.pojo.vo.request.InvtCancel.CEB605Message;
import com.kzkj.pojo.vo.request.arrival.CEB507Message;
import com.kzkj.pojo.vo.request.customs.Custom;
import com.kzkj.pojo.vo.request.delivery.CEB711Message;
import com.kzkj.pojo.vo.request.departure.CEB509Message;
import com.kzkj.pojo.vo.request.invt.CEB603Message;
import com.kzkj.pojo.vo.request.invt.CEB621Message;
import com.kzkj.pojo.vo.request.invtRefund.CEB625Message;
import com.kzkj.pojo.vo.request.logistics.CEB505Message;
import com.kzkj.pojo.vo.request.logistics.CEB511Message;
import com.kzkj.pojo.vo.request.logisticsStatus.CEB513Message;
import com.kzkj.pojo.vo.request.message.CEB900Message;
import com.kzkj.pojo.vo.request.message.MessageReturn;
import com.kzkj.pojo.vo.request.order.CEB303Message;
import com.kzkj.pojo.vo.request.order.CEB311Message;
import com.kzkj.pojo.vo.request.receipts.CEB403Message;
import com.kzkj.pojo.vo.request.summaryApply.CEB701Message;
import com.kzkj.pojo.vo.request.summaryResult.CEB792Message;
import com.kzkj.pojo.vo.request.tax.CEB816Message;
import com.kzkj.pojo.vo.request.taxStatus.CEB818Message;
import com.kzkj.pojo.vo.request.waybill.CEB607Message;
import com.kzkj.utils.XMLUtil;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class MqReceiver implements ChannelAwareMessageListener
{


    private static final String begin="<ds:Signature";

    private static final String end="</ds:Signature>";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    @Autowired
    AsyncEventBus asyncEventBus;

//    @Autowired
//    Schema exportSchema;

    @Autowired
    BaseListener baseListener;

    @Autowired
    public MqSender mqSender;
    /**
     * 根据收到的报文类型，生成不同的事件
     *
     */
    @Override
    public void onMessage(Message message, Channel channel) {

        String customsXml = new String(message.getBody());
		log.info("终端报文{}",customsXml);
        log.info("接收报文类型出口");
        String xml;
        try
        {
            boolean flag=false;
            customsXml=customsXml.replace("dxp:", "");
            Custom customs = (Custom) XMLUtil.convertXmlStrToObject(Custom.class,customsXml);
            //判断接收方id是否对应
            for(String receiverId:customs.getTransInfo().getReceiverIds().getReceiverId())
            {
                if(BaseListener.customsDxpId.equals(receiverId))
                {
                    flag=true;
                    break;
                }
            }
            if(!flag)
            {
                return;
            }
            byte[] s=Base64.decodeBase64(customs.getData());
            String receiveXml=new String (s);
			log.info("解密终端报文{}",receiveXml);
            String removePart=receiveXml.substring(receiveXml.indexOf(begin),receiveXml.indexOf(end)+end.length());
            xml=receiveXml.replace(removePart, "");
//            String errorMsg = this.validateXml(xml);
            String errorMsg="";
            if(StringUtils.isNotEmpty(errorMsg)) {
                sendCEB900Message(errorMsg, xml, customsXml, receiveXml);
                return;
            }
            xml=xml.replace("ceb:", "");

			log.info("解密终端报文{}",xml);
        }
        catch (Exception e)
        {
            log.error("终端报文解析失败{}",e.getMessage());
            return;
        }


        Object result = null;

        if(xml.indexOf(EnumBillType.Arrival.getName()) > 0)
        {
            log.info("处理{CEB507Message}报文");
            result =  XMLUtil.convertXmlStrToObject(CEB507Message.class,xml);
        }
        else if(xml.indexOf(EnumBillType.Departure.getName()) > 0)
        {
            log.info("处理{CEB509Message}报文");
            result =  XMLUtil.convertXmlStrToObject(CEB509Message.class,xml);
        }
        else if(xml.indexOf(EnumBillType.Inventory.getName()) > 0)
        {
            log.info("处理{CEB603Message}报文");
            result =  XMLUtil.convertXmlStrToObject(CEB603Message.class,xml);
        }
        else if(xml.indexOf(EnumBillType.InvtCancel.getName()) > 0)
        {
            log.info("处理{CEB605Message}报文");
            result = XMLUtil.convertXmlStrToObject(CEB605Message.class,xml);
        }
        else if(xml.indexOf(EnumBillType.Logistics.getName()) > 0)
        {
            log.info("处理{CEB505Message}报文");
            result = XMLUtil.convertXmlStrToObject(CEB505Message.class,xml);
        }
        else if(xml.indexOf(EnumBillType.Order.getName()) > 0)
        {
            log.info("处理{CEB303Message}报文");
            result =  XMLUtil.convertXmlStrToObject(CEB303Message.class,xml);
        }
        else if(xml.indexOf(EnumBillType.SummaryApply.getName()) > 0)
        {
            log.info("处理{CEB701Message}报文");
            result =  XMLUtil.convertXmlStrToObject(CEB701Message.class,xml);
        }
        else if(xml.indexOf(EnumBillType.SummaryResult.getName()) > 0)
        {
            log.info("处理{CEB792Message}报文");
            result = XMLUtil.convertXmlStrToObject(CEB792Message.class,xml);
        }
        else if(xml.indexOf(EnumBillType.WayBill.getName()) > 0)
        {
            log.info("处理{CEB607Message}报文");
            result = XMLUtil.convertXmlStrToObject(CEB607Message.class,xml);
        }
        else if(xml.indexOf(EnumBillType.Receipts.getName()) > 0)
        {
            log.info("处理{CEB403Message}报文");
            result =  XMLUtil.convertXmlStrToObject(CEB403Message.class,xml);
        }
        else if(xml.indexOf(EnumBillType.ImportOrder.getName()) > 0)
        {
            log.info("处理{CEB311Message}报文");
            result =  XMLUtil.convertXmlStrToObject(CEB311Message.class, xml);
        }
        else if(xml.indexOf(EnumBillType.ImportLogistics.getName()) > 0)
        {
            log.info("处理{CEB511Message}报文");
            result = XMLUtil.convertXmlStrToObject(CEB511Message.class, xml);
        }
        else if(xml.indexOf(EnumBillType.ImportLogisticsStatus.getName()) > 0)
        {
            log.info("处理{CEB513Message}报文");
            result =  XMLUtil.convertXmlStrToObject(CEB513Message.class, xml);
        }
        else if(xml.indexOf(EnumBillType.ImportInventory.getName()) > 0)
        {
            log.info("处理{CEB621Message}报文");
            result = XMLUtil.convertXmlStrToObject(CEB621Message.class, xml);
        }
        else if(xml.indexOf(EnumBillType.InvtRefund.getName()) > 0)
        {
            log.info("处理{CEB625Message}报文");
            result =  XMLUtil.convertXmlStrToObject(CEB625Message.class, xml);
        }
        else if(xml.indexOf(EnumBillType.Delivery.getName()) > 0)
        {
            log.info("处理{CEB711Message}报文");
            result =  XMLUtil.convertXmlStrToObject(CEB711Message.class, xml);
        }
        else if(xml.indexOf(EnumBillType.Tax.getName()) > 0)
        {
            log.info("处理{CEB816Message}报文");
            result =  XMLUtil.convertXmlStrToObject(CEB816Message.class, xml);
        }
        else if(xml.indexOf(EnumBillType.TaxStatus.getName()) > 0)
        {
            log.info("处理{CEB818Message}报文");
            result =  XMLUtil.convertXmlStrToObject(CEB818Message.class, xml);
        }
        else
        {
            log.error("报文信息有误");
        }

        if(result != null)
        {
            asyncEventBus.post(result);
        }
    }

    private void sendCEB900Message(String errorMsg, String xml, String customsXml, String receiveXml) {
        String guid = xml.substring(xml.indexOf("<ceb:guid>"), xml.indexOf("</ceb:guid>")).substring("<ceb:guid>".length());
        String version = "1.0";
        String dxpId = xml.substring(xml.indexOf("<ceb:dxpId>"), xml.indexOf("</ceb:dxpId>")).substring("<ceb:dxpId>".length());
        String oriCopMsgId = customsXml.substring(customsXml.indexOf("<CopMsgId>"), customsXml.indexOf("</CopMsgId>")).substring("<CopMsgId>".length());
        String oriMsgType = customsXml.substring(customsXml.indexOf("<MsgType>"), customsXml.indexOf("</MsgType>")).substring("<MsgType>".length());
        List<MessageReturn> messageReturnList = new ArrayList<>();
        MessageReturn messageReturn = new MessageReturn();
        messageReturn.setGuid(guid);
        messageReturn.setReturnStatus("-999999");
        messageReturn.setReturnInfo(errorMsg);
        messageReturn.setReturnTime(sdf.format(new Date()));
        messageReturn.setOriCopMsgId(oriCopMsgId);
        messageReturn.setOriMessageType(oriMsgType);
        messageReturn.setOriBizMessage(receiveXml);
        messageReturnList.add(messageReturn);

        CEB900Message ceb900Message = new CEB900Message();
        ceb900Message.setGuid(guid);
        ceb900Message.setVersion(version);
        ceb900Message.setMessageReturn(messageReturnList);

        String returnXml = XMLUtil.convertToXml(ceb900Message);
        String resultXml = baseListener.customData(returnXml, dxpId, "CEB900Message");
        String queue = dxpId+"_HZ";
        mqSender.sendMsg(queue, resultXml,"CEB304Message");
    }
/*
    private String validateXml(String xml) {
        // 通过Schema产生针对于此Schema的验证器，利用schenaFile进行验证
        Validator validator = exportSchema.newValidator();
        // 得到验证的数据源
        Source source = new StreamSource(String2InputStream(xml));
        // 开始验证，成功输出success!!!，失败输出fail
        // 参数还可以用文件的String转为的inputstreamnew
        // ByteArrayInputStream(text.getBytes("UTF-8"));
        try {
            validator.validate(source);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ex.getMessage();
        }
        return "";
    }
*/
    /**
     * 将字符串转换为流对象
     * @param str
     *            需要装的字符串
     * @return 返回流对象
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    private static InputStream String2InputStream(String str) {
        ByteArrayInputStream stream = null;
        try {
            stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
        return stream;
    }
}
