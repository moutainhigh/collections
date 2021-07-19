package com.gwssi.ip.util;

public class StringUtil {

	
	
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0 || str.equals("")
				|| str.matches("\\s*");
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
}
