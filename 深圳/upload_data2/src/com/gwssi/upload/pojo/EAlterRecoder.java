package com.gwssi.upload.pojo;

/**
 * 变更备案信息
 * @author Administrator
 *
 */
public class EAlterRecoder {

	private String altid;
	private String pripid;
	private String altitem;
	private String altitemCn;
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

	public String getAltid() {
		return altid;
	}
	public void setAltid(String altid) {
		this.altid = altid;
	}
	public String getPripid() {
		return pripid;
	}
	public void setPripid(String pripid) {
		this.pripid = pripid;
	}
	public String getAltitem() {
		return altitem;
	}
	public void setAltitem(String altitem) {
		this.altitem = altitem;
	}
	public String getAltitemCn() {
		return altitemCn;
	}
	public void setAltitemCn(String altitemCn) {
		this.altitemCn = altitemCn;
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
		return "EAlterRecoder [altid=" + altid + ", pripid=" + pripid
				+ ", altitem=" + altitem + ", altitemCn=" + altitemCn
				+ ", altbe=" + altbe + ", altaf=" + altaf + ", altdate="
				+ altdate + "]";
	}
	
}
