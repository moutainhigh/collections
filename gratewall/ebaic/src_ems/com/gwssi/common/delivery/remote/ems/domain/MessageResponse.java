package com.gwssi.common.delivery.remote.ems.domain;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MessageResponse extends AbstractMessage{
	
	public MessageResponse(){
		this.HEAD = new MessageHeaderResponse();
	}
	public MessageResponse(String json) {
		JSONObject jo = JSON.parseObject(json);
		
		JSONObject joHead = jo.getJSONObject("HEAD");
		this.HEAD = new MessageHeaderResponse(joHead);
		
		JSONObject joBody = jo.getJSONObject("BODY");
		super.BODY = new MessageBody(joBody);
	}
	
		
	public MessageHeader getHEAD(){
		return this.HEAD;
	}
	
	public MessageHeaderResponse getResponseHeader(){
		return this.HEAD;
	}
	
	protected MessageHeaderResponse HEAD = null;
	
	
	public MessageRetCode getRetCode() {
		return getResponseHeader().getRET_CODE();
	} 
	public String getRetCodeString() {
		return getResponseHeader().getRetCode();
	} 
	public String getRET_MSG() {
		return getResponseHeader().getRET_MSG();
	} 
	public List<Map<String, String>> getDefaultDataSet(){
		return getBODY().getDefaultDataSet();
	}
	public List<Map<String,String>>getDataSet(String dataSetName){
		return getBODY().getDataSet(dataSetName);
	}
}
