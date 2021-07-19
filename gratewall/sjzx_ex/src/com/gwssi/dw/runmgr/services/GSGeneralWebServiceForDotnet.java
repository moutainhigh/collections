package com.gwssi.dw.runmgr.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GSGeneralWebServiceForDotnet 
{
	public CommonResult query(CommonMapObject params){
		Map paramMap = convertCommonMapObjectToMap(params);
		if(paramMap==null){
			return new CommonResult();
		}
		GSGeneralWebService service = new GSGeneralWebService();
		Map resultMap = service.query(paramMap);
		CommonResult result = convertResultMapToCommonResult(resultMap);
		return result;
		
	}
	protected CommonResult convertResultMapToCommonResult(Map resultMap){
		CommonResult result = new CommonResult();
		result.setFHDM((String)resultMap.get("FHDM"));
		result.setKSJLS((String)resultMap.get("KSJLS"));
		result.setJSJLS((String)resultMap.get("JSJLS"));
		String zts = (String)resultMap.get("ZTS");
		result.setZTS(zts);
		int total = Integer.parseInt(zts);
		if(total==0){
			return result;
		}

		HashMap[] rs = (HashMap[])resultMap.get("GSDJ_INFO_ARRAY");
		if(rs==null){
			return result;
		}
		System.out.println("total="+rs.length);
		CommonMapObject[] commonObject = new CommonMapObject[rs.length];
		for(int i=0;i<rs.length;i++){
			System.out.println("111");
			commonObject[i] = convertMapToArray(rs[i]);
		}	
		System.out.println("222");
		result.setCommonObject(commonObject);
		System.out.println(result.getCommonObject().length);
		/*
		if(total>1){
			System.out.println(rs.length);
			
			CommonMapObject[][] mutiple = new CommonMapObject[rs.length][];
			for(int i=0;i<rs.length;i++){
				System.out.println("adddsf");
				mutiple[i] = convertMapToArray(rs[i]);
			}
			result.setMultipleObject(mutiple);			
		}else{
			CommonMapObject[] single = convertMapToArray(rs[0]);
			result.setSingleObject(single);
		}*/
		return result;
	}
	
	protected CommonMapObject convertMapToArray(Map result){
		CommonMapObject common = new CommonMapObject(result.size());
		Iterator keyIte = result.keySet().iterator();
        int i=0;
		while(keyIte.hasNext()){
			String key = (String)keyIte.next();
			String value = String.valueOf(result.get(key));
			common.getKey()[i] = key;
			common.getValue()[i] = value;
			i++;
		}
		return common;
	}
	
	protected Map convertCommonMapObjectToMap(CommonMapObject params){
		if(params==null){
			return null;
		}
		String[] key = params.getKey();
		String[] value = params.getValue();
		if(key==null||key.length==0){
			return null;
		}
		Map m = new HashMap();
		for(int i =0;i<key.length;i++){
			m.put(key[i], value[i]);
		}
		return m;
	}
}
