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
		Element rootElement = doc.addElement("Datas");//��Ӹ��ڵ�
		Element dataElement;//���ݽڵ�
		Element dataAttributeElement;//�������Խڵ�
		String attributeName;//������������
		String attributeValue;//��������ֵ
		HashMap dataMap;//����
		
		String dataName;//�̶���������
		String dataValue;//�̶�����ֵ
		
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
				Iterator dataIter = fixDatas.keySet().iterator();//����̶�����ֵ
				
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
