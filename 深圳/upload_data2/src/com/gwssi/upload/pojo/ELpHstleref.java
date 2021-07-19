package com.gwssi.upload.pojo;

/**
 * 历史法定代表人信息
 * @author Administrator
 *
 */
public class ELpHstleref {
	
	private String hisnameid; 
	private String pripid; 
	private String name; 
	private String certype; 
	private String cerno;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getHisnameid() {
		return hisnameid;
	}
	public void setHisnameid(String hisnameid) {
		this.hisnameid = hisnameid;
	}
	public String getPripid() {
		return pripid;
	}
	public void setPripid(String pripid) {
		this.pripid = pripid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCertype() {
		return certype;
	}
	public void setCertype(String certype) {
		this.certype = certype;
	}
	public String getCerno() {
		return cerno;
	}
	public void setCerno(String cerno) {
		this.cerno = cerno;
	}
	@Override
	public String toString() {
		return "ELpHstleref [hisnameid=" + hisnameid + ", pripid=" + pripid
				+ ", name=" + name + ", certype=" + certype + ", cerno="
				+ cerno + "]";
	} 
	
}
