package com.tz.util.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 邮件身份验证器。
 * 
 * @author xuchengfei
 * @date 2013-11-21 下午12:04:39
 * @modifyNote
 * @version 1.0
 */
public class MailAuthTool extends Authenticator{
	private String userName;
	private String password;

	public MailAuthTool(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
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
}