package com.gwssi.common.delivery.remote.ems.domain;

import com.alibaba.fastjson.JSONObject;

public class MessageHeaderResponse extends MessageHeader {

	public static final String RET_CODE = "RET_CODE"; 
	public static final String RET_MSG = "RET_MSG"; 
	
	public MessageHeaderResponse(){}
	public MessageHeaderResponse(JSONObject joHead) {
		if(joHead!=null){
			String retCodeString = joHead.getString(RET_CODE);
			String retMsg  = joHead.getString(RET_MSG);
			
			MessageRetCode retCode = MessageRetCode.get(retCodeString);
			this.setRET_CODE(retCode);
			this.setRET_MSG(retMsg);
		}
	}
	
	public MessageRetCode getRET_CODE() {
		String retCode = super.dataMap.get(RET_CODE);
		return MessageRetCode.get(retCode);
	}
	public String getRetCode() {
		String retCode = super.dataMap.get(RET_CODE);
		return retCode;
	}
	public void setRET_CODE(MessageRetCode retCode) {
		super.dataMap.put(RET_CODE, retCode.getCode());
	}
	
	public String getRET_MSG() {
		return super.dataMap.get(RET_MSG);
	}
	public void setRET_MSG(String retMsg) {
		super.dataMap.put(RET_MSG, retMsg);
	}
}
