package com.gwssi.common.rodimus.report.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class HttpUtil {
	
	/**
	 * 得到当前HTTP请求对象。
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		HttpServletRequest request = (HttpServletRequest) ThreadLocalManager.get(ThreadLocalManager.HTTP_REQUEST);
		return request;
	}
	
	/**
	 * 得到当前HTTP响应对象。
	 * 
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		HttpServletResponse response = (HttpServletResponse) ThreadLocalManager.get(ThreadLocalManager.HTTP_RESPONSE);
		return response;
	}
	
	/**
	 * 得到HTTP请求参数。
	 * 
	 * @param name
	 * @return
	 */
	public static String getParameter(String name){
		HttpServletRequest request = getRequest();
		if(request==null){
			return "";
		}
		String ret = request.getParameter(name);
		return ret ;
	}
	
	/**
	 * 得到HTTP请求参数Map。
	 * 
	 * @param name
	 * @return
	 */
	public static Map<String, String> getParameterMap(){
		HttpServletRequest request = getRequest();
		if(request==null){
			return new HashMap<String, String>();
		}
		Map<String, String[]> map = request.getParameterMap();
		Map<String, String> ret = new HashMap<String, String>();
		String key , value ;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = StringUtils.join(entry.getValue(),",");
			ret.put(key, value);
		}
		return ret ;
	}
	
}
