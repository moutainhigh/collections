package com.ly.common.dateutil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

//https://www.jianshu.com/p/a9c9ad8a25c4
//https://blog.csdn.net/qq_37511501/article/details/80193097
//https://www.jianshu.com/p/179528007ecd
//https://blog.csdn.net/rocling/article/details/82431632
public class MyDateUtilsForDayToWeek {

	public static String getWeekOfDate(String date) {
		if (StringUtils.isEmpty(date)) {
			return "";
		}
		//String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		String[] weekDaysName =  { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date dt1;
		try {
			dt1 = df.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt1);
			int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			return weekDaysName[intWeek];
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getWeekOfDate(Date date) {
		/**
		 *
		 * 功能描述: 根据日期获取星期几
		 *
		 * @auther: lkj
		 * @date: 2018/4/3 下午1:46
		 * @param: [date]
		 * @return: java.lang.String
		 *
		 */
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static void main(String[] args) {
		/*Date date = new Date();
		String str = MyDateUtils.getWeekOfDate(date);
		System.out.println(str);*/
		
		System.out.println(MyDateUtilsForDayToWeek.getWeekOfDate("20160301"));
	}
}
