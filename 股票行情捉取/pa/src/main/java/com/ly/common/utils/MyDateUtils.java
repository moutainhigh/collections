package com.ly.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtils {

	public static String getTimeString(Date date,String formatStr) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String dateStr = sdf.format(date);
		
		return dateStr;
	}
}
