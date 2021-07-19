package com.tz.util.date;

import static com.tz.util.date.TmDateUtil.Format_Date;
import static com.tz.util.date.TmDateUtil.Format_DateTime;
import static com.tz.util.date.TmDateUtil.parse;
import hirondelle.date4j.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.format.DateTimeFormat;

import com.tz.util.TzStringUtils;

public class TmDateTimeUtil {

	// public enum ExmayDateTimes {
	// TODAY, YESTERDAY, THIS_WEEK, LAST_WEEK, THIS_MONTH, LAST_MONTH,
	// THIS_YEAR, LAST_YEAR;
	// }
	// 今天
	public static final String TODAY = "today";
	// 昨天
	public static final String YESTERDAY = "yesterday";
	// 明天
	public static final String TOMORROW = "tomorrow";
	// 前天
	public static final String THE_DAY_BEFORE_YESTERDAY = "theDayBeforeYesterday";
	// 本周
	public static final String THIS_WEEK = "thisWeek";
	//本周第一天到今天的时间段
	public static final String NOW_WEEK = "nowWeek";
	// 上周
	public static final String LAST_WEEK = "lastWeek";
	// 本月
	public static final String THIS_MONTH = "thisMonth";
	// 本月第一天到尽头的时间段
	public static final String NOW_MONTH = "nowMonth";
	// 本月第一天到尽头的时间段
	public static final String NOW_MONTH_DAY = "nowMonthDay";
	// 上月
	public static final String LAST_MONTH = "lastMonth";
	// 今年
	public static final String THIS_YEAR = "thisYear";
	// 去年
	public static final String LAST_YEAR = "lastYear";
	// 前年
	public static final String THE_YEAR_BEFORE_LAST = "theYearBeforeLast";
	// 两年前
	public static final String TWO_YEAR_AGO = "twoYearsAgo";

	public static final String DATATIME_FORMAT = "YYYY-MM-DD hh:mm:ss";

	public static final String DATATIME_FORMAT_YMD = "YYYY-MM-DD";

	public static TmTimeInterval getDateTime(String dateTime) {
		Date now = new Date();
		return getDateTime(dateTime, now);
	}
	
	
	
	/**
	 * 获取年份
	 * @param dateString
	 * @return
	 */
	public static String getYear(String dateString){
		DateTime datetime = new DateTime(dateString);
		return String.valueOf(datetime.getYear());
	}
	/**
	 * 获取年份
	 * @param dateString
	 * @return
	 */
	public static String getYear(Date date){
		org.joda.time.DateTime datetime = new org.joda.time.DateTime(date);
		return String.valueOf(datetime.getYear());
	}
	
	/**
	 * 获取月份
	 * @param dateString
	 * @return
	 */
	public static String getMonth(String dateString){
		DateTime datetime = new DateTime(dateString);
		return String.valueOf(datetime.getMonth());
	}
	/**
	 * 获取月份
	 * @param dateString
	 * @return
	 */
	public static String getMonth(Date date){
		org.joda.time.DateTime datetime = new org.joda.time.DateTime(date);
		return String.valueOf(datetime.getMonthOfYear());
	}
	
	/**
	 * 获取日期
	 * @param dateString
	 * @return
	 */
	public static String getDay2(String dateString){
		return String.valueOf(getDay(dateString));
	}
	/**
	 * 获取日期
	 * @param dateString
	 * @return
	 */
	public static String getDay2(Date date){
		return String.valueOf(getDay(date));
	}
	
	/**
	 * 获取指定时间的开始时间
	 * @param dateTime
	 * @param timeDate
	 * @return
	 */
	public static String getStartTime(String timeDate) {
		Date starttime = TmDateTimeUtil.getDateTime("today",timeDate).getStartTime();
		return TmDateUtil.getCurrentDatetime(starttime);
	}
	
	/**
	 * 获取指定时间的开始时间
	 * @param dateTime
	 * @param timeDate
	 * @return
	 */
	public static String getStartTimeByDate(Date timeDate) {
		Date starttime = TmDateTimeUtil.getDateTime("today",timeDate).getStartTime();
		return TmDateUtil.getCurrentDatetime(starttime);
	}
	
