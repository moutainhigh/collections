package com.gwssi.common.util;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

public class ReceptSendXmlUtil {

	/**
	 * 从ajax查询中获得查询的xml文档
	 * @param request
	 * @return
	 * @throws DocumentException
	 */
	public static Document getSendXml(HttpServletRequest request) throws DocumentException {
		ServletInputStream Stream = null;
		Document doc = null;
		byte ba[];
		int len=0;
		int start = 0;
		int length = 2048;
		
		String sendXml;
		try {
			Stream = request.getInputStream();
			len = request.getContentLength();
			ba = new byte[len];
			while (true) {
				if (start + length > len)
					length = len - start;
				length = Stream.read(ba, start, length);
				if (start + length >= len || length == -1)
					break;
				start += length;
			}

			sendXml = new String(ba,"utf-8");

			doc = DocumentHelper.parseText(sendXml);
			
			doc.setXMLEncoding("gb2312");
		} catch (IOException e) {

		}
		return doc;

	}
}
