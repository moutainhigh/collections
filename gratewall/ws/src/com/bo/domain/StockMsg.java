package com.bo.domain;

public class StockMsg implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String phoneId;
	private String phone;
	private String name;
	private Integer hasSender;
	private Integer ispermitted;

	public String getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getHasSender() {
		return hasSender;
	}

	public void setHasSender(Integer hasSender) {
		this.hasSender = hasSender;
	}

	public Integer getIspermitted() {
		return ispermitted;
	}

	public void setIspermitted(Integer ispermitted) {
		this.ispermitted = ispermitted;
	}

}
