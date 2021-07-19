package com.gwssi.cxf.feedback.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ParamsCheck {
	
	public static Map<String, String> checkResult(String updateStartTime, String updateEndTime) {
		Map<String, String> params = null;
		if (!StringUtils.isEmpty(updateStartTime) || !StringUtils.isEmpty(updateEndTime)) {
			params = new HashMap<String, String>();
			
			if (!StringUtils.isEmpty(updateStartTime)) {
				params.put("updateStartTime", updateStartTime);
			} else {
				params.put("updateStartTime", null);
			}
			
			if (!StringUtils.isEmpty(updateEndTime)) {
				params.put("updateEndTime", updateEndTime);
			} else {
				params.put("updateEndTime", null);
			}
			
			/*if (!StringUtils.isEmpty(updateTime)) {
				params.put("updateTime", updateTime);
			} else {
				params.put("updateTime", null);
			}*/
			
		}
		return params;
	}

}
