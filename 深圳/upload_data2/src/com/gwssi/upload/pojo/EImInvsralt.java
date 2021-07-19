package com.gwssi.upload.pojo;

/**
 * 企业公示_股权变更信息
 * @author Administrator
 *
 */
public class EImInvsralt {
	
	private String invuid; 
	private String pripid; 
	private String entname; 
	private String regno; 
	private String uniscid; 
	private String alitem;
	private String altbe;
	private String altaf;
	private String altdate;
	private String publicdate;
	
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getPublicdate() {
		return publicdate;
	}
	public void setPublicdate(String publicdate) {
		this.publicdate = publicdate;
	}
	public String getInvuid() {
		return invuid;
	}
	public void setInvuid(String invuid) {
		this.invuid = invuid;
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
	public String getAlitem() {
		return alitem;
	}
	public void setAlitem(String alitem) {
		this.alitem = alitem;
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
		return "EImInvsralt [invuid=" + invuid + ", pripid=" + pripid
				+ ", entname=" + entname + ", regno=" + regno + ", uniscid="
				+ uniscid + ", alitem=" + alitem + ", altbe=" + altbe
				+ ", altaf=" + altaf + ", altdate=" + altdate + ", publicdate="
				+ publicdate + ", count=" + count + "]";
	}

}
