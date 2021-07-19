package com.gwssi.common.rodimus.detail.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class RequestUtil {
	
	public static Map<String, Object> getRequestParamsAsMap(HttpServletRequest request) {
		Map<String, String[]> map = request.getParameterMap();
		Map<String, Object> ret = new HashMap<String, Object>();
		String key , value ;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			key = entry.getKey();
			value = StringUtils.join(entry.getValue(),",");
			ret.put(key, value);
		}
		return ret ;
	}
	
}
