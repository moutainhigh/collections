package com.gwssi.rodimus.util;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author liuhailong
 */
public class JSonUtil {
	/**
	 * 
	 * @param o
	 * @param isFormat
	 * @return
	 */
	public static String toJSONString(Object o, boolean isFormat){
		String ret = JSON.toJSONString(null, isFormat);
		return ret;
	}
	/**
	 * 转对象为JSON字符串。
	 * @param o
	 * @return
	 */
	public static String toJSONString(Object o){
		String ret = toJSONString(null, false);
		return ret;
	}
}
