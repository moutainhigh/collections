package com.gwssi.upload.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 日期类型判断
 * @author Administrator
 *
 */
 
public class DateRegexUtil {
	
	public static String dateType(String date) {
		String reges = "^\\d+$";
		if(date == null || date.length()<10 || date.matches("^-\\d+$")) {
			return null;
		} else {
			if(date.matches(reges)) {
				//日期格式为 1266540000
				long time = Long.parseLong(date);
		    	Date date2 = new Date(time);
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    	return sdf.format(date2);
			} else {
				//日期格式为 2017-10-24 00:00:00
				return date.substring(0, 10);
			}
		}
		
	}

}
