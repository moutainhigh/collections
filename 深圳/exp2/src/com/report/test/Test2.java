package com.report.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.report.core.Business;
import com.report.service.DoQueryService;
import com.report.util.excell.Data01ExcelUtil;
import com.report.util.excell.Data02ExcelUtil;
import com.report.util.excell.Data03ExcelUtil;

public class Test2 {
	public static void main(String[] args) throws ParseException, IOException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml") ;
		String begTime = "2018-09-26";
		Business ss = ac.getBean(Business.class);
		
		
		ss.doQuery(begTime);
		
		
	}
}
