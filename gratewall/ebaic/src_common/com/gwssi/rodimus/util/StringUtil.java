package com.gwssi.rodimus.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gwssi.rodimus.exception.RodimusException;

/**
 * 
 * @author liuhailong
 */
public class StringUtil {

	
	protected final static Logger logger = Logger.getLogger(StringUtil.class);
	/**
	 * obj转字符串，为null时返回“”
	 * 
	 * @param obj
	 * @return
	 */
	public static String safe2String(Object obj) {
		if(obj instanceof BigDecimal){
			BigDecimal bd = (BigDecimal)obj;
			obj = bd.doubleValue() + "";
			String ret = (obj == null) ? "" : obj.toString();
			if(ret.endsWith(".0")){
				ret = ret.substring(0, ret.length()-2);
			}
			return ret;
		}
		String ret = (obj == null) ? "" : obj.toString();
		return ret;
	}
	
	/**
	 * 把用seperator分割的字符串解析成字符串集合。
	 * 
	 */
	public static List<String> str2List(String data, String seperator) {
		if (StringUtils.isEmpty(data)) {
			return null;
		}
		if(StringUtils.isEmpty(seperator)){
			seperator = ",";
		}
		String[] arr = data.split(seperator);
		List<String> retList = new ArrayList<String>();
		if(arr!=null && arr.length>0){
			for(String string : arr){
				if(!StringUtil.isBlank(string)){
					string = string.trim();
					retList.add(string);
				}
			}
		}
		return retList;
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isBlank(String string) {
		return StringUtils.isBlank(string);
	}
	/**
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNotBlank(String string) {
		return StringUtils.isNotBlank(string);
	}
	
	public static final String EMPTY_STRING = "";
	
	
	public static int str2Int(String string) {
		try{
			int ret = Integer.parseInt(string);
			return ret;
		}catch(Throwable e){
			return Integer.MIN_VALUE;
		}
	}
	
	public static int str2Int(Object o,int defaultValue) {
		String s = safe2String(o);
		return str2Int(s,defaultValue);
	}
	
	public static int str2Int(String string,int defaultValue) {
		try{
			int ret = Integer.parseInt(string);
			return ret;
		}catch(Throwable e){
			return defaultValue;
		}
	}
	
	public static BigDecimal str2BigDecimal(String string,BigDecimal defaultValue) {
		try{
			BigDecimal ret = BigDecimal.valueOf(Double.parseDouble(string));
			return ret;
		}catch(Throwable e){
			return defaultValue;
		}
	}
	
	/**
	 * @param str
	 * @param cnt
	 * @return
	 */
	public static String repeat(String str, int cnt) {
		if(cnt<1){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i<cnt ; ++ i){
			sb.append(str);
		}
		return sb.toString();
	}
	
	
	public static String padLeft(String str, int size) {
		String ret = StringUtils.leftPad(str, size,'0');
		return ret;
	}
	public static String padLeft(int num, int size) {
		String ret = num + "";
		ret = padLeft(ret,size);
		return ret;
	}
	
	public static boolean isNumeric(String str){
		return StringUtils.isNumeric(str);
	}
	/**
	 * 将 18600107299处理为186******99。
	 * 
	 * @param mobileInDb
	 * @return
	 */
	public static String maskMobile(String mobile) {
		if(StringUtil.isBlank(mobile)){
			return "";
		}
		if(mobile.length()<11){
			throw new RodimusException("移动电话格式不正确。");
		}
		String ret = mobile.substring(0,3) + "******" + mobile.substring(9,11);
		return ret;
	}
	/**
	 * 
	 * @param arg
	 */
	public static void main(String[] arg){
		System.out.println(maskMobile("18600107299"));
	}
}

