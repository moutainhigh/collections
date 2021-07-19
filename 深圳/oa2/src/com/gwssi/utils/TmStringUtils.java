package com.gwssi.utils;

public class TmStringUtils {

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0 || str.equals("") || str.matches("\\s*");
	}

}