package com.gwssi.common.rodimus.report.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: CSS Date: 2010-10-14 Time: 15:10:14 To change
 * this template use File | Settings | File Templates.
 */
public class DateUtil {
	/**
     * 获取当前时间。
     * 
     * @return 返回当前时间
     */
	public static Calendar getCurrentTime() {
		Calendar ret = Calendar.getInstance();
		ret.setTime(new Date());
	    return ret;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
	    
	    List<String> list = new ArrayList<String>();
	    list.add("first");
	    list.add("mid");
	    list.add("end");
	    String s = list.toString();
	    String s1 = s.substring(1, s.length()-1);
	    String[] ss = s1.split(",");
	    //System.out.println("a"+ss[1]);
	    //long tmp = 259200000L;
		/*Date d = new Date();

		String d1 = "2012-1-10";
		String d2 = "2012-1-21";
		Calendar c1 = DateUtil.parseDate(d1);
		Calendar c2 = DateUtil.parseDate(d2);
		//System.out.println(c1.get(Calendar.DAY_OF_WEEK));
		int count = DateUtil.calcWorkday(c1, c2);*/
		//System.out.println(count);
		//int i = DateUtil.calcMonthDays(c1);
	    Calendar calendar = Calendar.getInstance();
	    String string = DateUtil.toDateStr(calendar);
		//System.out.println(string);
	
		
		long t1 = 1336725830000L;
        long t2 = 1336984110246L;
        Date d1 = new Date(t1);
        Date d2 = new Date(t2);
        Calendar c1 = DateUtil.convUtilDateToUtilCalendar(d1);
        Calendar c2 = DateUtil.convUtilDateToUtilCalendar(d2);
        System.out.println(d1);
        System.out.println(d2);
        System.out.println( DateUtil.toDateStr(c1));
        System.out.println( DateUtil.toDateStr(c2));
	}

	private final static Logger logger = Logger.getLogger(DateUtil.class);

