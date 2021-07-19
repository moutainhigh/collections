package com.ly.common.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CalcUtil {
	//进行四舍五入
	public static String formatDouble(String value) {
		DecimalFormat df = new DecimalFormat("#0.00");
		String st = df.format(Double.valueOf(value));
		return st;
	}
	//进行四舍五入
	public static String formatDouble(double value) {
		DecimalFormat df = new DecimalFormat("#0.00");
		String st = df.format(value);
		return st;
	}
	
	
	//不进行四舍五入
	//https://blog.csdn.net/juhua2012/article/details/54091111
	//https://www.cnblogs.com/dichuan/p/7769098.html
	public static String formateDouleToStringFloor(Double d){
		 DecimalFormat df = new DecimalFormat("#.00");
		 df.setRoundingMode(RoundingMode.FLOOR);
		// logger.debug("Double转String之后的内容 " +str);
	     String str =  df.format(d);
		 return str;
	}
}
