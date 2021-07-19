package com.gwssi.upload.pojo;

/**
 * 历史名称信息
 * @author Administrator
 *
 */
public class ELpHstname {
	
	private String hisentnameid; 
	private String pripid; 
	private String entname;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getHisentnameid() {
		return hisentnameid;
	}
	public void setHisentnameid(String hisentnameid) {
		this.hisentnameid = hisentnameid;
	}
	public String getPripid() {
		return pripid;
	}
	public void setPripid(String pripid) {
		this.pripid = pripid;
	}
	public String getEntname() {
		return entname;
	}
	public void setEntname(String entname) {
		this.entname = entname;
	}
	@Override
	public String toString() {
		return "ELpHstname [hisentnameid=" + hisentnameid + ", pripid="
				+ pripid + ", entname=" + entname + "]";
	} 

}
