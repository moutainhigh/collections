package com.gwssi.trs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.application.common.FileUtil;

/**
 * 
 * @author yangzihao
 */
public class JsonUtil{
	
	private static String CONFIG_ROOT_PATH = JsonUtil.class.getClassLoader().getResource("").getPath()
			+ "queryConfig"+File.separator +"%s.json" ;
	
	private static Map<String,JSONObject> CONFIG_CACHE = new HashMap<String,JSONObject>();
	
	public static Map<String,Object> Json = new HashMap<String,Object>();
	
	private static JSONObject getConfig(String configName){
		JSONObject ret = CONFIG_CACHE.get(configName);
		if(ret==null){
			String configPath = String.format(CONFIG_ROOT_PATH, configName);
			System.out.println("load config from " + configPath);
			String jsonString = FileUtil.getFileText(new File(configPath));
			JSONObject jo = JSON.parseObject(jsonString);
			ret = jo;
			if(ret!=null){
				CONFIG_CACHE.put(configName, ret);
			}
		}
		return ret;
	}
	
	public static void getJson(String configName){
		JSONObject config = getConfig(configName);
		if(config==null){
			throw new RuntimeException("No config found.");
		}
		
		JSONArray jsonData = config.getJSONArray("data");
		if(jsonData==null){
			throw new RuntimeException("No table config found.");
		}
		
		for(int i=0;i<jsonData.size();i++){
			JSONObject jsonObjectSingle = jsonData.getJSONObject(i);
			String code = jsonObjectSingle.getString("code");
			HashMap<String, Object> hashMap = new HashMap<String,Object>();
			//hashMap.put("code",code);
			
			String sql = jsonObjectSingle.getString("sql");
			hashMap.put("sql",sql);
			
			//String columns = jsonObjectSingle.getString("column");
			/*ArrayList columnList = new ArrayList();
			for(int y=0;y<columns.size();y++){
				columnList.add(columns.getString(y));
			}*/
			//hashMap.put("columns", columns);
			
			Json.put(code,hashMap);
		}
		
		System.err.println(Json.toString());
	}
	
	public static void main(String[] agrs){
		getJson("Bg");
	}
}