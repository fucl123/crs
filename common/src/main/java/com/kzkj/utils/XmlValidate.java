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
import java.math.BigDecimal;

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

    public static void main(String[] args) throws Exception {

        BigDecimal bigDecimal = new BigDecimal("0.0");
        System.out.println("String:"+bigDecimal.toString());

        XmlValidate xmlValidate = new XmlValidate();

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Signature xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<SignedInfo>\n" +
                "\t\t<CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" />\n" +
                "\t\t<SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" />\n" +
                "\t\t<Reference URI=\"String\">\n" +
                "\t\t\t<DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" />\n" +
                "\t\t\t<DigestValue />\n" +
                "\t\t</Reference>\n" +
                "\t</SignedInfo>\n" +
                "\t<SignatureValue />\n" +
                "\t<KeyInfo>\n" +
                "\t\t<KeyName>yujiantao</KeyName>\n" +
                "\t</KeyInfo>\n" +
                "\t<Object Id=\"String\">\n" +
                "\t\t<Package>\n" +
                "\t\t\t<EnvelopInfo>\n" +
                "\t\t\t\t<version>1.0</version>\n" +
                "\t\t\t\t<business_id>aaaaaaa</business_id>\n" +
                "\t\t\t\t<message_id>a</message_id>\n" +
                "\t\t\t\t<file_name>BWS101修改.zip</file_name>\n" +
                "\t\t\t\t<message_type>BWS101</message_type>\n" +
                "\t\t\t\t<sender_id>DXPESW0000214130</sender_id>\n" +
                "\t\t\t\t<receiver_id>DXPENTSWSA000001</receiver_id>\n" +
                "\t\t\t\t<send_time>2019-11-18T14:55:11</send_time>\n" +
                "\t\t\t\t<Ic_Card>2100040033809</Ic_Card>\n" +
                "\t\t\t</EnvelopInfo>\n" +
                "\t\t\t<DataInfo>\n" +
                "\t\t\t\t<PocketInfo>\n" +
                "\t\t\t\t\t<pocket_id/>\n" +
                "\t\t\t\t\t<total_pocket_qty>0</total_pocket_qty>\n" +
                "\t\t\t\t\t<cur_pocket_no>0</cur_pocket_no>\n" +
                "\t\t\t\t\t<is_unstructured>a</is_unstructured>\n" +
                "\t\t\t\t</PocketInfo>\n" +
                "\t\t\t\t<BussinessData>\n" +
                "\t\t\t\t\t<BwlMessage>\n" +
                "\t\t\t\t\t\t<BwlHead>\n" +
                "\t\t\t\t\t\t\t<SeqNo>201900000000233203</SeqNo>\n" +
                "\t\t\t\t\t\t\t<BwlNo>L1929B19A004</BwlNo>\n" +
                "\t\t\t\t\t\t\t<EtpsPreentNo>33169609NY</EtpsPreentNo>\n" +
                "\t\t\t\t\t\t\t<DclTypeCd>1</DclTypeCd>\n" +
                "\t\t\t\t\t\t\t<BwlTypeCd>B</BwlTypeCd>\n" +
                "\t\t\t\t\t\t\t<MasterCuscd>1906</MasterCuscd>\n" +
                "\t\t\t\t\t\t\t<BizopEtpsSccd>91231090MA1BCB2QX5</BizopEtpsSccd>\n" +
                "\t\t\t\t\t\t\t<BizopEtpsno>33169609NY</BizopEtpsno>\n" +
                "\t\t\t\t\t\t\t<BizopEtpsNm>宽窄科技（杭州）有限公司</BizopEtpsNm>\n" +
                "\t\t\t\t\t\t\t<DclEtpsSccd>91231090MA1BCB2QX5</DclEtpsSccd>\n" +
                "\t\t\t\t\t\t\t<DclEtpsno>2310980007</DclEtpsno>\n" +
                "\t\t\t\t\t\t\t<DclEtpsNm>宽窄科技（杭州）有限公司</DclEtpsNm>\n" +
                "\t\t\t\t\t\t\t<DclEtpsTypeCd>2</DclEtpsTypeCd>\n" +
                "\t\t\t\t\t\t\t<ContactEr>联系人</ContactEr>\n" +
                "\t\t\t\t\t\t\t<ContactTele>13945396779</ContactTele>\n" +
                "\t\t\t\t\t\t\t<HouseTypeCd>B</HouseTypeCd>\n" +
                "\t\t\t\t\t\t\t<HouseNo>33169609NY</HouseNo>\n" +
                "\t\t\t\t\t\t\t<HouseNm>33169609NY</HouseNm>\n" +
                "\t\t\t\t\t\t\t<HouseArea>3443</HouseArea>\n" +
                "\t\t\t\t\t\t\t<HouseVolume>123</HouseVolume>\n" +
                "\t\t\t\t\t\t\t<HouseAddress>浙江省杭州市梦想小镇</HouseAddress>\n" +
                "\t\t\t\t\t\t\t<DclTime>20191120</DclTime>\n" +
                "\t\t\t\t\t\t\t<TaxTypeCd>1</TaxTypeCd>\n" +
                "\t\t\t\t\t\t\t<FinishValidTime>20180831</FinishValidTime>\n" +
                "\t\t\t\t\t\t\t<AppendTypeCd>2</AppendTypeCd>\n" +
                "\t\t\t\t\t\t\t<Rmk>note</Rmk>\n" +
                "\t\t\t\t\t\t\t<InputCode>33169609NY</InputCode>\n" +
                "\t\t\t\t\t\t\t<InputSccd>91231090MA1BCB2QX5</InputSccd>\n" +
                "\t\t\t\t\t\t\t<InputName>宽窄科技（杭州）有限公司</InputName>\n" +
                "\t\t\t\t\t\t\t<UsageTypeCd>1</UsageTypeCd>\n" +
                "\t\t\t\t\t\t\t<Col1>1</Col1>\n" +
                "\t\t\t\t\t\t\t<Col2>1</Col2>\n" +
                "\t\t\t\t\t\t\t<Col3>1</Col3>\n" +
                "\t\t\t\t\t\t\t<Col4>1</Col4>\n" +
                "\t\t\t\t\t\t</BwlHead>\n" +
                "\t\t\t\t\t\t<BwlList>\n" +
                "\t\t\t\t\t\t\t<SeqNo>201900000000233203</SeqNo>\n" +
                "\t\t\t\t\t\t\t<GdsSeqNo>1</GdsSeqNo>\n" +
                "\t\t\t\t\t\t\t<GdsMtno>1</GdsMtno>\n" +
                "\t\t\t\t\t\t\t<Gdecd>6201129090</Gdecd>\n" +
                "\t\t\t\t\t\t\t<GdsNm>衣服</GdsNm>\n" +
                "\t\t\t\t\t\t\t<GdsSpcfModelDesc>4531894642020</GdsSpcfModelDesc>\n" +
                "\t\t\t\t\t\t\t<NatCd>301</NatCd>\n" +
                "\t\t\t\t\t\t\t<DclUnitCd>035</DclUnitCd>\n" +
                "\t\t\t\t\t\t\t<LawfUnitCd>035</LawfUnitCd>\n" +
                "\t\t\t\t\t\t\t<SecdLawfUnitCd>035</SecdLawfUnitCd>\n" +
                "\t\t\t\t\t\t\t<DclUprcAmt>100</DclUprcAmt>\n" +
                "\t\t\t\t\t\t\t<DclCurrCd>142</DclCurrCd>\n" +
                "\t\t\t\t\t\t\t<MODF_MARKCD>1</MODF_MARKCD>\n" +
                "\t\t\t\t\t\t\t<InvtNo>123456789012345678</InvtNo>\n" +
                "\t\t\t\t\t\t\t<InvtGNo>1234567890123456789</InvtGNo>\n" +
                "\t\t\t\t\t\t\t<Rmk>65464</Rmk>\n" +
                "\t\t\t\t\t\t\t<Col1>1</Col1>\n" +
                "\t\t\t\t\t\t\t<Col2>1</Col2>\n" +
                "\t\t\t\t\t\t\t<Col3>1</Col3>\n" +
                "\t\t\t\t\t\t\t<Col4>1</Col4>\n" +
                "\t\t\t\t\t\t</BwlList>\n" +
                "\t\t\t\t\t\t<OperCusRegCode>33169609NY</OperCusRegCode>\n" +
                "\t\t\t\t\t</BwlMessage>\n" +
                "\t\t\t\t\t<DelcareFlag>1</DelcareFlag>\n" +
                "\t\t\t\t</BussinessData>\n" +
                "\t\t\t</DataInfo>\n" +
                "\t\t</Package>\n" +
                "\t</Object>\n" +
                "</Signature>\n";
        //xml = xml.replace("ceb:", "");
        //System.out.println(xml);
        xmlValidate.setXsdpath("C:/Users/32723/Desktop/check1/BWS101c.xsd");
        String newxml = fileRead("C:\\Users\\32723\\Desktop\\下载\\BWS101C.xml");
        String err = xmlValidate.xmlStringValidate(newxml);
        System.out.println(err);

    }

    public static String fileRead(String path) throws Exception {
        File file = new File(path);//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            System.out.println(s);
        }
        bReader.close();
        String str = sb.toString();
        return str;
    }
}
