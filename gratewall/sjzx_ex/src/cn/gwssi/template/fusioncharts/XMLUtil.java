package cn.gwssi.template.fusioncharts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XMLUtil {
	private Document document = null;

	public Document getDocument() {
		return document;
	}

	/**
	 * ���췽������ʼ��Document
	 */
	public XMLUtil() {
		document = DocumentHelper.createDocument();
	}

	/**
	 * ���ɸ��ڵ�
	 * 
	 * @param rootName
	 * @return
	 */
	public Element addRoot(String rootName) {
		Element root = document.addElement(rootName);
		return root;
	}

	/**
	 * ���ɽڵ�
	 * 
	 * @param parentElement
	 * @param elementName
	 * @return
	 */
	public Element addNode(Element parentElement, String elementName) {
		Element node = parentElement.addElement(elementName);
		return node;
	}

	/**
	 * Ϊ�ڵ�����һ������
	 * 
	 * @param thisElement
	 * @param attributeName
	 * @param attributeValue
	 */
	public void addAttribute(Element thisElement, String attributeName,
			String attributeValue) {
		thisElement.addAttribute(attributeName, attributeValue);
	}

	/**
	 * Ϊ�ڵ����Ӷ������
	 * 
	 * @param thisElement
	 * @param attributeNames
	 * @param attributeValues
	 */
	public void addAttributes(Element thisElement, String[] attributeNames,
			String[] attributeValues) {
		for (int i = 0; i < attributeNames.length; i++) {
			thisElement.addAttribute(attributeNames[i], attributeValues[i]);
		}
	}

	/**
	 * ���ӽڵ��ֵ
	 * 
	 * @param thisElement
	 * @param text
	 */
	public void addText(Element thisElement, String text) {
		thisElement.addText(text);
	}

	/**
	 * ��ȡ���յ�XML
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getXML() {
		String content = document.asXML();
		if (content.length() > 39) {
			content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ content.substring(39);
		}
		return content;
	}

	/**
	 * ����������ͼ
	 * 
	 * @param param
	 * @param values
	 * @param column
	 * @return
	 */
	public String getSimpleBarXml(Map param, String[] values, String[] column) {
		XMLUtil xml = new XMLUtil();
		Element chart = xml.addRoot("chart");
		// �������������
		Iterator it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			xml.addAttribute(chart, entry.getKey().toString(), entry.getValue()
					.toString());
		}
		for (int i = 0; i < values.length; i++) {
			Element set = xml.addNode(chart, "set");
			set.addAttribute("name", column[i]);
			set.addAttribute("value", values[i]);
		}
		return xml.getXML();
	}

	public static void main(String[] args) {
		XMLUtil xml = new XMLUtil();

		// Element graph = xml.addRoot("graph");
		// xml.addAttribute(graph, "caption", "����ͳ��");
		// xml.addAttribute(graph, "subCaption", "���������ͳ��");
		// xml.addAttribute(graph, "basefontsize", "12");
		// xml.addAttribute(graph, "xAxisName", "���������");
		// xml.addAttribute(graph, "decimalPrecision", "0");// С����ȷ�ȣ�0Ϊ��ȷ����λ
		// xml.addAttribute(graph, "showValues", "0");// �ڱ����ϲ���ʾ��ֵ
		// // List browserList =
		// // getServMgr().getChartService().getStatsByType("browser");
		// Random random = new Random();
		// for (int i = 0; i < 5; i++) {
		// Element set = xml.addNode(graph, "set");
		// set.addAttribute("name", "abc");
		// set.addAttribute("value", random.nextInt(10) + "");
		// set.addAttribute("color", Integer.toHexString(
		// (int) (Math.random() * 255 * 255 * 255)).toUpperCase());
		// }
		// String xmlStr = xml.getXML();
		HashMap map = new HashMap();
		map.put("caption", "����ͳ��");// ������
		map.put("subCaption", "���������ͳ��");// ������
		map.put("basefontsize", "12");// �����С
		map.put("xAxisName", "���������");// x������ֵ
		map.put("decimalPrecision", "0");// y������ֵ
		map.put("showValues", "0");// �Ƿ���ͼ������ʾ����
		String[] values = new String[] { "2", "1", "3" };
		String[] column = new String[] { "a", "b", "c" };
		System.out.println(xml.getSimpleBarXml(map, values, column));
	}
}
