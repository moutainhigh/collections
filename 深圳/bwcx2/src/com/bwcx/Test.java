package com.bwcx;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bwcx.dao.DocumentDao;
import com.bwcx.pojo.DocumentEntity;

public class Test {

	//https://www.cnblogs.com/jakeylove3/p/7799862.html
	//https://blog.csdn.net/zengdeqing2012/article/details/78864922
	//https://blog.csdn.net/qq_33537014/article/details/80944689
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");
		DocumentEntity doc = new DocumentEntity();
		
		DocumentDao docDao = ac.getBean(DocumentDao.class);
		
		
		Map map = new HashMap();
		//map.put("cid", "222"); 
		map.put("pageEnd", 10);
		map.put("pageBegin", 0);
		//List<DocumentEntity> list = docDao.getDocumentsByChannel(map);
		Integer i = docDao.getCount(map);
		/*for (DocumentEntity e : list) {
			System.out.println(e.getDocid()); 
		}*/
		
		//System.out.println(i);
		
		/*doc.setDocid("333");
		
		
		docDao.save(doc);*/
		
		
		/*
		 Map map = new HashMap();
		map.put("docId", "222"); 
		 
		  DocumentEntity dp = docDao.getDocumentDetail(map);
		String conts = dp.getContents();*/
		
		
	/*	map.put("docId", "1235");
	    dp = docDao.getDocumentDetail(map); ;
	    dp.setContents(conts);
	    
		System.out.println(dp.getContents());*/
		
		
		

	}
}
