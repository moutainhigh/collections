package com.arj.comm.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IpReport {
	/**
	 * ��ȡSOAP������ͷ�����滻���еı�־����Ϊ�û�����ĳ���
	 * @return �ͻ���Ҫ���͸���������SOAP����
	 */
	private static String getSoapRequest(String ip) {
		StringBuilder sb = new StringBuilder();
		sb
				.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
						+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
						+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
						+ "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
						+ "<soap:Body>    " 
						+ "<getCountryCityByIp xmlns=\"http://WebXml.com.cn/\">"
						+ "<theIpAddress>" + ip	+ "</theIpAddress>" 
						+ "</getCountryCityByIp>"
						+ "</soap:Body></soap:Envelope>");
		return sb.toString();
	}
	
	/**
	 * �û���SOAP�����͸��������ˣ������ط������㷵�ص�������
	 *            �û�����ĳ�������
	 * @return �������˷��ص������������ͻ��˶�ȡ
	 * @throws Exception
	 */
	private static InputStream getSoapInputStream(String ip) throws Exception {
		try {
			String soap = getSoapRequest(ip);
			if (soap == null) {
				return null;
			}
			URL url = new URL("http://webservice.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx");
			URLConnection conn = url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestProperty("Content-Length", Integer.toString(soap.length()));
			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			conn.setRequestProperty("SOAPAction","http://WebXml.com.cn/getCountryCityByIp");

			OutputStream os = conn.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
			osw.write(soap);
			osw.flush();
			osw.close();

			InputStream is = conn.getInputStream();
			return is;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * �Է������˷��ص�XML���н���
	 * 
	 * @return �ַ��� ��,�ָ�
	 */
	public static String getIp(String ip) {
		try {
			Document doc;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream is = getSoapInputStream(ip);
			doc = db.parse(is);
			NodeList nl = doc.getElementsByTagName("string");
			StringBuffer sb = new StringBuffer();
			for (int count = 0; count < nl.getLength(); count++) {
				Node n = nl.item(count);
				if(n.getFirstChild().getNodeValue().equals("��ѯ���Ϊ�գ�")) {
					sb = new StringBuffer("#") ;
					break ;
				}
				sb.append(n.getFirstChild().getNodeValue() + "#\n");
			}
			is.close();
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * ������
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		 System.out.println(getIp("200.151.111.111"));
		  
		 
	}
}
