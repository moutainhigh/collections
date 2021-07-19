package com.gwssi.rodimus.indentity.domain;

/**
 * 调用公安部接口返回的身份证信息。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class IdentityCardBO implements java.io.Serializable {
	
	private static final long serialVersionUID = -3407006433361473634L;
	
	private String name = null;
	private String cerNo = null;
	private String sex = null;
	private String birthday = null;
	private String folk = null;
	private String picData = null;
	private String picUrl = null;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicData() {
		return picData;
	}
	public void setPicData(String picData) {
		this.picData = picData;
	}
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getFolk() {
		return folk;
	}
	public void setFolk(String folk) {
		this.folk = folk;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	
	
}
