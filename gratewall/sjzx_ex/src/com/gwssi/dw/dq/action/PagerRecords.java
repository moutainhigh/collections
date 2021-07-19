package com.gwssi.dw.dq.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class PagerRecords {
	private int totalCount;
	
	private String recordsJson;
	
	public  PagerRecords(int totalCount,Collection records){
		this.totalCount = totalCount;
		Map map = new HashMap();
		map.put("totalCount", ""+totalCount);
		map.put("records", records);
		this.recordsJson = JSONObject.fromObject(map).toString();//"{totalCount:10,records:[{userId:'1',userName:'yy'},{userId:'2',userName:'zz'}]}";
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the recordsJson
	 */
	public String getRecordsJson() {
		return recordsJson;
	}

	/**
	 * @param recordsJson the recordsJson to set
	 */
	public void setRecordsJson(String recordsJson) {
		this.recordsJson = recordsJson;
	}
	
	
}
