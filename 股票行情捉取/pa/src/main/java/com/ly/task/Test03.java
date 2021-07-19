package com.ly.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//https://zhidao.baidu.com/question/1495688445373778059.html
//https://www.cnblogs.com/yadongliang/p/9549349.html
//https://zhidao.baidu.com/question/127113902.html
//https://blog.csdn.net/hj7jay/article/details/75043447
//https://www.cnblogs.com/feiyuanxing/articles/4990542.html
public class Test03 {
	public static String getWorkDay(Date startDate, int workDay) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);
		for (int i = 0; i < workDay; i++) {
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
			if (Calendar.SATURDAY == c1.get(Calendar.SATURDAY) || Calendar.SUNDAY == c1.get(Calendar.SUNDAY)) {
				workDay = workDay + 1;
				c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
				continue;
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
//		System.out.println(df.format(c1.getTime()) + " " + getWeekOfDate(c1.getTime()));
		return df.format(c1.getTime());
	}

	/**
	 * 根据日期，获取星期几
	 * @param dt
	 * @return String类型
	 * @author 【狒狒：Q9715234】
	 * @motto 既然笨到家，就要努力到家...
	 */
	
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}
	public static void main(String[] args) {
		// Date date = AppUtil.getCurrentDate2();
		System.out.println("工作日： 3天之前 " + getWorkDay(new Date(), 3));
		System.out.println("工作日： 9天之前 " + getWorkDay(new Date(), 9));
		System.out.println("工作日： 10天之前 " + getWorkDay(new Date(), 10));
		System.out.println("工作日： 11天之前 " + getWorkDay(new Date(), 11));
		
		//String str = getWeekOfDate(new Date());
		//System.out.println(str);
		
	}
}
