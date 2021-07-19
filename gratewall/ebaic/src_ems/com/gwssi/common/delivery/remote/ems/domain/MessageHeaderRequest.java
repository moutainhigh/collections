package com.gwssi.common.delivery.remote.ems.domain;

public class MessageHeaderRequest extends MessageHeader {
	
	public static final String SYS_CODE = "SYS_CODE"; //接入系统编码
	public static final String USERNAME = "USERNAME"; //认证用户名
	public static final String PASSWORD = "PASSWORD"; //认证密码
	public static final String FUNC_CODE = "FUNC_CODE"; //功能代码
	public static final String VERSION = "VERSION"; //版本号
	
	public String getSYS_CODE() {
		return super.dataMap.get(SYS_CODE);
	}
	public void setSYS_CODE(String sysCode) {
		super.dataMap.put(SYS_CODE, sysCode);
	}
	public String getUSERNAME() {
		return super.dataMap.get(USERNAME);
	}
	public void setUSERNAME(String username) {
		super.dataMap.put(USERNAME, username);
	}
	public String getPASSWORD() {
		return super.dataMap.get(PASSWORD);
	}
	public void setPASSWORD(String password) {
		super.dataMap.put(PASSWORD, password);
	}
	public String getFUNC_CODE() {
		return super.dataMap.get(FUNC_CODE);
	}
	public void setFUNC_CODE(String funcCode) {
		super.dataMap.put(FUNC_CODE, funcCode);
	}
	public String getVERSION() {
		return super.dataMap.get(VERSION);
	}
	public void setVERSION(String version) {
		super.dataMap.put(VERSION, version);
	}
}
