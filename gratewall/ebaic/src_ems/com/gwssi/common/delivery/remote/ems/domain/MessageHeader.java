package com.gwssi.common.delivery.remote.ems.domain;

import java.util.HashMap;
import java.util.Map;

import com.gwssi.optimus.util.JSON;

public class MessageHeader {
	
	public String toJSonString(){
		String retString = JSON.toJSON(this.dataMap);
		return retString;
	}
	
	protected Map<String, String> dataMap = new HashMap<String, String>();
	
}
