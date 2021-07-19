package com.gwssi.application.common.query;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.application.common.FileUtil;

class QueryConfigUtil {

	private static String QUERY_CONFIG_PATH = QueryConfigUtil.class.getClassLoader().getResource("").getPath()
			+ "queryConfig"+File.separator  ;
	private static String QUERY_CONFIG_PATH_CONDITION =  QUERY_CONFIG_PATH + "query_%s_condition.json";
	private static String QUERY_CONFIG_PATH_LIST =  QUERY_CONFIG_PATH + "query_%s_list.json";
	private static String QUERY_CONFIG_PATH_DETAIL =  QUERY_CONFIG_PATH + "query_%s_detail.json";
	
	private static Map<String,JSONObject> CONFIG_CACHE = new HashMap<String,JSONObject>();
	
	
	public static JSONObject getConfig(String configName,String type){
		JSONObject ret = null;
		//JSONObject ret = CONFIG_CACHE.get(configName+type);
		//if(ret==null){
			String configPath = "";
			if("c".equals(type)){//查询条件
				configPath = String.format(QUERY_CONFIG_PATH_CONDITION, configName);
			}if("d".equals(type)){//明细
				configPath = String.format(QUERY_CONFIG_PATH_DETAIL, configName);
			}if("l".equals(type)){//列表
				configPath = String.format(QUERY_CONFIG_PATH_LIST, configName);
			}
			System.out.println("load config from " + configPath);
			
			String jsonString = FileUtil.getFileText(new File(configPath));
			JSONObject jo = JSON.parseObject(jsonString);
			ret = jo;
			if(ret!=null){
				CONFIG_CACHE.put(configName, ret);
			}
		//}
		return ret;
	}
}