	/**
	 * 获取指定时间的结束时间
	 * @param dateTime
	 * @param timeDate
	 * @return
	 */
	public static String getEndTime(String timeDate) {
		Date endtime = TmDateTimeUtil.getDateTime("today",timeDate).getEndTime();
		return TmDateUtil.getCurrentDatetime(endtime);
	}
	
	/**
	 * 获取指定时间的结束时间
	 * @param dateTime
	 * @param timeDate
	 * @return
	 */
	public static String getEndTimeByDate(Date timeDate) {
		Date endtime = TmDateTimeUtil.getDateTime("today",timeDate).getEndTime();
		return TmDateUtil.getCurrentDatetime(endtime);
	}

	public static TmTimeInterval getDateTime(String dateTime, String timeDate) {
		Date date = TmDateUtil.parse(timeDate, Format_Date);
		return getDateTime(dateTime, date);
	}
	
	/**
	 * 获取两个日期的所有天数
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static List<String> getDays(String starttime,String endtime){
		List<String> days = new ArrayList<String>();
		int count = TmDateTimeUtil.getNumDaysFrom(starttime, endtime);
		DateTime dt = new DateTime(starttime);
		for (int i = 0; i <=count; i++) {
			DateTime dateTime = dt.plusDays(i);
			days.add(dateTime.format("YYYY-MM-DD"));
			
		}
		return days;
	}
	
	/**
	 * 获取两个日期的所有天数
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static List<String> getDays(Date starttime,Date endtime){
		return getDays(TmDateUtil.getCurrentDate(starttime),TmDateUtil.getCurrentDate(endtime));
	}

	public static TmTimeInterval getDateTime(String dateTime, Date now) {
		TmTimeInterval interval = new TmTimeInterval();
		String nowTime = TmDateUtil.toString(now, Format_DateTime);
		DateTime dt = new DateTime(nowTime);
		if (dateTime.equals(TODAY)) {
			Date startTime = parse(dt.getStartOfDay().format(DATATIME_FORMAT),
					Format_DateTime);
			Date endTime = parse(dt.getEndOfDay().format(DATATIME_FORMAT),
					Format_DateTime);
			// Date endTime = new Date();
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}
		if (dateTime.equals(YESTERDAY)) {
			Date startTime = parse(dt.getStartOfDay().minusDays(1).format(
					DATATIME_FORMAT), Format_DateTime);
			Date endTime = parse(dt.getEndOfDay().minusDays(1).format(
					DATATIME_FORMAT), Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}

		if (dateTime.equals(TOMORROW)) {
			Date startTime = parse(dt.getStartOfDay().plusDays(1).format(
					DATATIME_FORMAT), Format_DateTime);
			Date endTime = parse(dt.getEndOfDay().plusDays(1).format(
					DATATIME_FORMAT), Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}

		if (dateTime.equals(THE_DAY_BEFORE_YESTERDAY)) {
			Date startTime = parse(dt.getStartOfDay().minusDays(2).format(
					DATATIME_FORMAT), Format_DateTime);
			Date endTime = parse(dt.getEndOfDay().minusDays(2).format(
					DATATIME_FORMAT), Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}
		if (dateTime.equals(THIS_WEEK)) {
			org.joda.time.DateTime time = DateTimeFormat.forPattern(
					Format_DateTime).parseDateTime(nowTime);
			int dayOfWeek = time.getDayOfWeek();
			Date startTime = parse(dt.getStartOfDay().minusDays(dayOfWeek - 1)
					.format(DATATIME_FORMAT), Format_DateTime);
			Date endTime = parse(dt.getEndOfDay().plusDays(7 - dayOfWeek)
					.format(DATATIME_FORMAT), Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}
		if (dateTime.equals(NOW_WEEK)) {
			org.joda.time.DateTime time = DateTimeFormat.forPattern(
					Format_DateTime).parseDateTime(nowTime);
			int dayOfWeek = time.getDayOfWeek();
			Date startTime = parse(dt.getStartOfDay().minusDays(dayOfWeek - 1)
					.format(DATATIME_FORMAT), Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(parse(getEndTimeByDate(new Date()),Format_DateTime));
			return interval;
		}
		if (dateTime.equals(LAST_WEEK)) {
			org.joda.time.DateTime time = DateTimeFormat.forPattern(
					Format_DateTime).parseDateTime(nowTime);
			int dayOfWeek = time.getDayOfWeek();
			Date startTime = parse(dt.getStartOfDay().minusDays(dayOfWeek + 6)
					.format(DATATIME_FORMAT), Format_DateTime);
			Date endTime = parse(dt.getEndOfDay().minusDays(dayOfWeek).format(
					DATATIME_FORMAT), Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}
		if (dateTime.equals(THIS_MONTH)) {
			Date startTime = parse(
					dt.getStartOfMonth().format(DATATIME_FORMAT),
					Format_DateTime);
			Date endTime = parse(dt.getEndOfMonth().format(DATATIME_FORMAT),
					Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}
		if(dateTime.equals(NOW_MONTH)) {
			Date startTime = parse(
					dt.getStartOfMonth().format(DATATIME_FORMAT),
					Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(parse(getEndTimeByDate(new Date()),Format_DateTime));
			return interval;
		}
		if (dateTime.equals(NOW_MONTH_DAY)) {
			int day = dt.getDay();
			Date startTime = parse(dt.getStartOfMonth().plusDays(day-30).format(TmDateTimeUtil.DATATIME_FORMAT),
					Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(parse(getEndTimeByDate(new Date()),Format_DateTime));
			return interval;
		}
		if (dateTime.equals(LAST_MONTH)) {
			int monthDays = dt.getNumDaysInMonth();
			int lastMonthDays = dt.getEndOfMonth().minusDays(monthDays)
					.getNumDaysInMonth();
			Date startTime = parse(dt.getStartOfMonth()
					.minusDays(lastMonthDays).format(DATATIME_FORMAT),
					Format_DateTime);
			Date endTime = parse(dt.getEndOfMonth().minusDays(monthDays)
					.format(DATATIME_FORMAT), Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}
		if (dateTime.equals(THIS_YEAR)) {
			int days;
			if (dt.isLeapYear()) {
				days = 366;
			} else {
				days = 365;
			}
			Date startTime = parse(dt.getStartOfDay().minusDays(
					dt.getDayOfYear() - 1).format(DATATIME_FORMAT),
					Format_DateTime);
			Date endTime = parse(dt.getEndOfDay().plusDays(
					365 - dt.getDayOfYear()).format(DATATIME_FORMAT),
					Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}
		if (dateTime.equals(LAST_YEAR)) {
			int days;
			if (dt.isLeapYear()) {
				days = 366;
			} else {
				days = 365;
			}
			int lastYearDays;
			DateTime time = dt.getStartOfDay().minusDays(dt.getDayOfYear() - 2);
			if (time.isLeapYear()) {
				lastYearDays = 366;
			} else {
				lastYearDays = 365;
			}
			Date startTime = parse(dt.getStartOfDay().minusDays(
					dt.getDayOfYear() + lastYearDays - 1).format(
					DATATIME_FORMAT), Format_DateTime);
			Date endTime = parse(dt.getEndOfDay().plusDays(
					days - dt.getDayOfYear() - lastYearDays).format(
					DATATIME_FORMAT), Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}
		if (dateTime.equals(THE_YEAR_BEFORE_LAST)) {
			org.joda.time.DateTime time = DateTimeFormat.forPattern(
					Format_DateTime).parseDateTime(nowTime);
			time = time.minusYears(2);
			nowTime = time.toString(Format_DateTime);
			dt = new DateTime(nowTime);
			Date startTime = parse(dt.getStartOfDay().minusDays(
					dt.getDayOfYear() - 1).format(DATATIME_FORMAT),
					Format_DateTime);
			Date endTime = parse(dt.getEndOfDay().plusDays(
					365 - dt.getDayOfYear()).format(DATATIME_FORMAT),
					Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}
		if (dateTime.equals(THE_YEAR_BEFORE_LAST)) {
			org.joda.time.DateTime time = DateTimeFormat.forPattern(
					Format_DateTime).parseDateTime(nowTime);
			time = time.minusYears(2);
			nowTime = time.toString(Format_DateTime);
			dt = new DateTime(nowTime);
			Date startTime = parse(dt.getStartOfDay().minusDays(
					dt.getDayOfYear() - 1).format(DATATIME_FORMAT),
					Format_DateTime);
			Date endTime = parse(dt.getEndOfDay().plusDays(
					365 - dt.getDayOfYear()).format(DATATIME_FORMAT),
					Format_DateTime);
			interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}
		if (dateTime.equals(TWO_YEAR_AGO)) {
			org.joda.time.DateTime time = DateTimeFormat.forPattern(
					Format_DateTime).parseDateTime(nowTime);
			time = time.minusYears(3);
			nowTime = time.toString(Format_DateTime);
			dt = new DateTime(nowTime);
			// Date startTime =
			// parse(dt.getStartOfDay().minusDays(dt.getDayOfYear()-1).format(DATATIME_FORMAT),
			// Format_DateTime);
			Date endTime = parse(dt.getEndOfDay().plusDays(
					365 - dt.getDayOfYear()).format(DATATIME_FORMAT),
					Format_DateTime);
			// interval.setStartTime(startTime);
			interval.setEndTime(endTime);
			return interval;
		}
		return null;
	}
	
	/**
	 * 获取这个当天的日期 2021-12-08 ==>返回8
	 * @param date
	 * @return
	 */
	public static Integer getDay(Date date){
		 return getDay(TmDateUtil.getCurrentDate(date));
	}
	
