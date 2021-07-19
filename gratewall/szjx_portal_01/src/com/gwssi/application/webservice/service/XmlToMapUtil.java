package com.gwssi.application.webservice.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * xml与map格式互换
 * 
 * @author lizheng
 * 
 */
public class XmlToMapUtil {

	/**
	 * 
	 * dom2String 将dom文件转化成String
	 * 
	 * @param document
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String dom2String(Document document) {
		return document.asXML();
	}

	/**
	 * 
	 * map2Dom将Map转化成dom, 此方法只适用于本系统不是通用的
	 * 
	 * @param dataMap
	 * @param count
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String map2Dom(Map dataMap) {
		int lvl = 0;
		Document document = DocumentHelper.createDocument();
		Element params = document.addElement("PARAMS");
		Map[] maps = null;
		for (Object obj : dataMap.keySet()) {
			lvl++;
			if (dataMap.get(obj) instanceof Map[]) {
				maps = (HashMap[]) dataMap.get(obj);
			} else {
				Element keyElement = params.addElement(String.valueOf(obj));
				keyElement.setText(String.valueOf(dataMap.get(obj)));

			}
			if (null != maps) {
				if (dataMap.keySet().size() == lvl) {
					Element data = params.addElement("DATA");
					Map map = new HashMap();
					for (int i = 0; i < maps.length; i++) {
						Element row = data.addElement("ROW");
						map = maps[i];
						for (Object o : map.keySet()) {
							Element keyElement = row.addElement(String
									.valueOf(o));
							keyElement.setText(String.valueOf(map.get(o)));
						}
					}
				}
			}
		}
		return dom2String(document);
	}

	/**
	 * 
	 * Dom2Map 将节点转化成Map
	 * 
	 * @param e
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static Map dom2Map(Element e) {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	public static Map<String, Object> dom2Map(String xml) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (xml == null)
			return map;
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
				Element e = (Element) iterator.next();
				List list = e.elements();
				if (list.size() > 0) {
					map.put(e.getName(), dom2Map(e));
				} else
					map.put(e.getName(), e.getText());
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		return map;
	}

	public static void main(String[] args) {
		HashMap dataMap = new HashMap();
		dataMap.put("APP_ID", "SM");
		dataMap.put("USER_ID", "zhangfengrui");
		String b = map2Dom(dataMap);
		xMLWriter(b);
	}

	public static String xMLWriter(String xml) {
		// stringWriter字符串是用来保存XML文档的
		StringWriter stringWriter = new StringWriter();
		try {
			// 将字符串转为XML;
			Document doc = DocumentHelper.parseText(xml);

			// 设置XML文档格式,漂亮的格式
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();

			// 设置XML编码方式,即是用指定的编码方式保存XML文档到字符串(String),这里也可以指定为GBK或是ISO8859-1
			outputFormat.setEncoding("UTF-8");

			// xmlWriter是用来把XML文档写入字符串的(工具)
			XMLWriter xmlWriter = new XMLWriter(stringWriter, outputFormat);

			// 把创建好的XML文档写入字符串
			xmlWriter.write(doc);

			// 打印字符串,即是XML文档
			System.out.println(stringWriter.toString());

			xmlWriter.close();

		} catch (DocumentException e2) {
			e2.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("把XML文档写入字符串失败!");
		}
		return stringWriter.toString();
	}
	
	/**
	 * 
	 * xmlType 将list转化成HashMap
	 * 
	 * @param List<Map> list
	 * @return HashMap
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static HashMap xmlType(List<Map> list){
		Map [] map = new HashMap[list.size()];
		HashMap dataMap = new HashMap();
		dataMap.put("COUNT", list.size());
		
		for(int i=0;i<list.size();i++){
			map[i] = new HashMap();
			map[i].put("FUNCTION_CODE", list.get(i).get("functionCode"));
			map[i].put("FUNCTION_NAME", list.get(i).get("functionName"));
			map[i].put("FUNCTION_NAME_SHORT", list.get(i).get("functionNameShort"));
			map[i].put("FUNCTION_TYPE", list.get(i).get("functionType"));
			map[i].put("SUPER_FUNC_CODE", list.get(i).get("superFuncCode"));
			map[i].put("FUNCTION_URL", list.get(i).get("functionUrl"));
			map[i].put("LEVEL_CODE", list.get(i).get("levelCode"));
			map[i].put("ORDER_NO", list.get(i).get("orderNo"));
			
		}
	
		dataMap.put("op", map);
		return dataMap;
	}

}
