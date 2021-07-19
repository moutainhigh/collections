package com.ly.vo;

public class EmailReceverBean {
	private String address;
	private String isSend;
	private String name;
	private String times;

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EmailReceverBean() {
		super();
	}

	public EmailReceverBean(String address, String name, String isSend, String times) {
		super();
		this.address = address;
		this.name = name;
		this.isSend = isSend;
		this.times = times;
	}

	public String getAddress() {
		return address;
	}

	public String getIsSend() {
		return isSend;
	}

	public String getName() {
		return name;
	}

	public String getTimes() {
		return times;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTimes(String times) {
		this.times = times;
	}

}
