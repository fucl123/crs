package com.kzkj.utils;

import javax.xml.stream.XMLStreamReader;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author Administrator
 *
 * XML错误处理器，用于获得XML中哪一个元素标签的数据有误
 *
 */
public class XMLErrorHandler implements ErrorHandler {
    private String errorElement = null;
    private XMLStreamReader reader;
    public XMLErrorHandler(XMLStreamReader reader) {
        this.reader = reader;
    }
    @Override
    public void warning(SAXParseException e) throws SAXException {
        fatalError(e);
    }
    @Override
    public void error(SAXParseException e) throws SAXException {
        fatalError(e);
    }
    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        String lement = reader.getLocalName();
        String msg= e.getMessage();
        this.errorElement=lement+":"+msg;
    }
    public String getErrorElement() {
        return errorElement;
    }
    public void setErrorElement(String errorElement) {
        this.errorElement = errorElement;
    }

}
