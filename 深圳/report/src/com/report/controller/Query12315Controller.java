package com.report.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.report.service.Query12315Service;

@Controller
public class Query12315Controller {

	@Autowired
	private Query12315Service query12315Service;

	@RequestMapping("/12315")
	@ResponseBody
	public List get12315(String startTime, String endTime) throws ParseException {
		if(startTime==null) {
			return null;
		}
		if(endTime==null) {
			return null;
		}
		List list = query12315Service.getRecode(startTime, endTime);
		return list;
	}


	
	
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

		String str = "2018-10-17";
		Date dt = sdf.parse(str);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		System.out.println(reStr);
		//lastYearEndTime = reStr; // 去年这个日期
		Calendar cale = null;
		cale = Calendar.getInstance();
		System.out.println("==>" + sdf.format(cale.getTime()));
	//	endTime = sdf.format(cale.getTime());

		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);

		String firstday = sdf.format(cale.getTime());
		rightNow.add(Calendar.YEAR, -1);// 日期减1年
		 System.out.println(firstday);
		///startTime = firstday;
		str = firstday;
		dt = sdf.parse(str);
		rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.YEAR, -1);// 日期减1年
		dt1 = rightNow.getTime();
		reStr = sdf.format(dt1);
		 System.out.println(reStr);
		//lastYearStartTime = reStr;
	}
	
	
	
	

/*	public static void getDate() {

		// 获取当前年份、月份、日期
		Calendar cale = null;
		cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH) + 1;
		int day = cale.get(Calendar.DATE);
		int hour = cale.get(Calendar.HOUR_OF_DAY);
		int minute = cale.get(Calendar.MINUTE);
		int second = cale.get(Calendar.SECOND);
		int dow = cale.get(Calendar.DAY_OF_WEEK);
		int dom = cale.get(Calendar.DAY_OF_MONTH);
		int doy = cale.get(Calendar.DAY_OF_YEAR);

		System.out.println("Current Date: " + cale.getTime());
		System.out.println("Year: " + year);
		System.out.println("Month: " + month);
		System.out.println("Day: " + day);
		System.out.println("Hour: " + hour);
		System.out.println("Minute: " + minute);
		System.out.println("Second: " + second);
		System.out.println("Day of Week: " + dow);
		System.out.println("Day of Month: " + dom);
		System.out.println("Day of Year: " + doy);

		// 获取当月第一天和最后一天
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String firstday, lastday;
		// 获取前月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime());
		// 获取前月的最后一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime());
		System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);

		// 获取当前日期字符串
		Date d = new Date();
		System.out.println("当前日期字符串1：" + format.format(d));
		System.out.println("当前日期字符串2：" + year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second);

	}*/

}