	/**
	 * 
	 * 获取这个当天的日期 2021-12-08 ==>返回8
	 * @param date
	 * @return
	 */
	public static Integer getDay(String date){
		 DateTime dateTime = new DateTime(date);
		 return dateTime.getDay();
	}
	/**
	 * 
	 * 获取这个当天的日期 2021-12-08 ==>返回8
	 * @param date
	 * @return
	 */
	public static Integer getDayT(Date date){
		org.joda.time.DateTime dateTime = new org.joda.time.DateTime(date);
		return dateTime.getDayOfMonth();
	}
	
	/**
	 * 
	 * 获取这个当天的日期 2021-12-08 ==>返回8
	 * @param date
	 * @return
	 */
	public static Integer getDayT(String date){
		return getDayT(TmDateUtil.parse(date));
	}
	
	/**
	 * 获取今天是星期几
	 * @param date
	 * @return
	 */
	public static Integer dayOfWeek(Object dateOrStringDate){
		org.joda.time.DateTime dateTime = new org.joda.time.DateTime(dateOrStringDate);
		return dateTime.getDayOfWeek();
	}
	

	/**
	 * 获取一个月有多少天
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getDayOfMonth(String date) {
		Date time = null;
		try {
			time = new SimpleDateFormat(Format_Date).parse(date);
			return getDayOfMonth(time);
		} catch (ParseException e) {
			return 31;
		}
	}
	
	/***
	 * 获取一个月有多少天
	 * @param date
	 * @return
	 */
	public static Integer getDayOfMonth(Date date) {
		String nowTime = new SimpleDateFormat(Format_DateTime).format(date);
		DateTime dt = new DateTime(nowTime);
		return dt.getNumDaysInMonth();
	}
	
