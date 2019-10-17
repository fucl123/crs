package com.kzkj.utils;/*
 * 创建人：fcl
 * 修改时间：2018/6/15 0015 15:16
 * 版权：Copyright 2015-2020 义乌市跨境电商供应链管理有限公司-版权所有
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 封装了XML转换成object，object转换成XML的代码
 */
public class XMLUtil {

	private static final Logger logger = LoggerFactory.getLogger(XMLUtil.class);

	private static final ConcurrentHashMap<String, JAXBContext> jaxbContextMap = new ConcurrentHashMap<String, JAXBContext>();

	/**
	 * 将对象直接转换成String类型的 XML输出
	 *
	 * @param obj
	 * @return
	 */
	public static String convertToXml(Object obj) {
		// 创建输出流
		StringWriter sw = new StringWriter();
		try
		{
			JAXBContext context = getContext(obj.getClass());

			Marshaller marshaller = context.createMarshaller();
			// 格式化xml输出的格式
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			// 将对象转换成输出流形式的xml
			marshaller.marshal(obj, sw);
		}
		catch (JAXBException e)
		{
			logger.error(e.getMessage());
		}

		String xml=sw.toString().replace("ns2:", "");
		xml=xml.replace(":ns2", "");
		return xml;
	}

	/**
	 * 将String类型的xml转换成对象
	 */
	public static Object convertXmlStrToObject(@SuppressWarnings("rawtypes") Class clazz, String xmlStr) {
		Object xmlObject = null;

		try
		{
			JAXBContext context = getContext(clazz);
			// 进行将Xml转成对象的核心接口
			Unmarshaller unmarshaller = context.createUnmarshaller();

			StringReader sr = new StringReader(xmlStr);

			xmlObject = unmarshaller.unmarshal(sr);

		}
		catch (JAXBException e)
		{
			logger.error(e.getMessage());
		}

		return xmlObject;
	}

	@SuppressWarnings("rawtypes")
	public static JAXBContext getContext(final Class clazz) throws JAXBException
	{
		JAXBContext context = jaxbContextMap.get(clazz.getName());
		if(context == null)
		{
			synchronized (jaxbContextMap)
			{
				context = jaxbContextMap.get(clazz.getName());
				if(context == null)
				{
					// 如果每次都调用JAXBContext.newInstance方法，会导致性能急剧下降
					context = JAXBContext.newInstance(clazz);
					jaxbContextMap.putIfAbsent(clazz.getName(), context);
				}
			}
		}

		return  context;
	}

}
