package com.gwssi.application.common.query;

import java.util.Map;


/**
 * 唯一对外接口。
 * 
 * @author liuhailong2008@foxmail.com
 */
public class CommonQueryUtil {
	
	public static Map<String,Object> getConditionConfig(String configName){
		Map<String,Object> ret = QueryConfigUtil.getConfig(configName, "c");
		return ret;
	}
	
	public static Map<String,Object> getDetailConfig(String configName){
		Map<String,Object> ret = QueryConfigUtil.getConfig(configName, "d");
		return ret;
	}
	
	public static Map<String,Object> getListConfig(String configName){
		Map<String,Object> ret = QueryConfigUtil.getConfig(configName, "l");
		return ret;
	}
}
