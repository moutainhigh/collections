package com.gwssi.dw.dq.business;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class ParamXMLAnalyze
{
	private InputStream is;
	
	private Document doc = null;
	
	public ParamXMLAnalyze(InputStream is)
	{
		this.is = is;
		try {
			doc = new SAXReader().read(is);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("生成XML出错");
			doc = null;
		}
	}
	
	public ParamXMLAnalyze(File file)
	{
		try {
			doc = new SAXReader().read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("生成XML出错");
			doc = null;
		}
	}

	public String analyzeParam(String paramName) {
//		System.out.println("paramName:" + paramName);
		Node node = doc.selectSingleNode("//param[@name='"+paramName+"']");
		int index = -1;
		if (node != null) {
			index = Integer.parseInt(((Element)node).attributeValue("index"));
		}
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter;
//		System.out.println("index:" + index);
		switch(index){
		case 1: //当日
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			return formatter.format(date);
		case 2: //当月
			formatter = new SimpleDateFormat("yyyy-MM");
			return formatter.format(date);
		case 3: //当年
			formatter = new SimpleDateFormat("yyyy");
			return formatter.format(date);
		}
		return null;
	}
	
	public String analyzeSql(String sql,String[] params) {
		for (int i=0;i < params.length;i++) { //解析并替换参数
			sql = sql.replaceAll("{"+ params[i] +"}", analyzeParam(params[i]));
		}
		return sql;
	}
	
	public String trans2Json() {
		List list = doc.selectNodes("/params/param");
		List paramList = new ArrayList();
		Iterator iter=list.iterator();
		while(iter.hasNext()) {
			Element element = (Element)iter.next();
			Map map = new HashMap();
			String name = element.attribute("name").getValue();
			String msg = element.attribute("msg").getValue();
			map.put("name", name);
			map.put("msg", msg);
			paramList.add(map);
		}
		Map oMap = new HashMap();
		oMap.put("params", paramList);
		return JSONObject.fromObject(oMap).toString();
	}

	public Document getDoc()
	{
		return doc;
	}

	public void setDoc(Document doc)
	{
		this.doc = doc;
	}

	public InputStream getIs()
	{
		return is;
	}

	public void setIs(InputStream is)
	{
		this.is = is;
	}

}
