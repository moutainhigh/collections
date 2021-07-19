package com.gwssi.report.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwssi.report.model.HomeData;


public class Quartz_RunCognos {
	private static Logger log = Logger.getLogger(Quartz_RunCognos.class);
	RunCognos runCognos=new RunCognos();
	@Autowired
	private CognosService cognos;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd HHmmssSSS");
	{
		log.info("cognos报表采集系统初始化成功");
	}
	
	public void worker() throws Exception{
		//int month=Integer.valueOf(sdf.format(new Date()).substring(4,6));
	//	runCognos.test();
//		if (month==3||month==9) {
//			log.info(month+"月份采集季报");
//		runCognos.getJiBao();
//		}else if (month==6) {
//			log.info("6月份采集半年报");
//			runCognos.getBanNianBao();
//		}else if(month==12){
//			log.info("12月份采集年报");
//			runCognos.getNianBao();
//		}else{
//			log.info(month+"月份采集月报");
//		runCognos.getYueBao();
//		}
		
		//runCognos.test();
		//runCognos.getYueBao();
		System.out.println("cognos登记报表采集完毕");
	}
	public void workOf12315()throws Exception{
	//	runCognos.getCognos12315();
		System.out.println("cognos 12315报表采集-workOf12315");
	} 
	public void workOf12315g()throws Exception{
	//	runCognos.getCognos12315g();
		System.out.println("cognos 12315报表采集-workOf12315g");

	}
	public void getHomePage() throws Exception{
		System.out.println("首页地图数据初始化中... ...");
		HomeData.dataList=cognos.getHomePage();
		HomeData.datalist2=cognos.getHomePage1();
		System.out.println("首页地图数据初始化完毕... ...");
		
	}
}
