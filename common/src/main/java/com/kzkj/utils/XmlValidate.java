package com.kzkj.utils;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
@Data
public class XmlValidate {

    private static final Log logger = LogFactory.getLog(XmlValidate.class);
    private static final String SCHEMALANG = "http://www.w3.org/2001/XMLSchema";
    private static final String SCHEMALANG_CEB = "http://www.chinaport.gov.cn/ceb";

    private String xsdpath;

    /**
     * Schema校验xml文件
     * @param xmlStr xml字符串
     * @return xml文件是否符合xsd定义的规则
     */
    public String xmlStringValidate(String xmlStr) {
        try {
            if (xsdpath == null) return "无法找到xsd文件！";
            SchemaFactory factory = SchemaFactory.newInstance(SCHEMALANG);
            InputStream inputStream =null;
            File inputFile = new File(xsdpath);
            inputStream = new FileInputStream(inputFile);
            Source xsdsource = new StreamSource(inputStream);
            Schema schema = factory.newSchema(xsdsource);
            Validator validator = schema.newValidator();
            InputStream is = String2InputStream(xmlStr);
            //需要设置编码   否则会出字节解析错误
            InputStreamReader in = new InputStreamReader(is, "UTF-8");
            Source source = new StreamSource(in);
            try {
                validator.validate(source);
            } catch (SAXException ex) {
                logger.info("Schema校验xml文件 异常"+ex.getMessage());
                return ex.getMessage();
            }
        } catch (Exception e) {
            logger.info("Schema校验xml文件 异常"+e.getMessage());
            return e.getMessage();
        }
        logger.info("报文校验正常...");
        return "";
    }

    /**
     * 将字符串转换为流对象
     * @param str
     *            需要装的字符串
     * @return 返回流对象
     * @since CodingExample Ver(编码范例查看) 1.1
     */
    private InputStream String2InputStream(String str) {
        ByteArrayInputStream stream = null;
        try {
            stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return stream;
    }

    public static void main(String[] args) {

        XmlValidate xmlValidate = new XmlValidate();
        xmlValidate.setXsdpath("C:/Users/32723/Desktop/check/crse.xsd");
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><ceb:CEB303Message xmlns:ceb=\"http://www.chinaport.gov.cn/ceb\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" guid=\"5570da31-6564-44d8-afdf-ef44d8ec1597\" version=\"1.0\">\n" +
                "  <ceb:Order>\n" +
                "    <ceb:OrderHead>\n" +
                "      <ceb:guid>b09543aba9ab4c8e99efa3d4ab2366241111</ceb:guid>\n" +
                "      <ceb:appType>1</ceb:appType>\n" +
                "      <ceb:appTime>20191030163407</ceb:appTime>\n" +
                "      <ceb:appStatus>1</ceb:appStatus>\n" +
                "      <ceb:orderType>E</ceb:orderType>\n" +
                "      <ceb:orderNo>zj-84562</ceb:orderNo>\n" +
                "      <ceb:ebpCode>DS-8798</ceb:ebpCode>\n" +
                "      <ceb:ebpName>EDP_NAME电商平台名称</ceb:ebpName>\n" +
                "      <ceb:ebcCode>DM-98798</ceb:ebcCode>\n" +
                "      <ceb:ebcName>EBC_NAME电商企业名称</ceb:ebcName>\n" +
                "      <ceb:goodsValue>0.00000</ceb:goodsValue>\n" +
                "      <ceb:freight>50000.00000</ceb:freight>\n" +
                "      <ceb:currency>人民币</ceb:currency>\n" +
                "      <ceb:note>\n" +
                "      </ceb:note>\n" +
                "    </ceb:OrderHead>\n" +
                "    <ceb:OrderList>\n" +
                "      <ceb:gnum>2</ceb:gnum>\n" +
                "      <ceb:itemNo>9798799</ceb:itemNo>\n" +
                "      <ceb:itemName>香蕉</ceb:itemName>\n" +
                "      <ceb:itemDescribe>\n" +
                "      </ceb:itemDescribe>\n" +
                "      <ceb:barCode>\n" +
                "      </ceb:barCode>\n" +
                "      <ceb:unit>11吨</ceb:unit>\n" +
                "      <ceb:currency>人民币</ceb:currency>\n" +
                "      <ceb:qty>20.00000</ceb:qty>\n" +
                "      <ceb:price>600.00000</ceb:price>\n" +
                "      <ceb:totalPrice>12000.00000</ceb:totalPrice>\n" +
                "      <ceb:note>\n" +
                "      </ceb:note>\n" +
                "    </ceb:OrderList>\n" +
                "  </ceb:Order>\n" +
                "  <ceb:BaseTransfer>\n" +
                "    <ceb:copCode>3702100002</ceb:copCode>\n" +
                "    <ceb:copName>3702100002</ceb:copName>\n" +
                "    <ceb:dxpMode>DXP</ceb:dxpMode>\n" +
                "    <ceb:dxpId>DXPLGS0000000002</ceb:dxpId>\n" +
                "    <ceb:note>\n" +
                "    </ceb:note>\n" +
                "  </ceb:BaseTransfer>\n" +
                "</ceb:CEB303Message>";
        //xml = xml.replace("ceb:", "");
        System.out.println(xml);
        String err = xmlValidate.xmlStringValidate(xml);
        System.out.println(err);

    }
}
