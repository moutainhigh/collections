package com.ly.vo;

public class SMSReceverBean {
	private String isSend;
	private String name;
	private String phone;
	private String times;
	private String id;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SMSReceverBean() {
		super();
	}
	public SMSReceverBean(String phone, String isSend, String name, String times) {
		super();
		this.phone = phone;
		this.isSend = isSend;
		this.name = name;
		this.times = times;
	}
	public String getIsSend() {
		return isSend;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getTimes() {
		return times;
	}
	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	
	
}
