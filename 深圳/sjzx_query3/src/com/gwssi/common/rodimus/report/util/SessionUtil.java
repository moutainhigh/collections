package com.gwssi.common.rodimus.report.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class SessionUtil {
	
	public static HttpSession getSession(){
		HttpSession session = (HttpSession) ThreadLocalManager.get(ThreadLocalManager.HTTP_SESSION);
		return session;
	}
	
	public static Object get(String key){
		HttpSession session = getSession();
		if(session==null){
			return null;
		}
		Object value = session.getAttribute(key);
		return value;
	}
	
	public static Object getString(String key){
		Object obj = get(key);
		String ret = StringUtil.safe2String(obj);
		return ret;
	}
	
	public static void set(String key,Object val){
		HttpSession session = getSession();
		if(session==null){
			throw new RuntimeException("获取不到Session对象，请联系系统管理员。或稍后重试。");
		}
		session.setAttribute(key, val);
	}
	
	public static void remove(String key){
		HttpSession session = getSession();
		if(session==null){
			return ;
		}
		session.removeAttribute(key);
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getAttributeMap(){
		HttpSession session = getSession();
		if(session==null){
			return new HashMap<String, Object>();
		}
		Map<String, Object> retMap = new HashMap<String, Object>();
		Enumeration enums =session.getAttributeNames();
		String attrName ;Object attrValue;
		while(enums.hasMoreElements()){
			attrName = StringUtil.safe2String(enums.nextElement());
			attrValue = session.getAttribute(attrName);
			retMap.put(attrName, attrValue);
		}
		return retMap;
	}
}
