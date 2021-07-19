package com.sender.mail.ftl.model;

public class UserMailModel {

	private String userName;
	private String loginName;
	private String emailAddress;
	private String ipAddress;
	private String ipArea;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getIpArea() {
		return ipArea;
	}

	public void setIpArea(String ipArea) {
		this.ipArea = ipArea;
	}

}