	/**
	 * 获取两个日期的间隔天数  yyyy-MM-dd
	 * @param date
	 * @param date2
	 * @return
	 */
	public static Integer getNumDaysFrom(String date,String date2){
		DateTime dateTime = new DateTime(date);
		DateTime dateTime2 = new DateTime(date2);
		return dateTime.numDaysFrom(dateTime2);
	}
	
	/**
	 * 获取两个日期的间隔天数 yyyy-MM-dd
	 * @param date
	 * @param date2
	 * @return
	 */
	public static Integer getNumDaysFrom(Date date,Date date2){
		return getNumDaysFrom(TmDateUtil.getCurrentDate(date),TmDateUtil.getCurrentDate(date2));
	}

	/**
	 * 获取当前时间之前一个月的时间
	 * 
	 * @return
	 */
	public static String getMonthDay() {
		Date date = new Date();// 当前时间
		String dString = new SimpleDateFormat(Format_DateTime).format(date);// 时间转换
		DateTime dt = new DateTime(dString);
		String time = new SimpleDateFormat().format(getDateTime(LAST_MONTH)
				.getEndTime());
		Integer day = getDayOfMonth(time);
		DateTime mDateTime = dt.minusDays(day);// 当前时间减去上个月的天数
		return mDateTime.format(DATATIME_FORMAT_YMD);
	}

