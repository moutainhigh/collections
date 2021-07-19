package com.gwssi.common.util;

public class ResourceBundleUtil
{
	private static final String PATH="app";
	
	public static String getValue(String key){
		return java.util.ResourceBundle.getBundle(PATH).getString(key);
	}
}
