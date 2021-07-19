package com.report.test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.report.comon.TransferTime;
import com.report.service.DoQueryService;
import com.report.util.UUIDUtils;
import com.report.util.excell.Data01ExcelUtil;
import com.report.util.excell.Data02ExcelUtil;
import com.report.util.excell.Data03ExcelUtil;

public class Test {
	public static void main(String[] args) throws ParseException, IOException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml") ;
	
		String timeStr = "2019-09-25";
		
		Map<String,String> map = TransferTime.getStartTimeAndEndTimeByStr(timeStr);
		
		//String threeMonthAgoStr = map.get("查询截止时间三个月前的结束时间");
		String endTime = map.get("选择查询截止时间");
		String quNianTongQi =  map.get("选择查询截止时间的去年同期时间");
		String begTime = map.get("查询截止时间三个月前的开始时间");
		String lastYearbegTime = map.get("查询去年与当前月份一致的三个月前时间");
		String lastYearNichuBeginTime = map.get("去年年初开始时间");
		String lastYearEndTime = map.get("选择查询截止时间的去年同期时间");
		String niChuShiJian = map.get("今年年初时间");
		DoQueryService ss = ac.getBean(DoQueryService.class);
		
		
		//1.深圳各区优势产业分布
		List task01JinNian= ss.task01JinNian(endTime);
		
		List task01QuNian= ss.task01QuNian(quNianTongQi);
		
		//2.深圳本期新登记企业前十名监管所
		List task02NeiZiYouShiYiLai= ss.task02NeiZiYouShiYiLai(niChuShiJian,endTime);
		List task02NeiZiBenQi= ss.task02NeiZiBenQi(niChuShiJian,endTime);
		
		
		List task02SiYingYouShiYiLai= ss.task02SiYingYouShiYiLai(niChuShiJian,endTime);
		List task02SiYingBenQi= ss.task02SiYingBenQi(niChuShiJian,endTime);
		
		
		List task02WaiZiBenQi= ss.task02WaiZiBenQi(niChuShiJian,endTime);
		List task02WaiZiYouShiYiLai= ss.task02WaiZiYouShiYiLai(niChuShiJian,endTime);
		
		
		//3.各辖区局当期新设立个体前三名的监管所 ,开始时间为本月往前推三个月
		List task03NianChuDaoBenYue = ss.task03NianChuDaoBenYue(niChuShiJian,endTime);
		
		List task03BenYue = ss.task03NianChuDaoBenYue(begTime,endTime);

		
		
		//3.各辖区局当期新设立个体前三名的监管所 ,开始时间为本月往前推三个月去年同期时间
		List task03QuNianBenYue = ss.task03QuNianDaoBenYue(lastYearNichuBeginTime,lastYearEndTime);
		
		List task03QuNianDanQi = ss.task03QuNianDangQi(lastYearbegTime,lastYearEndTime);

		
		
		
		
		//4.各辖区局当期新设立企业前三名的监管所 ,,开始时间为本月往前推三个月
        List task04NianChuDaoBenYue = ss.task04NianChuDaoBenYue(niChuShiJian,endTime);
		
		List task04DangQi = ss.task04DangQi(begTime,endTime);
		
		//4.各辖区局当期新设立企业前三名的监管所 ,,开始时间为本月往前推三个月--去年同期时间
		 List task04QuNianNianChuDaoBenYue = ss.task04QuNianNianChuDaoBenYue(lastYearNichuBeginTime,lastYearEndTime);
			
	     List task04QuNianDangQi = ss.task04QuNianDangQi(lastYearbegTime,lastYearEndTime);
			
		
		
		
		
		
		
		//5.深圳本期新登记个体前十名监管所
		List task05BenQi= ss.task05BenQi(niChuShiJian,endTime);
		List task05YouShiYiLai= ss.task05YouShiYiLai(niChuShiJian,endTime);

		
		
	
		
		
		
	}
}
