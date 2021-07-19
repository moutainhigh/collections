package com.gwssi.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * 获得xml
 * 依赖dom4j
 * @author zhyi
 * @创建时间
 * @版本号 V1.0.0
 */
public class XmlUrlReaderUtil
{
	//public 
	public static Document getDocument(URL url) throws XmlUrlReaderException{
		Document doc = null;
		URLConnection urlConnection = null;
		try {
			urlConnection = url.openConnection();
		} catch (IOException e) {
			new XmlUrlReaderException("URL打开失败：",e);
		}   
		urlConnection.setRequestProperty("method", "POST");
		urlConnection.setRequestProperty("content-type",   
			"application/x-www-form-urlencoded;charset=utf-8");//设置http的mime   
		urlConnection.setDoOutput(true);   
		PrintWriter out = null;
		
		byte ba[];
		int len=0;
		int start = 0;
		int length = 2048;
		
		try {
			out = new PrintWriter(urlConnection.getOutputStream());
			if(out==null){
				throw new XmlUrlReaderException("未获得远程数据！");
			}
			urlConnection.connect();
			
			InputStream stream = urlConnection.getInputStream();
			len = urlConnection.getContentLength();
			ba = new byte[len];
			while (true) {
				if (start + length > len)
					length = len - start;
				length = stream.read(ba, start, length);
				if (start + length >= len || length == -1)
					break;
				start += length;
			}

			String sendXml = new String(ba,"GBK");

			doc = DocumentHelper.parseText(sendXml);
			//SAXReader reader = new SAXReader();
			//doc = reader.read(urlConnection.getInputStream());
			//System.out.println(doc.getXMLEncoding());
			//System.out.println(doc.asXML());
		} catch (IOException e) {
			throw new XmlUrlReaderException("输出流失败：",e);
		} catch (DocumentException e) {
			throw new XmlUrlReaderException("XML错误：",e);
		} finally{
			try {
				out.close();
			} catch (RuntimeException e) {
				throw new XmlUrlReaderException("未获得远程数据！");
			}  
		}
		
		return doc;
	}
	
	public static Document getDocument(String urlString) throws XmlUrlReaderException{
		//urlString = "http://127.0.0.1:7001/beacon/txn202011a.ajax";
		URL  url = null;
		try {
			url  =  new URL(urlString);
		} catch (MalformedURLException e) {
			throw new XmlUrlReaderException("URL错误：",e);
		}
		return getDocument(url);
	}
	
	public static void  main(String[] args){
		String urlString = "http://127.0.0.1:7001/beacon/txn202011a.ajax?1=1&select-key:dm_zd=1&select-key:dm_dclb=1&select-key:dm_bgqb=2&select-key:wjml_nf=2006";
		try {
			Document doc = XmlUrlReaderUtil.getDocument(urlString);
			List recordElements = doc.selectNodes("//context/record");
			System.out.println(recordElements.size());
		} catch (XmlUrlReaderException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
}