	/**
	 * 定义常见的时间格式
	 */
	private static String[] dateFormat = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
			"yyyy/MM/dd HH:mm:ss", "yyyy年MM月dd日HH时mm分ss秒", "yyyy/MM/dd",
			"yy-MM-dd", "yy/MM/dd", "yyyy年MM月dd日", "HH:mm:ss",
			"yyyyMMddHHmmss", "yyyyMM","yyyyMMdd", "yyyy.MM.dd", "yy.MM.dd" };

	/**
	 * 周几
	 */
	private static String[] weekDay = {"周日","周一","周二","周三","周四","周五","周六"};
	
	/**
	 * 获取中文周几
	 * @param day  1-7  SUN-SAT
	 * @return
	 */
	public static String getWeekDay(int day) {
	    return weekDay[day-1];
    }

    
	
	
	/**
	 * 将日期格式从 java.util.Calendar 转到 java.sql.Timestamp 格式
	 * 
	 * @param date
	 *            java.util.Calendar 格式表示的日期
	 * @return java.sql.Timestamp 格式表示的日期
	 */
	public static Timestamp convUtilCalendarToSqlTimestamp(Calendar date) {
		if (date == null)
			return null;
		else
			return new Timestamp(date.getTimeInMillis());
	}

	/**
	 * 将日期格式从 java.util.Timestamp 转到 java.util.Calendar 格式
	 * 
	 * @param date
	 *            java.sql.Timestamp 格式表示的日期
	 * @return java.util.Calendar 格式表示的日期
	 */
	public static Calendar convSqlTimestampToUtilCalendar(Timestamp date) {
		if (date == null)
			return null;
		else {
			java.util.GregorianCalendar gc = new java.util.GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			return gc;
		}
	}

	/**
	 * 解析一个字符串，形成一个Calendar对象，适应各种不同的日期表示法
	 * 
	 * @param dateStr
	 *            期望解析的字符串，注意，不能传null进去，否则出错
	 * @return 返回解析后的Calendar对象 <br>
	 * <br>
	 *         可输入的日期字串格式如下： <br>
	 *         "yyyy-MM-dd HH:mm:ss", <br>
	 *         "yyyy/MM/dd HH:mm:ss", <br>
	 *         "yyyy年MM月dd日HH时mm分ss秒", <br>
	 *         "yyyy-MM-dd", <br>
	 *         "yyyy/MM/dd", <br>
	 *         "yy-MM-dd", <br>
	 *         "yy/MM/dd", <br>
	 *         "yyyy年MM月dd日", <br>
	 *         "HH:mm:ss", <br>
	 *         "yyyyMMddHHmmss", <br>
	 *         "yyyyMMdd", <br>
	 *         "yyyy.MM.dd", <br>
	 *         "yy.MM.dd"
	 */
	public static Calendar parseDate(String dateStr) {
	    if (dateStr == null || "".equals(dateStr) || dateStr.trim().length() == 0) {
            return null;
        }

		Date result = parseDate(dateStr, 0);
		Calendar cal = Calendar.getInstance();
		cal.setTime(result);

		return cal;
	}

	/**
	 * 将一个日期转成日期时间格式，格式这样 2002-08-05 21:25:21
	 * 
	 * 请参考toDateStrWithTime；
	 * @param date
	 *            期望格式化的日期对象
	 * @return 返回格式化后的字符串 <br>
	 * @deprecated
	 * 
	 * <br>
	 *         例： <br>
	 *         调用： <br>
	 *         Calendar date = new GregorianCalendar(); <br>
	 *         String ret = DateUtils.toDateTimeStr(date); <br>
	 *         返回： <br>
	 *         ret = "2002-12-04 09:13:16";
	 */
	public static String toDateTimeStr(Calendar date) {
		if (date == null)
			return "";
		return new SimpleDateFormat(dateFormat[0]).format(date.getTime());
	}
	/**
	 * 将一个日期转成日期时间格式，格式这样 2002-08-05 21:25:21
	 * 
	 * @param date
	 *            期望格式化的日期对象
	 * @return 返回格式化后的字符串 <br>
	 * <br>
	 *         例： <br>
	 *         调用： <br>
	 *         Calendar date = new GregorianCalendar(); <br>
	 *         String ret = DateUtils.toDateTimeStr(date); <br>
	 *         返回： <br>
	 *         ret = "2002-12-04 09:13:16";
	 */
	public static String toDateStrWithTime(Calendar date) {
		if (date == null)
			return "";
		return new SimpleDateFormat(dateFormat[1]).format(date.getTime());
	}

	/**
	 * 将一个日期转成日期时间格式，格式这样 2002-08-05 21:25:21
	 * 
	 * @param date
	 *            期望格式化的日期对象
	 * @return 返回格式化后的字符串 <br>
	 * <br>
	 *         例： <br>
	 *         调用： <br>
	 *         Calendar date = new GregorianCalendar(); <br>
	 *         String ret = DateUtils.toDateTimeStr(date); <br>
	 *         返回： <br>
	 *         ret = "2002-12-04 09:13:16";
	 */
	public static String toDateTimeStr(int format, Calendar date) {
		if (date == null)
			return "";
		return new SimpleDateFormat(dateFormat[format]).format(date.getTime());
	}
	
	

	/**
	 * 将一个日期转成日期格式，格式这样 2002-08-05
	 * 
	 * @param date
	 *            期望格式化的日期对象
	 * @return 返回格式化后的字符串 <br>
	 * <br>
	 *         例： <br>
	 *         调用： <br>
	 *         Calendar date = new GregorianCalendar(); <br>
	 *         String ret = DateUtils.toDateStr(calendar); <br>
	 *         返回： <br>
	 *         ret = "2002-12-04";
	 */
	public static String toDateStr(Calendar date) {
		if (date == null)
			return "";
		return new SimpleDateFormat(dateFormat[0]).format(date.getTime());
	}
	public static String toDateStr(Calendar date,int index) {
		if (date == null)
			return "";
		if(index>=dateFormat.length)
			index = 1;
		return new SimpleDateFormat(dateFormat[index]).format(date.getTime());
	}

	public static String toDateStrByFormatIndex(Calendar date, int formatIndex) {
		if (date == null)
			return "";
		return new SimpleDateFormat(dateFormat[formatIndex]).format(date
				.getTime());
	}

	public static int calendarMinus(Calendar d1, Calendar d2) {
		if (d1 == null || d2 == null) {
			return 0;
		}

		d1.set(Calendar.HOUR_OF_DAY, 0);
		d1.set(Calendar.MINUTE, 0);
		d1.set(Calendar.SECOND, 0);

		d2.set(Calendar.HOUR_OF_DAY, 0);
		d2.set(Calendar.MINUTE, 0);
		d2.set(Calendar.SECOND, 0);

		long t1 = d1.getTimeInMillis();
		long t2 = d2.getTimeInMillis();
		logger.debug("DateUtils: d1 = " + DateUtil.toDateTimeStr(d1) + "(" + t1
				+ ")");
		logger.debug("DateUtils: d2 = " + DateUtil.toDateTimeStr(d2) + "(" + t2
				+ ")");
		long daylong = 3600 * 24 * 1000;
		t1 = t1 - t1 % (daylong);
		t2 = t2 - t2 % (daylong);

		long t = t1 - t2;
		int value = (int) (t / (daylong));

		logger.debug("DateUtils: d2 -d1 = " + value + " （天）");

		return value;
	}

	/**
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long calendarminus(Calendar d1, Calendar d2) {
		if (d1 == null || d2 == null) {
			return 0;
		}
		return (d1.getTimeInMillis() - d2.getTimeInMillis()) / (3600 * 24000);
	}

	/**
	 * 内部方法，根据某个索引中的日期格式解析日期
	 * 
	 * @param dateStr
	 *            期望解析的字符串
	 * @param index
	 *            日期格式的索引
	 * @return 返回解析结果
	 */
	public static Date parseDate(String dateStr, int index) {
	    if (dateStr == null || "".equals(dateStr) || dateStr.trim().length() == 0) {
            return null;
        }
	    
		DateFormat df = null;
		try {
			df = new SimpleDateFormat(dateFormat[index]);

			return df.parse(dateStr);
		} catch (ParseException pe) {
			return parseDate(dateStr, index + 1);
		} catch (ArrayIndexOutOfBoundsException aioe) {
			return null;
		}
	}

	/**
	 * 字符转日期,字符串格式："yyyy-MM-dd"，例如2006-01-01
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date StringToDate(String dateStr) {
		if (dateStr == null || "".equals(dateStr) || dateStr.trim().length() == 0) {
			return null;
		}
		return parseDate(dateStr, 3);
	}

	/**
	 * DATE to String，支持多种格式
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date, int index) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(dateFormat[index]).format(date);
	}

	/**
	 * DATE to String，转换结果格式为："yyyy-MM-dd"，例如2006-01-01
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(dateFormat[0]).format(date);
	}

	/**
	 * 将日期格式从 java.util.Date 转到 java.sql.Timestamp 格式 convUtilDateToSqlTimestamp <br>
	 * 
	 * @param date
	 *            java.util.Date 格式表示的日期
	 * @return Timestamp java.sql.Timestamp 格式表示的日期
	 */
	public static Timestamp convUtilDateToSqlTimestamp(Date date) {
		if (date == null)
			return null;
		else
			return new Timestamp(date.getTime());
	}

	public static Calendar convUtilDateToUtilCalendar(Date date) {
		if (date == null)
			return null;
		else {
			java.util.GregorianCalendar gc = new java.util.GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			return gc;
		}
	}

	/**
	 * 内部方法，根据某个索引中的日期格式解析日期
	 * 
	 * @param dateStr
	 *            期望解析的字符串
	 * @param index
	 *            日期格式的索引
	 * @return 返回解析结果
	 */
	public static Timestamp parseTimestamp(String dateStr, int index) {
		DateFormat df = null;
		try {
			df = new SimpleDateFormat(dateFormat[index]);

			return new Timestamp(df.parse(dateStr).getTime());
		} catch (ParseException pe) {
			return new Timestamp(parseDate(dateStr, index + 1).getTime());
		} catch (ArrayIndexOutOfBoundsException aioe) {
			return null;
		}
	}

	/**
	 * 内部方法，根据默认的日期格式“yyyy-MM-dd”解析日期
	 * 
	 * @param dateStr
	 *            期望解析的字符串
	 * @return 返回解析结果
	 */
	public static Timestamp parseTimestamp(String dateStr) {
		DateFormat df = null;
		try {
			df = new SimpleDateFormat(dateFormat[0]);
			return new Timestamp(df.parse(dateStr).getTime());
		} catch (ParseException pe) {
			return null;
		} catch (ArrayIndexOutOfBoundsException aioe) {
			return null;
		}
	}

	public static int calcMonthDays(Calendar date) {
	    if (date == null) {
            return 0;
        }
		Calendar t1 = (Calendar) date.clone();
		Calendar t2 = (Calendar) date.clone();
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH);
		t1.set(year, month, 1);
		t2.set(year, month + 1, 1);
		t2.add(Calendar.DAY_OF_YEAR, -1);
		return calendarMinus(t2, t1) + 1;
	}

	/**
	 * 对外接口
	 * 
	 * @param date
	 *            传入日期类型
	 * @return 返回大写日期字符串
	 */
	public static String toUpperCase(Date date) {
	    if (date == null) {
            return "";
        }
		return dataToUpper(date);
	}

	// 日期转化为大小写
	private static String dataToUpper(Date date) {
	    if (date == null) {
            return "";
        }
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH) + 1;
		int day = ca.get(Calendar.DAY_OF_MONTH);
		return numToUpper(year) + "年" + monthToUppder(month) + "月"
				+ dayToUppder(day) + "日";
	}

	// 将数字转化为大写
	private static String numToUpper(int num) {
		// String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
		String u[] = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		char[] str = String.valueOf(num).toCharArray();
		String rstr = "";
		for (int i = 0; i < str.length; i++) {
			rstr = rstr + u[Integer.parseInt(str[i] + "")];
		}
		return rstr;
	}
	
	/**
	 * 对外接口
	 * 
	 * @param date
	 *            传入日期类型
	 * @return 返回大写日期字符串如：二〇一二年三月十三日
	 */
	public static String toUpperCases(Date date) {
	    if (date == null) {
            return "";
        }
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH) + 1;
		int day = ca.get(Calendar.DAY_OF_MONTH);
		return numToUpper1(year) + "年" + monthToUppder(month) + "月"
				+ dayToUppder(day) + "日";
	}	
	
	// 将数字转化为大写
	private static String numToUpper1(int num) {
		// String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
		String u[] = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		char[] str = String.valueOf(num).toCharArray();
		String rstr = "";
		for (int i = 0; i < str.length; i++) {
			rstr = rstr + u[Integer.parseInt(str[i] + "")];
		}
		return rstr;
	}

	// 月转化为大写
	private static String monthToUppder(int month) {
		if (month < 10) {
			return numToUpper(month);
		} else if (month == 10) {
			return "十";
		} else {
			return "十" + numToUpper(month - 10);
		}
	}

	// 日转化为大写
	private static String dayToUppder(int day) {
		if (day < 20) {
			return monthToUppder(day);
		} else {
			char[] str = String.valueOf(day).toCharArray();
			if (str[1] == '0') {
				return numToUpper(Integer.parseInt(str[0] + "")) + "十";
			} else {
				return numToUpper(Integer.parseInt(str[0] + "")) + "十"
						+ numToUpper(Integer.parseInt(str[1] + ""));
			}
		}
	}
	
	public static int calcWorkday(Calendar c1, Calendar c2){
	    int count = 0;
	    if (c1==null) {
            return count;
        }
	    Calendar tempCal = Calendar.getInstance();
	    tempCal.setTime(c1.getTime());
	    
	    //如果结束时间在开始时间之后
        while (c2.after(tempCal)
                && !((tempCal.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (tempCal
                        .get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)))) {
            int day = tempCal.get(Calendar.DAY_OF_WEEK);
            if (!(day == 1 || day == 7)) {
                count++;
            }
            tempCal.add(Calendar.DATE, 1);
        }
        return count;
	}
	/**
	 * 获得大写的年份，便于年度、年
	 *<p>
	 *方法描述|
	 *
	 *</p>
	 *<p>
	 *2012-3-20
	 *</p>
	 *<p>
	 *Author:石佩|TEL:18971612939|QQ:408370389
	 *</p>
	 * @param date
	 * @return
	 */

	public static String getUpperYear(Date date){
	    if (date == null) {
            return "";
        }
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int year = ca.get(Calendar.YEAR);		
		return numToUpper1(year);
	}
	/**
	 * 获得大写的月份
	 *<p>
	 *方法描述|
	 *
	 *</p>
	 *<p>
	 *2012-3-20
	 *</p>
	 *<p>
	 *Author:石佩|TEL:18971612939|QQ:408370389
	 *</p>
	 * @param date
	 * @return
	 */
	public static String getUpperMonth(Date date){
	    if (date == null) {
            return "";
        }
	    
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int month = ca.get(Calendar.MONTH) + 1;
		return monthToUppder(month);
	}
	/**
	 * 获得大写的日期天
	 *<p>
	 *方法描述|
	 *
	 *</p>
	 *<p>
	 *2012-3-20
	 *</p>
	 *<p>
	 *Author:石佩|TEL:18971612939|QQ:408370389
	 *</p>
	 * @param date
	 * @return
	 */
	public static String getUpperDay(Date date){
	    if (date == null) {
            return "";
        }
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int day = ca.get(Calendar.DAY_OF_MONTH);
		return dayToUppder(day);
	}	
	
	public static String format(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ret = sdf.format(date);
		return ret;
	}
}