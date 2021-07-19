package com.gwssi.util;
/**
 * 
 * @author liuhailong
 */
public class ErrorUtil {
	/**
	 * 报错信息格式。
	 */
	protected final static String ERROR_RESPONSE_FORMAT = "{error:\"%s\"}";
	
	/**
	 * 格式化报错信息。
	 * 
	 * @param msg
	 * @return
	 */
	public static String getErrorResponse(String msg){
		String ret = String.format(ERROR_RESPONSE_FORMAT, msg);
		return ret;
	}
}
