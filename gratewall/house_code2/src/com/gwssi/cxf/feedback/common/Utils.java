package com.gwssi.cxf.feedback.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Utils {
	
	/*
	 * 日期转换
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map> typechage(List<Map> list){
		List<Map> changtype =new ArrayList<Map>();
		for(Map<String,Object> map1:list){
			
			Map<String,Object> newMap= new HashMap<String,Object>();
			for(String s :map1.keySet()){
				Object obj=map1.get(s);
				
				if (obj!=null&&(obj.getClass()==GregorianCalendar.class)){
					GregorianCalendar gcal =(GregorianCalendar)obj;
					String format = "yyyy-MM-dd";
					SimpleDateFormat formatter = new SimpleDateFormat(format);
					newMap.put(s,  formatter.format(gcal.getTime()).toString());
				}else{
					newMap.put(s, map1.get(s));
				}
			}
			changtype.add(newMap);
		}
		
		return  changtype;
	}
	
	/**
	 * 校验日期格式
	 * @param arg
	 * @return
	 */
	public static String checkTimeFormat(String arg) {
		String regex = "^\\d{4}-\\d{2}-\\d{2}$";  //匹配日期格式2017-12-01
		if (StringUtils.isNotEmpty(arg)) {
			arg = arg.trim();
			if (arg.length() == 10 && arg.matches(regex)) {
				return arg;
			}
		}
		return null;
	}
		
}

