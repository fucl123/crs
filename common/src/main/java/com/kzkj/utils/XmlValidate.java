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

    }
}
