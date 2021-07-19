package com.gwssi.common.delivery.remote.ems.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MessageBody {
	
	public MessageBody(){
		this.dataSets = new HashMap<String,List<Map<String, String>>>(); 
	}
	@SuppressWarnings("unchecked")
	public MessageBody(JSONObject jo){
		this();
		if(jo!=null){
			for(String key : jo.keySet()){
				JSONArray arr = jo.getJSONArray(key);
				Object[] arrObjects = arr.toArray();
				List<Map<String, String>> valueList = new ArrayList<Map<String, String>>();
				for(Object object : arrObjects){
					valueList.add((Map<String, String>)object);
				}
				this.dataSets.put(key, valueList);
			}
		}
	}
	
	protected Map<String,List<Map<String, String>>> dataSets = null; 

	public void putDataSet(String dataSetName,List<Map<String, String>> dataSetContent){
		this.dataSets.put(dataSetName, dataSetContent);
	}
	
	public List<Map<String, String>> getDataSet(String dataSetName){
		List<Map<String, String>> retList = this.dataSets.get(dataSetName);
		return retList;
	}
	/**
	 * 
	 * @return
	 */
	public List<Map<String, String>> getDefaultDataSet(){
		List<Map<String, String>> retDs = 
				this.dataSets.values().iterator().next();
		return retDs;
	}
	

	public String toJSonString(){
		String ret = JSON.toJSONString(dataSets, false);
		return ret;
	}
}
