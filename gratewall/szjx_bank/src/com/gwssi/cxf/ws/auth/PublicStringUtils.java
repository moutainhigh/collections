package com.gwssi.cxf.ws.auth;

public class PublicStringUtils {

	/**
	 * 空判断
	 * 方法名：isEmpty<BR>
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.length() == 0 || "".equals(str)
				|| str.matches("\\s*");
	}
	
	/**
	 * 非空判断
	 * 方法名：isNotEmpty<BR>
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

}
