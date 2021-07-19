package com.gwssi.upload.pojo;

/**
 * 投资人情况信息
 * @author Administrator
 *
 */
public class EImInvestment {
	
	private String invid;
	private String pripid;
	private String entname;
	private String regno;
	private String uniscid; 
	private String inv;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getInvid() {
		return invid;
	}
	public void setInvid(String invid) {
		this.invid = invid;
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
	public String getRegno() {
		return regno;
	}
	public void setRegno(String regno) {
		this.regno = regno;
	}
	public String getUniscid() {
		return uniscid;
	}
	public void setUniscid(String uniscid) {
		this.uniscid = uniscid;
	}
	public String getInv() {
		return inv;
	}
	public void setInv(String inv) {
		this.inv = inv;
	}
	@Override
	public String toString() {
		return "EImInvestment [invid=" + invid + ", pripid=" + pripid
				+ ", entname=" + entname + ", regno=" + regno + ", uniscid="
				+ uniscid + ", inv=" + inv + "]";
	}

}
