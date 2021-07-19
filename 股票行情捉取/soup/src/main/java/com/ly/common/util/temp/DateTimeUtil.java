package com.ly.common.util.temp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=java%20%E8%8E%B7%E5%8F%96%E6%8C%87%E5%AE%9A%E6%97%B6%E9%97%B4%E5%91%A8%E4%B8%80%E5%92%8C%E5%91%A8%E4%BA%94%E6%97%B6%E9%97%B4&rsv_pq=9eef134700031fbf&rsv_t=6cd51b%2BMn1q2KYJISJJehADbwC9GD10oHdMaXrbVK1W5gXMLzNpVsdVQYkY&rqlang=cn&rsv_enter=1&rsv_sug3=65&rsv_sug1=3&rsv_sug7=100
//https://blog.csdn.net/samjustin1/article/details/81189151
//https://www.jianshu.com/p/179528007ecd
//https://www.cnblogs.com/mingtian521/p/3525870.html
public class DateTimeUtil {
	/**
     * 获取上周五时间
     */
    public Date lastFirday() {
    	//作用防止周日得到本周日期
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 7 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 9);

        return DateTimeUtil.getFirstDayOfWeek(calendar.getTime(), 6);//这是从上周日开始数的到本周五为6

    }

	/**
	 * 获取上周一时间
	 */
	public Date lastMonday() {
		Calendar calendar = Calendar.getInstance();
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DAY_OF_WEEK, -1);
		}
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int offset = 1 - dayOfWeek;
		calendar.add(Calendar.DATE, offset - 7);
		return DateTimeUtil.getFirstDayOfWeek(calendar.getTime(), 2);
	}

	/**
	 * 得到某一天的该星期的第一日 00:00:00
	 * 
	 * @param date
	 * @param firstDayOfWeek
	 *            一个星期的第一天为星期几
	 * 
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date, int firstDayOfWeek) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		cal.setFirstDayOfWeek(firstDayOfWeek);// 设置一星期的第一天是哪一天
		cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);// 指示一个星期中的某天
		cal.set(Calendar.HOUR_OF_DAY, 0);// 指示一天中的小时。HOUR_OF_DAY 用于 24
											// 小时制时钟。例如，在 10:04:15.250 PM
											// 这一时刻，HOUR_OF_DAY 为 22。
		cal.set(Calendar.MINUTE, 0);// 指示一小时中的分钟。例如，在 10:04:15.250 PM
									// 这一时刻，MINUTE 为 4。
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static void main(String[] args) {
		DateTimeUtil d = new DateTimeUtil();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(format.format(d.lastMonday()));;
	}
}
