package com.report.comon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class TimeUtil {
	private static Logger logger = Logger.getLogger(TimeUtil.class); // 获取logger实例

	//https://www.cnblogs.com/bunuo/p/6140750.html
	//yyyy-MM-dd HH:mm:ss
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getLastYearByTimeString(String str, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		String year = null;
		try {
			date = format.parse(str);
			Calendar c = Calendar.getInstance();
			// 过去一年
			c.setTime(date);
			c.add(Calendar.YEAR, -1);
			Date y = c.getTime();
			year = format.format(y);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		logger.debug("获取到过去一年时间字符串：" + year);
		return year;
	}

	// 得到过去3个月的时间
	public static String getLastThreeMonth(String timeStr, String formatStr) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		String mon3 = null;
		try {
			date = format.parse(timeStr);
			c.setTime(date);
			c.add(Calendar.MONTH, -3);
			Date m3 = c.getTime();
			mon3 = format.format(m3);
			logger.debug("获取到过去三个月时间字符串：" + mon3);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return mon3;
	}
	
	
	
	public static String getDateBefore(String timeStr, int day,String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		Date dateReturn = null;
		try {
			date = format.parse(timeStr);
			Calendar now = Calendar.getInstance();
			now.setTime(date);
			now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
			
			dateReturn = now.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return format.format(dateReturn);
	}

	public static Date getEndMonthDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(year, month - 1, day);
		return calendar.getTime();
	}

	public static Date getStartMonthDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getTime();
	}

	public static String getMonthStartDateAndEndDateStr(String timeStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = TimeUtil.getStartMonthDate(2019, 3);
		Date d2 = TimeUtil.getEndMonthDate(2019, 3);
		String str1 = format.format(d1);
		String str2 = format.format(d2);

		return str1 + "_" + str2;
	}

	public static void main(String[] args) {
		String str = TimeUtil.getDateBefore("2019-06-25",91, "yyyy-MM-dd");
		System.out.println(str);
	}
}
