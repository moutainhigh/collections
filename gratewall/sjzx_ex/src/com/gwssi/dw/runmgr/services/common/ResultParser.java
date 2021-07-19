package com.gwssi.dw.runmgr.services.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultParser
{
	public synchronized static Map createResultMap(List resultMap, Map params, String total){
		Map result = new HashMap();
		if(resultMap.size() < 1){
			result.put("FHDM", Constants.SERVICE_FHDM_NO_RESULT);
		}else{
			result.put("FHDM", Constants.SERVICE_FHDM_SUCCESS);
		}
		result.put("KSJLS", params.get(Constants.SERVICE_OUT_PARAM_KSJLS));
		//result.put("JSJLS", new Integer(resultMap.size()));
		result.put("JSJLS", params.get(Constants.SERVICE_OUT_PARAM_JSJLS));
		
		result.put("ZTS", total);
		Map[] maps = new HashMap[resultMap.size()];
		for(int i = 0;i<resultMap.size();i++){
			Map map = (Map)resultMap.get(i);
			map.remove("RN");
			
			//System.out.println("timestamp =========== "+map.get("ETL_TIMESTAMP"));
			//转换java.sql.Timestamp为可读日期
			if(map.get("ETL_TIMESTAMP") != null){
//				System.out.println("timestamp class =========== "+map.get("ETL_TIMESTAMP").getClass().getName());
				map.put("ETL_TIMESTAMP", map.get("ETL_TIMESTAMP").toString());
			}
			maps[i] = map;
		}
		result.put("GSDJ_INFO_ARRAY", maps);
		
		return result;
	}
	
	public synchronized static Map createTestResultMap(List resultMap, String total){
		Map result = new HashMap();
		if(resultMap.size() < 1){
			result.put("FHDM", Constants.SERVICE_FHDM_NO_RESULT);
		}else{
			result.put("FHDM", Constants.SERVICE_FHDM_SUCCESS);
		}
		result.put("KSJLS", "1");
		result.put("JSJLS", "5");
		result.put("ZTS", total);
		Map[] maps = new HashMap[resultMap.size()];
		for(int i = 0;i<resultMap.size();i++){
			Map map = (Map)resultMap.get(i);
			map.remove("RN");
			
			//System.out.println("timestamp =========== "+map.get("ETL_TIMESTAMP"));
			//转换java.sql.Timestamp为可读日期
			if(map.get("ETL_TIMESTAMP") != null){
				map.put("ETL_TIMESTAMP", map.get("ETL_TIMESTAMP").toString());
			}
			maps[i] = map;
		}
		result.put("GSDJ_INFO_ARRAY", maps);
		return result;
	}

	public synchronized static Map createLoginFailMap(Map params){
		Map result = new HashMap();
		result.put(Constants.SERVICE_OUT_PARAM_FHDM, Constants.SERVICE_FHDM_LOGIN_FAIL);
		result.put(Constants.SERVICE_OUT_PARAM_KSJLS, params.get(Constants.SERVICE_OUT_PARAM_KSJLS));
		result.put(Constants.SERVICE_OUT_PARAM_JSJLS, params.get(Constants.SERVICE_OUT_PARAM_JSJLS));
		result.put(Constants.SERVICE_OUT_PARAM_ZTS, "0");
		result.put(Constants.SERVICE_OUT_PARAM_ARRAY, null);
		return result;
	}

	public synchronized static Map createParamErrorMap(Map params, String code){
		Map result = new HashMap();
		result.put("FHDM", code);
		result.put("KSJLS", params.get(Constants.SERVICE_OUT_PARAM_KSJLS));
		result.put("JSJLS", params.get(Constants.SERVICE_OUT_PARAM_JSJLS));
		result.put("ZTS", "0");
		result.put("GSDJ_INFO_ARRAY", null);
		
		return result;
	}
	
	public synchronized static Map createOverMaxMap(Map params){
		Map result = new HashMap();
		result.put("FHDM", Constants.SERVICE_FHDM_OVER_MAX);
		result.put("KSJLS", params.get(Constants.SERVICE_OUT_PARAM_KSJLS));
		result.put("JSJLS", params.get(Constants.SERVICE_OUT_PARAM_JSJLS));
		result.put("ZTS", "0");
		result.put("GSDJ_INFO_ARRAY", null);
		
		return result;
	}
	
	public synchronized static Map createUnknownFailMap(Map params){
		Map result = new HashMap();
		result.put("FHDM", Constants.SERVICE_FHDM_UNKNOWN_ERROR);
		result.put("KSJLS", params.get(Constants.SERVICE_OUT_PARAM_KSJLS));
		result.put("JSJLS", params.get(Constants.SERVICE_OUT_PARAM_JSJLS));
		result.put("ZTS", "0");
		result.put("GSDJ_INFO_ARRAY", null);
		
		return result;
	}
}
