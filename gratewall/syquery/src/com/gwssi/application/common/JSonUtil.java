package com.gwssi.application.common;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author 刘海龙
 */
public class JSonUtil {
	public static String toString(Object o){
		String ret = JSON.toJSONString(o, true);
		return ret;
	}
	
}
