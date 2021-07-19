package com.gwssi.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

public class ListToXmlUtil {
	public static Document toXml(List dataList,String[] attributeNames,HashMap fixDatas,String encoding){
		Document doc = DocumentFactory.getInstance().createDocument();
		Element rootElement = doc.addElement("Datas");//添加根节点
		Element dataElement;//数据节点
		Element dataAttributeElement;//数据属性节点
		String attributeName;//数据属性名称
		String attributeValue;//数据属性值
		HashMap dataMap;//数据
		
		String dataName;//固定属性名称
		String dataValue;//固定属性值
		
		for(int i=0;i<dataList.size();i++){
			dataElement = rootElement.addElement("data");
			dataMap = (HashMap)dataList.get(i);
			for(int j=0;j<attributeNames.length;j++){
				attributeName = attributeNames[j];
				try {
					attributeValue = dataMap.get(attributeName).toString().trim();
				} catch (RuntimeException e) {
					//System.out.println("..."+attributeName);
					attributeValue = "";
				}
				dataAttributeElement = dataElement.addElement(attributeName);
				if(attributeValue!=null){
					dataAttributeElement.addText(attributeValue);
				}
				attributeName = null;
				attributeValue = null;
			}
			if(fixDatas!=null){
				Iterator dataIter = fixDatas.keySet().iterator();//加入固定属性值
				
				while(dataIter.hasNext()){
					dataName = (String)dataIter.next();
					dataValue = (String)fixDatas.get(dataName);
					dataAttributeElement = dataElement.addElement(dataName);
					dataAttributeElement.addText(dataValue);
					dataName = null;
					dataValue = null;
				}
			}
			dataMap.clear();
		}
		if(encoding!=null){
			doc.setXMLEncoding(encoding);
		}
		return doc;
	}
}
