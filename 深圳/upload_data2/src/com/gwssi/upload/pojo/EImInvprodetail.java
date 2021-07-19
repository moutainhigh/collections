package com.gwssi.upload.pojo;

/**
 * 企业公示_出资人认缴明细
 * @author Administrator
 *
 */
public class EImInvprodetail {
	
	private String subid;
	private String invid; 
	private String inv; 
	private String subconam; 
	private String conform; 
	private String conformCn;
	private String condate; 
	private String publicdate;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getSubid() {
		return subid;
	}
	public void setSubid(String subid) {
		this.subid = subid;
	}
	public String getInvid() {
		return invid;
	}
	public void setInvid(String invid) {
		this.invid = invid;
	}
	public String getInv() {
		return inv;
	}
	public void setInv(String inv) {
		this.inv = inv;
	}
	public String getSubconam() {
		return subconam;
	}
	public void setSubconam(String subconam) {
		this.subconam = subconam;
	}
	public String getConform() {
		return conform;
	}
	public void setConform(String conform) {
		this.conform = conform;
	}
	public String getConformCn() {
		return conformCn;
	}
	public void setConformCn(String conformCn) {
		this.conformCn = conformCn;
	}
	public String getCondate() {
		return condate;
	}
	public void setCondate(String condate) {
		this.condate = condate;
	}
	public String getPublicdate() {
		return publicdate;
	}
	public void setPublicdate(String publicdate) {
		this.publicdate = publicdate;
	}
	@Override
	public String toString() {
		return "EImInvprodetail [subid=" + subid + ", invid=" + invid
				+ ", inv=" + inv + ", subconam=" + subconam + ", conform="
				+ conform + ", conformCn=" + conformCn + ", condate=" + condate
				+ ", publicdate=" + publicdate + "]";
	} 

}
