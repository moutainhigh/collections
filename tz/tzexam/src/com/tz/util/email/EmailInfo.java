package com.tz.util.email;

import java.util.Properties;

public class EmailInfo {
	private String host;
	private String port = "25";
	private String sendAddress;// 邮件发送者地址
	private String receivAddress;// 邮件接收者地址
	/* 登录哟见发送服务器的用户名和密码 */
	private String userName;
	private String password;
	private boolean isValidate = true;

	private String subject;
	private String content;
	private String[] attachFileNames;

	private String[] rec;// 接收者
	private String[] cc;// 抄送者

	public Properties getProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", this.host);
		properties.put("mail.smtp.port", this.port);
		properties.put("mail.smtp.auth", this.isValidate ? "true" : "false");
		return properties;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getReceivAddress() {
		return receivAddress;
	}

	public void setReceivAddress(String receivAddress) {
		this.receivAddress = receivAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValidate() {
		return isValidate;
	}

	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] attachFileNames) {
		this.attachFileNames = attachFileNames;
	}

	public String[] getRec() {
		return rec;
	}

	public void setRec(String[] rec) {
		this.rec = rec;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}
}