package com.gwssi.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 当前系统日期各种格式处理帮助类
 * 
 * @author lifx
 * 
 */
public class CalendarUtil {

    public static SimpleDateFormat FORMAT1 = new SimpleDateFormat("yyyy");

    public static SimpleDateFormat FORMAT2 = new SimpleDateFormat("MM");

    public static SimpleDateFormat FORMAT3 = new SimpleDateFormat("dd");

    public static SimpleDateFormat FORMAT4 = new SimpleDateFormat("HH");

    public static SimpleDateFormat FORMAT5 = new SimpleDateFormat("mm");

    public static SimpleDateFormat FORMAT6 = new SimpleDateFormat("s");

    public static SimpleDateFormat FORMAT7 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat FORMAT8 = new SimpleDateFormat(
            "yyyy/MM/dd");

    public static SimpleDateFormat FORMAT9 = new SimpleDateFormat(
            "yyyyMMddHHmmss");

    public static SimpleDateFormat FORMAT10 = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    /**
     *yyyy-MM-dd
     */
    public static SimpleDateFormat FORMAT11 = new SimpleDateFormat(
            "yyyy-MM-dd");
    
    public static SimpleDateFormat FORMAT11_1 = new SimpleDateFormat(
            "yyyyMMdd");

    public static SimpleDateFormat FORMAT12 = new SimpleDateFormat(
            "yyyyMMddHHmmssSS");

    public static SimpleDateFormat FORMAT13 = new SimpleDateFormat(
            "yyyyMMddHHmmssSS");
    
    public static SimpleDateFormat FORMAT14 = new SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss");
    
    public static SimpleDateFormat FORMAT15 = new SimpleDateFormat("yyyy年MM月dd日");
    
    public static SimpleDateFormat FORMAT16 = new SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss.M");
    
    public static void main(String[] args){
    	long l = 1240280459880L;
    	Timestamp t_l = new Timestamp(l);
    	System.out.println(t_l);
    	
    	Timestamp t = Timestamp.valueOf("2009-04-08 00:00:00");
    	System.out.println(parse("2005-05-08","yyyy-MM-dd","yyyy年MM月dd日"));
    }
    
    /**
     * 转换英文日期到中文日期 2007年7月20日
     */
    public static String changeENCalendarToCNCalendar(String enCalendar){
        return FORMAT15.format(Timestamp.valueOf(enCalendar+" 00:00:00"));
    }
    
    /**
     * 取到当前的年份 2007年7月20日
     */
    public static String getCNCalendarByFormat(){
        return FORMAT15.format(getCurrentTime());
    }
    /**
     * 取到当前的年份
     */
    public static String getCurrYear() {
        return FORMAT1.format(getCurrentTime());
    }

    /**
     * 取到当前的月份
     */
    public static String getCurrMonth() {
        return FORMAT2.format(getCurrentTime());
    }

    /**
     * 取到当前的日
     */
    public static String getCurrDay() {
        return FORMAT3.format(getCurrentTime());
    }

    /**
     * 取到当前的时间的小时
     */
    public static String getCurrHour() {
        return FORMAT4.format(getCurrentTime());
    }

    /**
     * 取到当前的时间的分钟
     */
    public static String getCurrMinute() {
        return FORMAT5.format(getCurrentTime());
    }

    /**
     * 取到当前的时间的秒
     */
    public static String getCurrSecond() {
        return FORMAT6.format(getCurrentTime());
    }

    /**
     * 根据格式化类型取到当前的系统时间
     */
    public static String getCalendarByFormat(SimpleDateFormat sdf) {
        return sdf.format(getCurrentTime());
    }

    /**
     * 根据格式化类型取到当前的系统时间
     */
    public synchronized static String getSynchronizedCalendarByFormat(
            SimpleDateFormat sdf) {
        return sdf.format(getCurrentTime());
    }

    /**
     * 根据格式化类型取到当前的系统时间
     */
    public static String getCalendarByFormat(String str) {
        SimpleDateFormat format = new SimpleDateFormat(str);
        return format.format(getCurrentTime());
    }

    /**
     * 系统当前时间
     * 
     * @return
     */
    public static String getCurrentDateTime() {
        return FORMAT7.format(getCurrentTime());
    }
    
    /**
     * 系统当前时间
     * 
     * @return
     */
    public static String getCurrentDate() {
        return FORMAT11.format(getCurrentTime());
    }

    public static Timestamp getCurrentTime() {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        return t;
    }

    /**
     * 当前日期的前多少秒时间
     * 
     * @param second
     * @return
     */
    public static String getBeforeDateBySecond(
            SimpleDateFormat sdf, 
            long second) {
        Timestamp t = new Timestamp(System.currentTimeMillis() - second * 1000);
        return sdf.format(t);
    }

    /**
     * 当前日期的前多少秒时间
     * 
     * @param second
     * @return
     */
    public static String getBeforeDateBySecond(String format, long second) {
        Timestamp t = new Timestamp(System.currentTimeMillis() - second * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(t);
    }

    /**
     * 当前日期的后多少秒时间
     * 
     * @param second
     * @return
     */
    public static String getAfterDateBySecond(
            SimpleDateFormat sdf, 
            long second) {
        Timestamp t = new Timestamp(System.currentTimeMillis() + second * 1000);
        return sdf.format(t);
    }

    /**
     * 当前日期的后多少秒时间
     * 
     * @param second
     * @return
     */
    public static String getAfterDateBySecond(String format, long second) {
        Timestamp t = new Timestamp(System.currentTimeMillis() + second * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(t);
    }
    
    /**
     * 得到两个日期相差多少天
     * 
     * @param second
     * @return
     * @throws ParseException 
     */
    public static long getDayBetweenTwoDate(String start, String end) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date startDate = sdf.parse(start);
		Date endDate = sdf.parse(end);
		long t = (endDate.getTime()-startDate.getTime())/(3600*24*1000);
		return t;
    }
    
    /**
     * 当前日期的后多少秒时间
     * 
     * @param second
     * @return
     * @throws ParseException 
     */
    public static String getAfterDateBySecond(SimpleDateFormat format, String date, int day) throws ParseException {
    	Date temp = format.parse(date);
    	long second = day * 24 * 3600 * 1000;
    	Timestamp t = new Timestamp(temp.getTime() + second);                
        return format.format(t);
    }
    
    public static Date parseStringToDate(String date) throws ParseException{
    	return FORMAT11.parse(date);
    }
    
    public static String parse(String date, String frompattern, String toPattern){
    	SimpleDateFormat format = new SimpleDateFormat(frompattern);
    	Date d = null;
		try {
			d = format.parse(date);
	    	format.applyPattern(toPattern);
	    	return format.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
    }
    
    public static String getDayofWeek(Date date){
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);          
        int w=cal.get(java.util.Calendar.DAY_OF_WEEK)-1;
        if(w==0)w=7;
        return String.valueOf(w);
    }
    
}
