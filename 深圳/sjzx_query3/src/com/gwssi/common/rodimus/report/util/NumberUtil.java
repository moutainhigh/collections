package com.gwssi.common.rodimus.report.util;

import java.math.BigDecimal;

/**
 * 数字转换工具类。
 * 
 * @author liuhailong
 */
public class NumberUtil {
	
	/**
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static long obj2Long(Object o,long defaultValue){
		long ret = defaultValue;
		String str = StringUtil.safe2String(o);
		try{
			ret = Long.parseLong(str);
		}catch(Throwable e){
			ret = defaultValue;
		}
		return ret;
	}
	
	/**
	 * @param o
	 * @return
	 */
	public static BigDecimal parseBigDecimal(Object o){
		if(o==null){
			return BigDecimal.ZERO;
		}
		if(o instanceof BigDecimal){
			return (BigDecimal)o;
		}
		String s = o.toString();
		BigDecimal ret = null;
		try{
			ret = new BigDecimal(s);
		}catch(Throwable e){
			ret = BigDecimal.ZERO;
		}
		return ret;
	}
}
