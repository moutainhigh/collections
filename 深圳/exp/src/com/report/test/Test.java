package com.report.test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.report.service.DoQueryService;
import com.report.util.excell.Data01ExcelUtil;
import com.report.util.excell.Data02ExcelUtil;
import com.report.util.excell.Data03ExcelUtil;

public class Test {
	public static void main(String[] args) throws ParseException, IOException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml") ;
		String begTime = "2018-12-26";
		String endTime = "2019-03-25";
		DoQueryService ss = ac.getBean(DoQueryService.class);
		
		
		List task01JinNian= ss.task01JinNian(endTime);
		List task01QuNian= ss.task01QuNian(endTime);
		
		
		List task02NeiZiYouShiYiLai= ss.task02NeiZiYouShiYiLai(begTime,endTime);
		List task02NeiZiBenQi= ss.task02NeiZiBenQi(begTime,endTime);
		
		
		List task02SiYingYouShiYiLai= ss.task02SiYingYouShiYiLai(begTime,endTime);
		List task02SiYingBenQi= ss.task02SiYingBenQi(begTime,endTime);
		
		
		List task02WaiZiBenQi= ss.task02WaiZiBenQi(begTime,endTime);
		List task02WaiZiYouShiYiLai= ss.task02WaiZiYouShiYiLai(begTime,endTime);
		
		
		
		List task03NianChuDaoBenYue = ss.task03NianChuDaoBenYue(begTime,endTime);
		
		List task03BenYue = ss.task03NianChuDaoBenYue(begTime,endTime);

		
		
        List task04NianChuDaoBenYue = ss.task04NianChuDaoBenYue(begTime,endTime);
		
		List task04BenYue = ss.task04NianChuDaoBenYue(begTime,endTime);
		
		
		

		List task05BenQi= ss.task05BenQi(begTime,endTime);
		List task05YouShiYiLai= ss.task05YouShiYiLai(begTime,endTime);
		
		
		
		List taskForTaiWan= ss.taskForTaiWan(begTime,endTime);
		
		
		Data01ExcelUtil.createExcell(task01JinNian, task01QuNian, begTime, endTime, "tes");
		
		
		Data02ExcelUtil.createExcell(null,task02NeiZiYouShiYiLai, task02NeiZiBenQi,task02SiYingYouShiYiLai,task02SiYingBenQi,task02WaiZiBenQi,task02WaiZiYouShiYiLai, begTime, "tes");
		
		
		//Data03ExcelUtil.createExcell(task03BenQi, task03YouShiYiLai, begTime, endTime, "tesre");
	}
}
