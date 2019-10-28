package com.kzkj.utils;


import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;


public class XMLValidateUtils {

    /**
     * @param xmlFile  xml字符串
     * @param xsdFilePath  xsd 文件的全路径
     * @return
     */
    public static String validateXMLWithXSD(String xmlFile, String xsdFilePath) {
        XMLErrorHandler errHandler=null;
        try {
            Reader xmlReader = new BufferedReader(new StringReader(xmlFile));
            Reader xsdReader = new BufferedReader(new FileReader(xsdFilePath));
            Source xmlSource = new StreamSource(xmlReader);
            Source xsdSource = new StreamSource(xsdReader);

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdSource);
            XMLStreamReader reader = XMLInputFactory.newFactory().createXMLStreamReader(xmlSource);
            Validator validator = schema.newValidator();
            errHandler = new XMLErrorHandler(reader);
            validator.setErrorHandler(errHandler);
            validator.validate(new StAXSource(reader));
            return errHandler.getErrorElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {

        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ceb:CEB603Message guid=\"311af125-6fed-4603-8c5d-49b1fa4b4b9b\" version=\"1.0\"  xmlns:ceb=\"http://www.chinaport.gov.cn/ceb\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<ceb:Inventory>\n" +
                "\t\t<ceb:InventoryHead>\n" +
                "\t\t\t<ceb:guid>4CDE1CFD-EDED-46B1-946C-B8022E42FC94</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>2</ceb:appStatus>\n" +
                "\t\t\t<ceb:customsCode>1500</ceb:customsCode>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:logisticsCode>1105910159</ceb:logisticsCode>\n" +
                "\t\t\t<ceb:logisticsName>东方物通科技(北京)有限公司</ceb:logisticsName>\n" +
                "\t\t\t<ceb:logisticsNo>L2018050700001</ceb:logisticsNo>\n" +
                "\t\t\t<ceb:copNo>Cop20180507001</ceb:copNo>\n" +
                "\t\t\t<ceb:ieFlag>a</ceb:ieFlag>\n" +
                "\t\t\t<ceb:portCode>1501</ceb:portCode>\n" +
                "\t\t\t<ceb:ieDate>20180508</ceb:ieDate>\n" +
                "\t\t\t<ceb:statisticsFlag>A</ceb:statisticsFlag>\n" +
                "\t\t\t<ceb:agentCode>1105910159</ceb:agentCode>\n" +
                "\t\t\t<ceb:agentName>东方物通科技(北京)有限公司</ceb:agentName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:ownerCode>1105910159</ceb:ownerCode>\n" +
                "\t\t\t<ceb:ownerName>东方物通科技(北京)有限公司</ceb:ownerName>\n" +
                "\t\t\t<ceb:iacCode></ceb:iacCode>\n" +
                "\t\t\t<ceb:iacName></ceb:iacName>\n" +
                "\t\t\t<ceb:emsNo></ceb:emsNo>\n" +
                "\t\t\t<ceb:tradeMode>0101</ceb:tradeMode>\n" +
                "\t\t\t<ceb:trafMode>0</ceb:trafMode>\n" +
                "\t\t\t<ceb:trafName></ceb:trafName>\n" +
                "\t\t\t<ceb:voyageNo></ceb:voyageNo>\n" +
                "\t\t\t<ceb:billNo></ceb:billNo>\n" +
                "\t\t\t<ceb:totalPackageNo></ceb:totalPackageNo>\n" +
                "\t\t\t<ceb:loctNo></ceb:loctNo>\n" +
                "\t\t\t<ceb:licenseNo></ceb:licenseNo>\n" +
                "\t\t\t<ceb:country>001</ceb:country>\n" +
                "\t\t\t<ceb:POD>002</ceb:POD>\n" +
                "\t\t\t<ceb:freight>50.36</ceb:freight>\n" +
                "\t\t\t<ceb:fCurrency>142</ceb:fCurrency>\n" +
                "\t\t\t<ceb:fFlag>3</ceb:fFlag>\n" +
                "\t\t\t<ceb:insuredFee>52.32</ceb:insuredFee>\n" +
                "\t\t\t<ceb:iCurrency>142</ceb:iCurrency>\n" +
                "\t\t\t<ceb:iFlag>3</ceb:iFlag>\n" +
                "\t\t\t<ceb:wrapType>1</ceb:wrapType>\n" +
                "\t\t\t<ceb:packNo>9</ceb:packNo>\n" +
                "\t\t\t<ceb:grossWeight>80.25</ceb:grossWeight>\n" +
                "\t\t\t<ceb:netWeight>70.25</ceb:netWeight>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:InventoryHead>\n" +
                "\t\t<ceb:InventoryList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemRecordNo>1210</ceb:itemRecordNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:gcode>8541401000</ceb:gcode>\n" +
                "\t\t\t<ceb:gname>小米盒子</ceb:gname>\n" +
                "\t\t\t<ceb:gmodel>44mm</ceb:gmodel>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:country>116</ceb:country>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:qty1>200</ceb:qty1>\n" +
                "\t\t\t<ceb:qty2>300</ceb:qty2>\n" +
                "\t\t\t<ceb:unit>007</ceb:unit>\n" +
                "\t\t\t<ceb:unit1>007</ceb:unit1>\n" +
                "\t\t\t<ceb:unit2>007</ceb:unit2>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>200</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:InventoryList>\n" +
                "\t</ceb:Inventory>\t\n" +
                "\t\t<ceb:InventoryHead>\n" +
                "\t\t\t<ceb:guid>4CDE1CFD-EDED-46B1-946C-B8022E42FC94</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>1</ceb:appStatus>\n" +
                "\t\t\t<ceb:customsCode>1500</ceb:customsCode>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:logisticsCode>1105910159</ceb:logisticsCode>\n" +
                "\t\t\t<ceb:logisticsName>东方物通科技(北京)有限公司</ceb:logisticsName>\n" +
                "\t\t\t<ceb:logisticsNo>L2018050700001</ceb:logisticsNo>\n" +
                "\t\t\t<ceb:copNo>Cop20180507001</ceb:copNo>\n" +
                "\t\t\t<ceb:ieFlag>a</ceb:ieFlag>\n" +
                "\t\t\t<ceb:portCode>1501</ceb:portCode>\n" +
                "\t\t\t<ceb:ieDate>20180508</ceb:ieDate>\n" +
                "\t\t\t<ceb:statisticsFlag>A</ceb:statisticsFlag>\n" +
                "\t\t\t<ceb:agentCode>1105910159</ceb:agentCode>\n" +
                "\t\t\t<ceb:agentName>东方物通科技(北京)有限公司</ceb:agentName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:ownerCode>1105910159</ceb:ownerCode>\n" +
                "\t\t\t<ceb:ownerName>东方物通科技(北京)有限公司</ceb:ownerName>\n" +
                "\t\t\t<ceb:iacCode></ceb:iacCode>\n" +
                "\t\t\t<ceb:iacName></ceb:iacName>\n" +
                "\t\t\t<ceb:emsNo></ceb:emsNo>\n" +
                "\t\t\t<ceb:tradeMode>0101</ceb:tradeMode>\n" +
                "\t\t\t<ceb:trafMode>0</ceb:trafMode>\n" +
                "\t\t\t<ceb:trafName></ceb:trafName>\n" +
                "\t\t\t<ceb:voyageNo></ceb:voyageNo>\n" +
                "\t\t\t<ceb:billNo></ceb:billNo>\n" +
                "\t\t\t<ceb:totalPackageNo></ceb:totalPackageNo>\n" +
                "\t\t\t<ceb:loctNo></ceb:loctNo>\n" +
                "\t\t\t<ceb:licenseNo></ceb:licenseNo>\n" +
                "\t\t\t<ceb:country>001</ceb:country>\n" +
                "\t\t\t<ceb:POD>002</ceb:POD>\n" +
                "\t\t\t<ceb:freight>50.36</ceb:freight>\n" +
                "\t\t\t<ceb:fCurrency>142</ceb:fCurrency>\n" +
                "\t\t\t<ceb:fFlag>3</ceb:fFlag>\n" +
                "\t\t\t<ceb:insuredFee>52.32</ceb:insuredFee>\n" +
                "\t\t\t<ceb:iCurrency>142</ceb:iCurrency>\n" +
                "\t\t\t<ceb:iFlag>3</ceb:iFlag>\n" +
                "\t\t\t<ceb:wrapType>1</ceb:wrapType>\n" +
                "\t\t\t<ceb:packNo>9</ceb:packNo>\n" +
                "\t\t\t<ceb:grossWeight>80.25</ceb:grossWeight>\n" +
                "\t\t\t<ceb:netWeight>70.25</ceb:netWeight>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:InventoryHead>\n" +
                "\t\t<ceb:InventoryList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemRecordNo>1210</ceb:itemRecordNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:gcode>8541401000</ceb:gcode>\n" +
                "\t\t\t<ceb:gname>小米盒子</ceb:gname>\n" +
                "\t\t\t<ceb:gmodel>44mm</ceb:gmodel>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:country>116</ceb:country>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:qty1>200</ceb:qty1>\n" +
                "\t\t\t<ceb:qty2>300</ceb:qty2>\n" +
                "\t\t\t<ceb:unit>007</ceb:unit>\n" +
                "\t\t\t<ceb:unit1>007</ceb:unit1>\n" +
                "\t\t\t<ceb:unit2>007</ceb:unit2>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>200</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:InventoryList>\n" +
                "\t</ceb:Inventory>\n" +
                "\t<ceb:Inventory>\n" +
                "\t\t<ceb:InventoryHead>\n" +
                "\t\t\t<ceb:guid>4CDE1CFD-EDED-46B1-946C-B8022E42FC94</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>1</ceb:appStatus>\n" +
                "\t\t\t<ceb:customsCode>1500</ceb:customsCode>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:logisticsCode>1105910159</ceb:logisticsCode>\n" +
                "\t\t\t<ceb:logisticsName>东方物通科技(北京)有限公司</ceb:logisticsName>\n" +
                "\t\t\t<ceb:logisticsNo>L2018050700001</ceb:logisticsNo>\n" +
                "\t\t\t<ceb:copNo>Cop20180507001</ceb:copNo>\n" +
                "\t\t\t<ceb:ieFlag>a</ceb:ieFlag>\n" +
                "\t\t\t<ceb:portCode>1501</ceb:portCode>\n" +
                "\t\t\t<ceb:ieDate>20180508</ceb:ieDate>\n" +
                "\t\t\t<ceb:statisticsFlag>A</ceb:statisticsFlag>\n" +
                "\t\t\t<ceb:agentCode>1105910159</ceb:agentCode>\n" +
                "\t\t\t<ceb:agentName>东方物通科技(北京)有限公司</ceb:agentName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:ownerCode>1105910159</ceb:ownerCode>\n" +
                "\t\t\t<ceb:ownerName>东方物通科技(北京)有限公司</ceb:ownerName>\n" +
                "\t\t\t<ceb:iacCode></ceb:iacCode>\n" +
                "\t\t\t<ceb:iacName></ceb:iacName>\n" +
                "\t\t\t<ceb:emsNo></ceb:emsNo>\n" +
                "\t\t\t<ceb:tradeMode>0101</ceb:tradeMode>\n" +
                "\t\t\t<ceb:trafMode>0</ceb:trafMode>\n" +
                "\t\t\t<ceb:trafName></ceb:trafName>\n" +
                "\t\t\t<ceb:voyageNo></ceb:voyageNo>\n" +
                "\t\t\t<ceb:billNo></ceb:billNo>\n" +
                "\t\t\t<ceb:totalPackageNo></ceb:totalPackageNo>\n" +
                "\t\t\t<ceb:loctNo></ceb:loctNo>\n" +
                "\t\t\t<ceb:licenseNo></ceb:licenseNo>\n" +
                "\t\t\t<ceb:country>001</ceb:country>\n" +
                "\t\t\t<ceb:POD>002</ceb:POD>\n" +
                "\t\t\t<ceb:freight>50.36</ceb:freight>\n" +
                "\t\t\t<ceb:fCurrency>142</ceb:fCurrency>\n" +
                "\t\t\t<ceb:fFlag>3</ceb:fFlag>\n" +
                "\t\t\t<ceb:insuredFee>52.32</ceb:insuredFee>\n" +
                "\t\t\t<ceb:iCurrency>142</ceb:iCurrency>\n" +
                "\t\t\t<ceb:iFlag>3</ceb:iFlag>\n" +
                "\t\t\t<ceb:wrapType>1</ceb:wrapType>\n" +
                "\t\t\t<ceb:packNo>9</ceb:packNo>\n" +
                "\t\t\t<ceb:grossWeight>80.25</ceb:grossWeight>\n" +
                "\t\t\t<ceb:netWeight>70.25</ceb:netWeight>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:InventoryHead>\n" +
                "\t\t<ceb:InventoryList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemRecordNo>1210</ceb:itemRecordNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:gcode>8541401000</ceb:gcode>\n" +
                "\t\t\t<ceb:gname>小米盒子</ceb:gname>\n" +
                "\t\t\t<ceb:gmodel>44mm</ceb:gmodel>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:country>116</ceb:country>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:qty1>200</ceb:qty1>\n" +
                "\t\t\t<ceb:qty2>300</ceb:qty2>\n" +
                "\t\t\t<ceb:unit>007</ceb:unit>\n" +
                "\t\t\t<ceb:unit1>007</ceb:unit1>\n" +
                "\t\t\t<ceb:unit2>007</ceb:unit2>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>200</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:InventoryList>\n" +
                "\t</ceb:Inventory>\n" +
                "\t\t<ceb:InventoryHead>\n" +
                "\t\t\t<ceb:guid>4CDE1CFD-EDED-46B1-946C-B8022E42FC94</ceb:guid>\n" +
                "\t\t\t<ceb:appType>1</ceb:appType>\n" +
                "\t\t\t<ceb:appTime>20180507153001</ceb:appTime>\n" +
                "\t\t\t<ceb:appStatus>1</ceb:appStatus>\n" +
                "\t\t\t<ceb:customsCode>1500</ceb:customsCode>\n" +
                "\t\t\t<ceb:ebpCode>1105910159</ceb:ebpCode>\n" +
                "\t\t\t<ceb:ebpName>东方物通科技(北京)有限公司</ceb:ebpName>\n" +
                "\t\t\t<ceb:orderNo>order2018050711340001</ceb:orderNo>\n" +
                "\t\t\t<ceb:logisticsCode>1105910159</ceb:logisticsCode>\n" +
                "\t\t\t<ceb:logisticsName>东方物通科技(北京)有限公司</ceb:logisticsName>\n" +
                "\t\t\t<ceb:logisticsNo>L2018050700001</ceb:logisticsNo>\n" +
                "\t\t\t<ceb:copNo>Cop20180507001</ceb:copNo>\n" +
                "\t\t\t<ceb:ieFlag>a</ceb:ieFlag>\n" +
                "\t\t\t<ceb:portCode>1501</ceb:portCode>\n" +
                "\t\t\t<ceb:ieDate>20180508</ceb:ieDate>\n" +
                "\t\t\t<ceb:statisticsFlag>A</ceb:statisticsFlag>\n" +
                "\t\t\t<ceb:agentCode>1105910159</ceb:agentCode>\n" +
                "\t\t\t<ceb:agentName>东方物通科技(北京)有限公司</ceb:agentName>\n" +
                "\t\t\t<ceb:ebcCode>1105910159</ceb:ebcCode>\n" +
                "\t\t\t<ceb:ebcName>东方物通科技(北京)有限公司</ceb:ebcName>\n" +
                "\t\t\t<ceb:ownerCode>1105910159</ceb:ownerCode>\n" +
                "\t\t\t<ceb:ownerName>东方物通科技(北京)有限公司</ceb:ownerName>\n" +
                "\t\t\t<ceb:iacCode></ceb:iacCode>\n" +
                "\t\t\t<ceb:iacName></ceb:iacName>\n" +
                "\t\t\t<ceb:emsNo></ceb:emsNo>\n" +
                "\t\t\t<ceb:tradeMode>0101</ceb:tradeMode>\n" +
                "\t\t\t<ceb:trafMode>0</ceb:trafMode>\n" +
                "\t\t\t<ceb:trafName></ceb:trafName>\n" +
                "\t\t\t<ceb:voyageNo></ceb:voyageNo>\n" +
                "\t\t\t<ceb:billNo></ceb:billNo>\n" +
                "\t\t\t<ceb:totalPackageNo></ceb:totalPackageNo>\n" +
                "\t\t\t<ceb:loctNo></ceb:loctNo>\n" +
                "\t\t\t<ceb:licenseNo></ceb:licenseNo>\n" +
                "\t\t\t<ceb:country>001</ceb:country>\n" +
                "\t\t\t<ceb:POD>002</ceb:POD>\n" +
                "\t\t\t<ceb:freight>50.36</ceb:freight>\n" +
                "\t\t\t<ceb:fCurrency>142</ceb:fCurrency>\n" +
                "\t\t\t<ceb:fFlag>3</ceb:fFlag>\n" +
                "\t\t\t<ceb:insuredFee>52.32</ceb:insuredFee>\n" +
                "\t\t\t<ceb:iCurrency>142</ceb:iCurrency>\n" +
                "\t\t\t<ceb:iFlag>3</ceb:iFlag>\n" +
                "\t\t\t<ceb:wrapType>1</ceb:wrapType>\n" +
                "\t\t\t<ceb:packNo>9</ceb:packNo>\n" +
                "\t\t\t<ceb:grossWeight>80.25</ceb:grossWeight>\n" +
                "\t\t\t<ceb:netWeight>70.25</ceb:netWeight>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:InventoryHead>\n" +
                "\t\t<ceb:InventoryList>\n" +
                "\t\t\t<ceb:gnum>1</ceb:gnum>\n" +
                "\t\t\t<ceb:itemNo>AF001-001</ceb:itemNo>\n" +
                "\t\t\t<ceb:itemRecordNo>1210</ceb:itemRecordNo>\n" +
                "\t\t\t<ceb:itemName>小米盒子</ceb:itemName>\n" +
                "\t\t\t<ceb:gcode>8541401000</ceb:gcode>\n" +
                "\t\t\t<ceb:gname>小米盒子</ceb:gname>\n" +
                "\t\t\t<ceb:gmodel>44mm</ceb:gmodel>\n" +
                "\t\t\t<ceb:barCode>2345123</ceb:barCode>\n" +
                "\t\t\t<ceb:country>116</ceb:country>\n" +
                "\t\t\t<ceb:currency>142</ceb:currency>\n" +
                "\t\t\t<ceb:qty>100</ceb:qty>\n" +
                "\t\t\t<ceb:qty1>200</ceb:qty1>\n" +
                "\t\t\t<ceb:qty2>300</ceb:qty2>\n" +
                "\t\t\t<ceb:unit>007</ceb:unit>\n" +
                "\t\t\t<ceb:unit1>007</ceb:unit1>\n" +
                "\t\t\t<ceb:unit2>007</ceb:unit2>\n" +
                "\t\t\t<ceb:price>20</ceb:price>\n" +
                "\t\t\t<ceb:totalPrice>200</ceb:totalPrice>\n" +
                "\t\t\t<ceb:note>test</ceb:note>\n" +
                "\t\t</ceb:InventoryList>\n" +
                "\t</ceb:Inventory>\n" +
                "\t<ceb:BaseTransfer>\n" +
                "\t\t<ceb:copCode>1105910159</ceb:copCode>\n" +
                "\t\t<ceb:copName>东方物通科技(北京)有限公司</ceb:copName>\n" +
                "\t\t<ceb:dxpMode>DXP</ceb:dxpMode>\n" +
                "\t\t<ceb:dxpId>DXPLGS0000000001</ceb:dxpId>\n" +
                "\t\t<ceb:note>test</ceb:note>\n" +
                "\t</ceb:BaseTransfer>\n" +
                "</ceb:CEB603Message>\n";

        String strPath = "C:/Users/32723/Desktop/check/111.xsd";
        if(StringUtils.isNotBlank(validateXMLWithXSD(str,strPath)))
        {
            System.out.println(validateXMLWithXSD(str,strPath));
        }else{
            System.out.println("校验通过！");
        }
    }
}
