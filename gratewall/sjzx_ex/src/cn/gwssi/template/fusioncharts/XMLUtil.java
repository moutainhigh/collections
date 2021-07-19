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
	 * 构造方法，初始化Document
	 */
	public XMLUtil() {
		document = DocumentHelper.createDocument();
	}

	/**
	 * 生成根节点
	 * 
	 * @param rootName
	 * @return
	 */
	public Element addRoot(String rootName) {
		Element root = document.addElement(rootName);
		return root;
	}

	/**
	 * 生成节点
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
	 * 为节点增加一个属性
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
	 * 为节点增加多个属性
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
	 * 增加节点的值
	 * 
	 * @param thisElement
	 * @param text
	 */
	public void addText(Element thisElement, String text) {
		thisElement.addText(text);
	}

	/**
	 * 获取最终的XML
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
	 * 单序列柱形图
	 * 
	 * @param param
	 * @param values
	 * @param column
	 * @return
	 */
	public String getSimpleBarXml(Map param, String[] values, String[] column) {
		XMLUtil xml = new XMLUtil();
		Element chart = xml.addRoot("chart");
		// 设置整体的属性
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
		// xml.addAttribute(graph, "caption", "访问统计");
		// xml.addAttribute(graph, "subCaption", "浏览器类型统计");
		// xml.addAttribute(graph, "basefontsize", "12");
		// xml.addAttribute(graph, "xAxisName", "浏览器类型");
		// xml.addAttribute(graph, "decimalPrecision", "0");// 小数精确度，0为精确到个位
		// xml.addAttribute(graph, "showValues", "0");// 在报表上不显示数值
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
		map.put("caption", "访问统计");// 主标题
		map.put("subCaption", "浏览器类型统计");// 副标题
		map.put("basefontsize", "12");// 字体大小
		map.put("xAxisName", "浏览器类型");// x轴坐标值
		map.put("decimalPrecision", "0");// y轴坐标值
		map.put("showValues", "0");// 是否在图形上显示数字
		String[] values = new String[] { "2", "1", "3" };
		String[] column = new String[] { "a", "b", "c" };
		System.out.println(xml.getSimpleBarXml(map, values, column));
	}
}
