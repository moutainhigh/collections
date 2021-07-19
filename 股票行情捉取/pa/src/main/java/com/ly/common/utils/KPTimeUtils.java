package com.ly.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ly.pojo.Holiday;

public class KPTimeUtils {
	private static Logger logger = Logger.getLogger(KPTimeUtils.class);
	
	
	public static boolean getIsHoliday(Holiday h) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		logger.debug("当前日期=============>" + df.format(new Date()));
		//Holiday h = holidayDao.getIsHoliday();
		if (h != null) {
			logger.debug("当前节日" + h.getHolidayName() + ",系统不监控 ");
			return false;
		} else {
			return true;
		}
	}
	
	/// 公用的休市表
	public static boolean getIsBegin() {
		boolean flag = false;

		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY); // http://blog.csdn.net/jiangeeq/article/details/53103069
		int minute = c.get(Calendar.MINUTE);
		flag = hour == 9 && minute <= 26 || hour== 11 && minute >= 30 ||   hour == 14 && minute >= 57; //在这些时间段时，才为真
		return flag;
	}
	
	
	
}
