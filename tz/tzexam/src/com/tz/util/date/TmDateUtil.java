package com.tz.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

import com.tz.util.TzStringUtils;

/** 时间日期工具类 **/
public class TmDateUtil {
	public static final String Format_Date = "yyyy-MM-dd";
	public static final String Format_Time = "HH:mm:ss";
	public static final String Format_DateTime = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATE_TIME = "yyyy-MM-dd-HH-mm-ss"; 

	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public static String getCurrentDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	public static String getCurrentDatetime(Date date) {
		return new SimpleDateFormat(Format_DateTime).format(date);
	}
	
	public static String getCurrentDate(Date date,String format) {
		return new SimpleDateFormat(format).format(date);
	}
	
	
	
	public static String getCurrentDate(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	public static String getCurrentTime() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	public static String getCurrentTime(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	public static String getCurrentDateTime() {
		String format = "yyyy-MM-dd HH:mm:ss";
		return getCurrentDateTime(format);
	}

	public static int getDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		int week = cal.get(7)-1;
		if(week<=0)week=7;
		return week;
	}

	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(7);
	}

	public static int getDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(5);
	}

	public static int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(5);
	}

	public static int getMaxDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(5);
	}

	public static String getFirstDayOfMonth(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(date));
		cal.set(5, 1);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	public static int getDayOfYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(6);
	}

	public static int getDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(6);
	}

	public static int getDayOfWeek(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(date));
		return cal.get(7);
	}

	public static int getDayOfMonth(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(date));
		return cal.get(5);
	}

	public static int getDayOfYear(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parse(date));
		return cal.get(6);
	}

	public static String getCurrentDateTime(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	public static String toString(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String toDateTimeString(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static String toString(Date date, String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(date);
	}

	public static String toTimeString(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat("HH:mm:ss").format(date);
	}

	public static int compare(String date1, String date2) {
		return compare(date1, date2, "yyyy-MM-dd");
	}

	public static int compareTime(String time1, String time2) {
		return compareTime(time1, time2, "HH:mm:ss");
	}

	public static int compare(String date1, String date2, String format) {
		Date d1 = parse(date1, format);
		Date d2 = parse(date2, format);
		return d1.compareTo(d2);
	}

	public static int compareTime(String time1, String time2, String format) {
		String[] arr1 = time1.split(":");
		String[] arr2 = time2.split(":");
		if (arr1.length < 2) {
			throw new RuntimeException("错误的时间值:" + time1);
		}
		if (arr2.length < 2) {
			throw new RuntimeException("错误的时间值:" + time2);
		}
		int h1 = Integer.parseInt(arr1[0]);
		int m1 = Integer.parseInt(arr1[1]);
		int h2 = Integer.parseInt(arr2[0]);
		int m2 = Integer.parseInt(arr2[1]);
		int s1 = 0;
		int s2 = 0;
		if (arr1.length == 3) {
			s1 = Integer.parseInt(arr1[2]);
		}
		if (arr2.length == 3) {
			s2 = Integer.parseInt(arr2[2]);
		}
		if ((h1 < 0) || (h1 > 23) || (m1 < 0) || (m1 > 59) || (s1 < 0)
				|| (s1 > 59)) {
			throw new RuntimeException("错误的时间值:" + time1);
		}
		if ((h2 < 0) || (h2 > 23) || (m2 < 0) || (m2 > 59) || (s2 < 0)
				|| (s2 > 59)) {
			throw new RuntimeException("错误的时间值:" + time2);
		}
		if (h1 != h2) {
			return ((h1 > h2) ? 1 : -1);
		}
		if (m1 == m2) {
			if (s1 == s2) {
				return 0;
			}
			return ((s1 > s2) ? 1 : -1);
		}

		return ((m1 > m2) ? 1 : -1);
	}

	public static boolean isTime(String time) {
		String[] arr = time.split(":");
		if (arr.length < 2)
			return false;
		try {
			int h = Integer.parseInt(arr[0]);
			int m = Integer.parseInt(arr[1]);
			int s = 0;
			if (arr.length == 3) {
				s = Integer.parseInt(arr[2]);
			}
			if ((h < 0) || (h > 23) || (m < 0) || (m > 59) || (s < 0)
					|| (s > 59))
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean isDate(String date) {
		String[] arr = date.split("-");
		if (arr.length < 3)
			return false;
		try {
			int y = Integer.parseInt(arr[0]);
			int m = Integer.parseInt(arr[1]);
			int d = Integer.parseInt(arr[2]);
			if ((y < 0) || (m > 12) || (m < 0) || (d < 0) || (d > 31))
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean isWeekend(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int t = cal.get(7);

		return ((t == 7) || (t == 1));
	}

	public static boolean isWeekend(String str) {
		return isWeekend(parse(str));
	}

	public static Date parse(String str) {
		if (TzStringUtils.isEmpty(str))
			return null;
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date parse(String str, String format) {
		if (TzStringUtils.isEmpty(str))
			return null;
		try {
			SimpleDateFormat t = new SimpleDateFormat(format);
			return t.parse(str);
		} catch (ParseException e) {
		}
		return null;
	}

	public static Date parseDateTime(String str) {
		if (TzStringUtils.isEmpty(str)) {
			return null;
		}
		if (str.length() <= 10)
			return parse(str);
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date parseDateTime(String str, String format) {
		if (TzStringUtils.isEmpty(str))
			return null;
		try {
			SimpleDateFormat t = new SimpleDateFormat(format);
			return t.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date addMinute(Date date, int count) {
		return new Date(date.getTime() + 60000L * count);
	}

	public static Date addHour(Date date, int count) {
		return new Date(date.getTime() + 3600000L * count);
	}

	public static Date addDay(Date date, int count) {
		return new Date(date.getTime() + 86400000L * count);
	}

	public static Date addWeek(Date date, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(3, count);
		return c.getTime();
	}

	public static Date addMonth(Date date, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(2, count);
		return c.getTime();
	}

	public static Date addYear(Date date, int count) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(1, count);
		return c.getTime();
	}

	public static String toDisplayDateTime(String date) {
		if (TzStringUtils.isEmpty(date))
			return null;
		try {
			if (isDate(date)) {
				return toDisplayDateTime(parse(date));
			}
			SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = t.parse(date);
			return toDisplayDateTime(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "不是标准格式时间!";
	}

	public static String toDisplayDateTime(Date date) {
		long minite = (System.currentTimeMillis() - date.getTime()) / 60000L;
		System.out.println(System.currentTimeMillis());
		System.out.println(date.getTime());
		System.out.println(minite);
		if (minite < 60L) {
			return toString(date, "MM-dd") + " " + minite + "分钟前";
		}
		if (minite < 1440L) {
			return toString(date, "MM-dd") + " " + (minite / 60L) + "小时前";
		}
		return toString(date, "MM-dd") + " " + (minite / 1440L) + "天前";
	}
	
	public static String parseDateTime(String format, String str, int dateStyle) throws ParseException {
		return DateFormat.getDateTimeInstance(dateStyle,dateStyle).format(parseDateTime(str, format));
	}
	
	public static Date dateToString(String time){
		Date startTime = null;
		try {
			 startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startTime;
	}
	
	public static Date dateToString(String time,String format){
		Date startTime = null;
		try {
			 startTime = new SimpleDateFormat(format).parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startTime;
	}
	
	public static String getTimeFormat(String startTime){
		return getTimeFormat(dateToString(startTime));
	}
	

	public static String getTimeFormat(Date startTime){
		try{
			long startTimeMills = startTime.getTime();
			long endTimeMills = new Date().getTime();
			long diff = (endTimeMills - startTimeMills)/1000;//秒
			long day_diff  = (long) Math.floor(diff/86400);//天
			StringBuffer buffer = new StringBuffer();
			if(day_diff<0){
				return "[error],时间越界...";
			}else{
				if(day_diff==0 && diff<60){
					if(diff==0)diff=1;
					buffer.append(diff+"秒前");
				}else if(day_diff==0 && diff<120){
					buffer.append("1 分钟前");
				}else if(day_diff==0 && diff<3600){
					buffer.append(Math.round(Math.floor(diff/60))+"分钟前");
				}else if(day_diff==0 && diff<7200){
					buffer.append("1小时前");
				}else if(day_diff==0 && diff<86400){
					buffer.append(Math.round(Math.floor(diff/3600))+"小时前");
				}else if(day_diff==1){
					buffer.append("1天前");
				}else if(day_diff<7){
					buffer.append(day_diff+"天前");
				}else if(day_diff <30){
					buffer.append(Math.round(Math.ceil( day_diff / 7 )) + " 星期前");
				}else if(day_diff >=30 && day_diff<=179 ){
					buffer.append(Math.round(Math.ceil( day_diff / 30 )) + "月前");
				}else if(day_diff >=180 && day_diff<365){
					buffer.append("半年前");
				}else if(day_diff>=365){
					buffer.append(Math.round(Math.ceil( day_diff /30/12))+"年前");
				}
			}
			return buffer.toString();
		}catch(Exception ex){
			return "";
		}
	}
	
	/**
	 * 
	 * @param time(时间和分钟字符串格式是:8:10)
	 * @param addminute(加和减多少时间) 
	 * @param mark(add加时间，minus减时间)
	 * @return
	 * @throws ParseException
	 */
	public static String getHourMinus(String time,int addminute,String mark) {
		Date date = null;
		try {
			date = new SimpleDateFormat("HH:mm").parse(time);
			DateTime dateTime = new DateTime(date.getTime());
			DateTime d  = null;
			if(TzStringUtils.isEmpty(mark))mark="add";
			if(mark.equals("add")){
				d = dateTime.plusMinutes(addminute);
			}else{
				d = dateTime.minusMinutes(addminute);	
			}
			int hour = d.getHourOfDay();
			int minute = d.getMinuteOfHour();
			StringBuffer buffer = new StringBuffer();
			if(hour<10){
				buffer.append("0"+hour);
			}else{
				buffer.append(hour);
			}
			buffer.append(":");
			if(minute<10){
				buffer.append("0"+minute);
			}else{
				buffer.append(minute);
			}
			return buffer.toString();
		} catch (ParseException e) {
			return time;
		}
	}
	
}
