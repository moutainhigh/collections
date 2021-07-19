package com.gwssi.rodimus.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
	/**
	 * Map<String,Object>转换为Map<String,String>
	 * 
	 * @param oMap
	 * @return
	 */
	public static Map<String, String> oMap2strMap(Map<String, Object> oMap){
		Map<String, String> strMap = new HashMap<String, String>();
		if(oMap==null||oMap.isEmpty()){
			return strMap;
		}
		for(Map.Entry<String, Object> entry:oMap.entrySet()){
			String key = entry.getKey();
			String value = StringUtil.safe2String(entry.getValue());
			strMap.put(key, value);
		}
		return strMap;
	}
	
}
