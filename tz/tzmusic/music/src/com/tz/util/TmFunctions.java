package com.tz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tz.util.ip.TmIpUtil;


/**
 * 
 * JSP函数自定义标签
 * TmFunctions<BR>
 * 创建人:潭州学院-keke <BR>
 * 时间：2014年11月11日-下午9:21:37 <BR>
 * @version 1.0.0
 *
 */
public class TmFunctions {
	
	/**
	 * 日期具体的几秒钟以前
	 * 方法名：dateString<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月11日-下午10:34:33 <BR>
	 * @param startTime
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String dateToString(String startTime){
		return TmDateUtil.getTimeFormat(startTime);
	}
	
	/**
	 *  日期具体的几秒钟以前
	 * 方法名：dateToString2<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月11日-下午10:38:06 <BR>
	 * @param startTime
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String dateToString2(Date startTime){
		return TmDateUtil.getTimeFormat(startTime);
	}

	
	/**
	 * 通过ip地址获取真实地址
	 * 方法名：ipAddress
	 * 创建人：xuchengfei 时间：2015年3月25日-上午1:27:37 
	 * @param ip
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String ipAddress(String ip){
		return TmIpUtil.ipLocation(ip);
	}
	
	/**
	 * 格式化日期的自定义函数
	 * 方法名：dateFormat<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月11日-下午9:57:18 <BR>
	 * @param dateString
	 * @param format
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 * System.out.println(dateFormat("2014-12-14 12:12:12", "yyyy"));年份
		System.out.println(dateFormat("2014-12-14 12:12:12", "MM"));月份
		System.out.println(dateFormat("2014-12-14 12:12:12", "dd"));天
		System.out.println(dateFormat("2014-12-14 12:12:12", "HH:mm:ss"));24小时制
		System.out.println(dateFormat("2014-12-14 12:12:12", "hh:mm:ss"));12小时制
		System.out.println(dateFormat("2014-12-14 12:12:12", "yyyy-MM-dd"));年月日
		System.out.println(dateFormat("2014-12-14 12:12:12", "yyyy-MM-dd HH:mm"));年月日 时分
		System.out.println(dateFormat("2014-12-14 12:12:12", "yyyy-MM-dd HH:mm:ss"));年月日 时分秒
	 */
	public static String dateFormat(String dateString,String format){
		if(TmStringUtils.isEmpty(dateString))return "";
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
			String ds =  new SimpleDateFormat(format).format(date);
			return ds;
		} catch (ParseException e) {
			return "";
		}
	}
	
	/**
	 * 
	 * 方法名：formateDate<BR>
	 * 创建人：潭州学院-keke <BR>
	 * 时间：2014年11月23日-上午2:29:40 <BR>
	 * @param date
	 * @param format
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String formateDate(Date date,String format){
		if(date==null)return "";
		String ds =  new SimpleDateFormat(format).format(date);
		return ds;
	}
	
	/**
	 * 根据视频时长获取分：秒
	 * 方法名：mpstime
	 * 创建人：xuchengfei 时间：2015年3月27日-上午1:47:50 
	 * @param timeline
	 * @return String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String mpstime(int timeline){
		int minute = timeline / 60;
		int second = timeline % 60;
		return (minute<10?"0"+minute:minute+"")+":"+(second<10?"0"+second:second+"");
	}
}