	/**
	 * 前一天时间 时间格式是(yyyy-MM-dd)
	 * 
	 * @return string
	 */
	public static String getMinusDays(String dateTime) {
		DateTime dt = new DateTime(dateTime);
		DateTime mDateTime = dt.minusDays(1);
		return mDateTime.format(DATATIME_FORMAT_YMD);
	}

	/**
	 * 前一天时间 时间格式是(yyyy-MM-dd)
	 * 
	 * @return Date
	 */
	public static Date minusDays(String dateTime) {
		try {
			return new SimpleDateFormat(Format_Date)
					.parse(getMinusDays(dateTime));
		} catch (ParseException e) {
			return new Date();
		}
	}

	/**
	 * 后一天时间 时间格式是(yyyy-MM-dd)
	 * 
	 * @return Date
	 */
	public static Date plusDays(String dateTime) {
		try {
			return new SimpleDateFormat(Format_Date)
					.parse(getPlusDays(dateTime));
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	

	/**
	 * 后一天时间 时间格式是(yyyy-MM-dd)
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String getPlusDays(String dateTime) {
		DateTime dt = new DateTime(dateTime);
		if (dt.gt(new DateTime(TmDateUtil.toString(new Date())))) {
			return TmDateUtil.getCurrentDate();
		} else {
			DateTime mDateTime = dt.plusDays(1);
			return mDateTime.format(DATATIME_FORMAT_YMD);
		}
	}

	/**
	 * 前一天时间 时间格式是(yyyy-MM-dd)
	 * 
	 * @return
	 */
	public static String getMinusDays(Date dateTime) {
		Date date = new Date();// 当前时间
		String dfmt = new SimpleDateFormat(Format_Date).format(date);// 时间转换
		return getMinusDays(dfmt);
	}
	

	/**
	 * 后一天时间 时间格式是(yyyy-MM-dd)
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String getPlusDays(Date dateTime) {
		Date date = new Date();// 当前时间
		String dfmt = new SimpleDateFormat(Format_Date).format(date);// 时间转换
		return getPlusDays(dfmt);
	}

	/**
	 * 获取某个时间段的时间范围值 2010-01-01===>2010-12-12
	 * 获取:2010-01-02,2010-01-03.....2010-12-11,2010-12-12
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */

	public static String getPrevOrNextDay(String type, String dateTime) {
		if (TzStringUtils.isNotEmpty(type) && type.equals(YESTERDAY)) {
			return getMinusDays(dateTime);
		}
		if (TzStringUtils.isNotEmpty(type) && type.equals(TOMORROW)) {
			return getPlusDays(dateTime);
		}
		return dateTime;
		// ExmayDateUtil.getCurrentDate(Format_Date);
	}

	public static Date getPrevOrNextDays(String type, String dateTime) {
		if (TzStringUtils.isNotEmpty(type) && type.equals(YESTERDAY)) {
			return minusDays(dateTime);
		}
		if (TzStringUtils.isNotEmpty(type) && type.equals(TOMORROW)) {
			return plusDays(dateTime);
		}
		return TmDateUtil.parse(dateTime);
		// ExmayDateUtil.parse(dateTime);
	}

	public static List<String> getDateRange(String beginDate, String endDate) {
		GregorianCalendar[] ga = null;
		List<String> list = new ArrayList<String>();
		try {
			ga = getDate(beginDate, endDate);
			for (GregorianCalendar day : ga) {
				list.add(String.valueOf(day.get(Calendar.YEAR)) + "-"
						+ String.valueOf(day.get(Calendar.MONTH) + 1) + "-"
						+ String.valueOf(day.get(Calendar.DAY_OF_MONTH)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static boolean validDate(String beginDate, String endDate) {
		DateTime beginDateTime = new DateTime(beginDate);
		DateTime endDateTime = new DateTime(endDate);
		if (beginDateTime.gt(endDateTime) || beginDateTime.equals(endDateTime)) {
			return false;
		}
		return true;
	}

	public static GregorianCalendar[] getDate(String beginDate, String endDate)
			throws ParseException {
		List<GregorianCalendar> v = new ArrayList<GregorianCalendar>();
		SimpleDateFormat sdf = new SimpleDateFormat(Format_Date);
		GregorianCalendar gc1 = new GregorianCalendar(), gc2 = new GregorianCalendar();
		gc1.setTime(sdf.parse(beginDate));
		gc2.setTime(sdf.parse(endDate));
		do {
			GregorianCalendar gc3 = (GregorianCalendar) gc1.clone();
			v.add(gc3);
			gc1.add(Calendar.DAY_OF_MONTH, 1);
		} while (!gc1.after(gc2));
		return v.toArray(new GregorianCalendar[v.size()]);
	}
	
	/**
	 * 获取当前月的下一个月
	 * @param date
	 * @return
	 */
	public static String getNextMonth(Date date){
		org.joda.time.DateTime datetime = new org.joda.time.DateTime(date);
		return datetime.plusMonths(1).toString("yyyy-MM-dd");
	}
	
	public static String getNextMonth(String dateString){
		org.joda.time.DateTime datetime = new org.joda.time.DateTime(TmDateUtil.parse(dateString));
		return datetime.plusMonths(1).toString("yyyy-MM-dd");
	}
	
	public static String getNextMonth(Date date,String  format){
		org.joda.time.DateTime datetime = new org.joda.time.DateTime(date);
		return datetime.plusMonths(1).toString(format);
	}
	
	public static String getNextMonth(String dateString,String  format){
		org.joda.time.DateTime datetime = new org.joda.time.DateTime(TmDateUtil.parse(dateString));
		return datetime.plusMonths(1).toString(format);
	}
	
	/**
	 * 获取当前月的上一个月
	 * @param date
	 * @return
	 */
	public static String getPrevMonth(Date date){
		org.joda.time.DateTime datetime = new org.joda.time.DateTime(date);
		return datetime.minusMonths(1).toString("yyyy-MM-dd");
	}
	
	public static String getPrevMonth(String dateString){
		org.joda.time.DateTime datetime = new org.joda.time.DateTime(TmDateUtil.parse(dateString));
		return datetime.minusMonths(1).toString("yyyy-MM-dd");
	}
	
	public static String getPrevMonth(Date date,String format){
		org.joda.time.DateTime datetime = new org.joda.time.DateTime(date);
		return datetime.minusMonths(1).toString(format);
	}
	
	public static String getPrevMonth(String dateString,String format){
		org.joda.time.DateTime datetime = new org.joda.time.DateTime(TmDateUtil.parse(dateString));
		return datetime.minusMonths(1).toString(format);
	}
	
	public static int numYear(String currentDate,String date){
		DateTime dateTime = new DateTime(currentDate);
		DateTime edateTime = new DateTime(date);
		return edateTime.numDaysFrom(dateTime) / 365;
	}
	
	public static int numYear(Date currentDate,Date date){
		DateTime dateTime = new DateTime(TmDateUtil.getCurrentDate(currentDate));
		DateTime edateTime = new DateTime(TmDateUtil.getCurrentDate(date));
		return edateTime.numDaysFrom(dateTime) / 365;
	}
	
	public static String numMinutes(String currentDate,String date){
		DateTime dateTime = new DateTime(currentDate);
		DateTime edateTime = new DateTime(date);
		long timer = dateTime.numSecondsFrom(edateTime) / 60;
		if(timer<=0)return "-1";
		if(timer > 60){
			return  timer/60+"小时";
		}else{
			return timer+"分钟";
		}
	}

	//业务代码对您没有一点价值。
	public static void main(String[] args) throws ParseException {
		//两个日期相减(加)去
		//获取本周的第一天
		//本周，昨天，本月 本年 一年前 两年前
		//获取浸提的开始时间和结束时间
//		TmTimeInterval timeInterval = getDateTime(TmDateTimeUtil.TODAY);
//		System.out.println(TmDateUtil.getCurrentDatetime(timeInterval.getStartTime()));
//		System.out.println(TmDateUtil.getCurrentDatetime(timeInterval.getEndTime()));
		
		TmTimeInterval timeInterval = getDateTime(TmDateTimeUtil.THE_YEAR_BEFORE_LAST);
		System.out.println(TmDateUtil.getCurrentDatetime(timeInterval.getStartTime()));
		System.out.println(TmDateUtil.getCurrentDatetime(timeInterval.getEndTime()));
		System.out.println(TmDateUtil.getTimeFormat(timeInterval.getStartTime()));
		
		
//		TmTimeInterval timeInterval = getDateTime(TODAY,new Date());
//		System.out.println(TmDateUtil.getCurrentDatetime(timeInterval.getStartTime())+"==="+TmDateUtil.getCurrentDatetime(timeInterval.getEndTime()));
//		System.out.println(numMinutes("2013-12-24 06:30:00","2013-12-24 22:10:00"));
		//System.out.println(getYear("2012-11-12"));
		//System.out.println(getMonth("2012-11-12"));
		//System.out.println(numYear(new Date(),MyhomeDateUtil.parse("1986-02-27")));
		//org.joda.time.DateTime datetime = new org.joda.time.DateTime(TmDateUtil.parseDateTime("2013-11-15 10:44:52:123"));
		//System.out.println(datetime.getDayOfWeek());
		//System.out.println(getPrevMonth("2012-11-12"));
//		 MyhomeTimeInterval interval = getDateTime("today",MyhomeDateUtil.dateToString("2012-12-12 12:23:23"));
//		 System.out.println(interval.getStartTime()+"===>"+interval.getEndTime());
		 
		// System.out.println(interval.getStartTime().toLocaleString()+"===========>"+interval.getEndTime().toLocaleString());
		// System.out.println(getDayOfMonth(new Date()));
		// System.out.println(getPlusDays("2010-04-19"));
		// ExmayTimeInterval interval = getDateTime("yesterday");
		//		
		// System.out.println(new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(interval.getStartTime())+"===========>"+new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(interval.getEndTime()));
		// List<String> list = new
		// ExmayDateTimeUtil().getDateRange("2011-01-01", "2011-05-30");
		// for (String string : list) {
		// System.out.println(string);
		// }
		// ExmayTimeInterval interval = getDateTime(LAST_MONTH);
		// System.out.println(interval.getEndTime().toLocaleString()+"===>"+interval.getStartTime().toLocaleString());
		// System.out.println(getDayOfMonth(new
		// SimpleDateFormat("yyyy-MM-dd").format(interval.getStartTime())));
		// System.out.println();
		// System.out.println(getDayOfMonth("2011-03-12"));
		// System.out.println(getMonthDay());
		// Date nowTime = new SimpleDateFormat("yyyyMM").parse("201108");
//		 Date date = new Date();
//		 String dString = new SimpleDateFormat("yyyy-MM-dd").format(date);
//		 DateTime time = new DateTime(dString);
//		 time = time.plusDays(30);
//		 System.out.println(time.toString());
//		 DateTime time = new DateTime();
		//		
		// DateTime mDateTime = time.minusDays(30);
		// System.out.println(mDateTime.format("YYYY-MM-DD"));
		// String nowTime = ExmayDateUtil.toString(now, Format_DateTime);
		// DateTime time = new DateTime(nowTime);
		// System.out.println(time.getEndOfMonth().getDay());
		// ExmayTimeInterval interval = getDateTime(THE_YEAR_BEFORE_LAST);
		// System.out.println(ExmayDateUtil.toString(interval.getStartTime(),
		// ExmayDateUtil.Format_DateTime));
		// System.out.println(ExmayDateUtil.toString(interval.getEndTime(),ExmayDateUtil.Format_DateTime));
		// String time = "2010-11-19 20:22:59";
		// DateTime dt = new DateTime(time);
		// System.out.println(dt.getWeekIndex());
		// org.joda.time.DateTime dateTime =
		// DateTimeFormat.forPattern(Format_DateTime).parseDateTime(time);
		// System.out.println(dateTime.getDayOfWeek());
	}

	
}
