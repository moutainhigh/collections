package com.gwssi.ssoagent.model;

/**
 * @author chaihw
 *
 */
public class SSOUser {
	private String username;//�û���
	private String IpAddr;//��¼ip
	private String adDomain;//�û�ad��
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIpAddr() {
		return IpAddr;
	}
	public void setIpAddr(String ipAddr) {
		IpAddr = ipAddr;
	}
	public String getAdDomain() {
		return adDomain;
	}
	public void setAdDomain(String adDomain) {
		this.adDomain = adDomain;
	}
	@Override
	public String toString() {
		return "SSOUser [username=" + username + ", IpAddr=" + IpAddr
				+ ", adDomain=" + adDomain + "]";
	}
	
	
	

}
