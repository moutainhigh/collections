package com.gwssi.common.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�XmlToMapUtil ��������dom map �໥ת�� �����ˣ�lizheng ����ʱ�䣺Apr 1,
 * 2013 11:07:13 AM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 1, 2013 11:07:13 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public class XmlToMapUtil
{

	/**
	 * 
	 * dom2String ��dom�ļ�ת����String
	 * 
	 * @param document
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static String dom2String(Document document)
	{
		return document.asXML();
	}

	
	
	/**
	 * 
	 * map2Dom��Mapת����dom, �˷���ֻ�����ڱ�ϵͳ����ͨ�õ�
	 * 
	 * @param dataMap
	 * @param count
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static String map2Dom(Map dataMap)
	{
		int lvl = 0;
		Document document = DocumentHelper.createDocument();
		Element params = document.addElement("params");
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
					Element data = params.addElement("data");
					Map map = new HashMap();
					for (int i = 0; i < maps.length; i++) {
						Element row = data.addElement("row");
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
	 * Dom2Map ��dom�ļ�ת����Map
	 * 
	 * @param xml
	 * @return Map<String,Object>
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static Map<String, Object> dom2Map(String xml)
	{
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

	/**
	 * 
	 * Dom2Map ���ڵ�ת����Map
	 * 
	 * @param e
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static Map dom2Map(Element e)
	{
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
						if (!obj.getClass().getName().equals(
								"java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName().equals(
								"java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals(
								"java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName().equals(
								"java.util.ArrayList")) {
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

	public static void main(String[] args)
	{
		HashMap dataMap = new HashMap();
		dataMap.put("LOGIN_NAME", "ssjbuser");
		dataMap.put("PASSWORD", "111111");
		dataMap.put("KSJLS", "1");// ��ʼ��¼��
		dataMap.put("JSJLS", "30");// ������¼��
		dataMap.put("SVR_CODE", "service28");// ������룬���ú�����
		dataMap.put("ZTS", "100");// ������룬���ú�����
		Map[] maps = new HashMap[2];
		Map map0 = new HashMap();
		map0.put("name", "liz");
		map0.put("age", "11");
		Map map1 = new HashMap();
		map1.put("name", "lsss");
		map1.put("age", "133");
		maps[0] = map0;
		maps[1] = map1;
		dataMap.put("GSDJ_INFO_ARRAY", maps);
		String b = map2Dom(dataMap);
		System.out.println("b:" + b);
	}

}
