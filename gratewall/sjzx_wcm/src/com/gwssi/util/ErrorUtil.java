package com.gwssi.util;
/**
 * 
 * @author liuhailong
 */
public class ErrorUtil {
	/**
	 * ������Ϣ��ʽ��
	 */
	protected final static String ERROR_RESPONSE_FORMAT = "{error:\"%s\"}";
	
	/**
	 * ��ʽ��������Ϣ��
	 * 
	 * @param msg
	 * @return
	 */
	public static String getErrorResponse(String msg){
		String ret = String.format(ERROR_RESPONSE_FORMAT, msg);
		return ret;
	}
}
