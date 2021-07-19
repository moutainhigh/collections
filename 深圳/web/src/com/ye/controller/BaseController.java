package com.ye.controller;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
	
	private Map<String,String> sysCode = new HashMap<String,String>();
	
	private Map<String,String> code = new HashMap<String,String>();
	
	
	
	public void init() {
		code.put("00000", "系统异常");
		code.put("00001", "连续中断");
		code.put("00003", "数据库异常");
		code.put("00004", "系统繁忙");
		
		
		
		code.put("10000", "业务处理成功");
		code.put("10000", "业务处理失败");
		
		
		
	}



	public Map<String, String> getSysCode() {
		return sysCode;
	}



	public void setSysCode(Map<String, String> sysCode) {
		this.sysCode = sysCode;
	}



	public Map<String, String> getCode() {
		return code;
	}



	public void setCode(Map<String, String> code) {
		this.code = code;
	}
	


}
