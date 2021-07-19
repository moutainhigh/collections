package com.ly.common.dateutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//https://blog.csdn.net/haohaounique/article/details/81239559
public class DataTransfer {
	
	public static void main(String[] args) {
		String str=DataTransfer.dateConvertion("20190429","yyyy-MM-dd");
		System.out.println(str);
	}
	
	
	/**
	 * 日期转换，将接口返回的20180524转为自定义需要的格式
	 * @param str
	 * @param formatType
	 * @return
	 */
	public static String dateConvertion(String str,String formatType) {
		Date parse = null;
		String dateString = "";
		try {
			parse = new SimpleDateFormat("yyyyMMdd").parse(str);
			dateString = new SimpleDateFormat(formatType).format(parse);
		} catch (ParseException e) {
			dateString = null;
		}

		return dateString;
	}
	
	
	
	
	
	
	
	/**
	 * @Description:日期转换，将接口返回的20180524转为2018-05-24
	 * @Date 2018年5月24日
	 * @param str
	 *            传递的日期字符串
	 * @return
	 * @exception :异常返回null,保障数据库的数据一致性,数据库格式yyyyMMdd
	 */
	public static String dateConvertion(String str) {
		Date parse = null;
		String dateString = "";
		try {
			parse = new SimpleDateFormat("yyyyMMdd").parse(str);
			dateString = new SimpleDateFormat("yyyy-MM-dd").format(parse);
		} catch (ParseException e) {
			dateString = null;
		}

		return dateString;
	}

	/**
	 * @Description:日期转换，将前台的2018-05-24转为20180524
	 * @Date 2018年5月24日
	 * @param str
	 *            传递的日期字符串
	 * @return
	 * @exception :异常返回null,保障数据库的数据一致性,数据库格式yyyyMMdd
	 */
	public  static String StringToDate(String str) {
		Date parse = null;
		String dateString = "";
		try {
			parse = new SimpleDateFormat("yyyy-MM-dd").parse(str);
			dateString = new SimpleDateFormat("yyyyMMdd").format(parse);
		} catch (ParseException e) {
			dateString = null;
		}
		return dateString;
	}
	
}
