package com.gwssi.common.rodimus.report.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
	
	static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/***** Pre Month   ****/
	public static String firstDayOfMonth(Calendar calendar) {
		if(calendar==null){
			calendar = Calendar.getInstance();
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		String ret = format(calendar);
		return ret;
	}
	public static String firstDayOfMonth() {
		return firstDayOfMonth(null);
	}
	public static String firstDayOfPreMonth(Calendar calendar) {
		if(calendar==null){
			calendar = Calendar.getInstance();
		}
		calendar.add(Calendar.MONTH, -1);
		String ret = firstDayOfMonth(calendar);
		return ret;
	}
	public static String firstDayOfPreMonth() {
		return firstDayOfPreMonth(null);
	}
	/***** Last Month   ****/
	public static String lastDayOfMonth(Calendar calendar) {
		if(calendar==null){
			calendar = Calendar.getInstance();
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String ret = format(calendar);
		return ret;
	}
	public static String lastDayOfMonth() {
		return lastDayOfMonth(null);
	}
	public static String lastDayOfPreMonth(Calendar calendar) {
		if(calendar==null){
			calendar = Calendar.getInstance();
		}
		calendar.add(Calendar.MONTH, -1);
		String ret = lastDayOfMonth(calendar);
		return ret;
	}
	public static String lastDayOfPreMonth() {
		return lastDayOfPreMonth(null);
	}
	
	/***** Tool   ****/
	public static String format(Calendar calendar){
		Date date = calendar.getTime();
		String ret = sdf.format(date);
		return ret;
	}
	
	public static Date parse(String paramValueString) {
		try {
			Date ret = sdf.parse(paramValueString);
			return ret;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static java.sql.Date parseSqlDate(String paramValueString){
		java.sql.Date ret = java.sql.Date.valueOf(paramValueString);
		return ret;
	}
}
