package com.gwssi.upload.pojo;

/**
 * 企业公示_许可变更信息
 * @author Administrator
 *
 */
public class EImPrmtalt {

	private String licaltid; 
	private String licid; 
	private String alt; 
	private String altbe; 
	private String altaf; 
	private String altdate;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getLicaltid() {
		return licaltid;
	}
	public void setLicaltid(String licaltid) {
		this.licaltid = licaltid;
	}
	public String getLicid() {
		return licid;
	}
	public void setLicid(String licid) {
		this.licid = licid;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getAltbe() {
		return altbe;
	}
	public void setAltbe(String altbe) {
		this.altbe = altbe;
	}
	public String getAltaf() {
		return altaf;
	}
	public void setAltaf(String altaf) {
		this.altaf = altaf;
	}
	public String getAltdate() {
		return altdate;
	}
	public void setAltdate(String altdate) {
		this.altdate = altdate;
	}
	@Override
	public String toString() {
		return "EImPrmtalt [licaltid=" + licaltid + ", licid=" + licid
				+ ", alt=" + alt + ", altbe=" + altbe + ", altaf=" + altaf
				+ ", altdate=" + altdate + "]";
	} 
	
}
