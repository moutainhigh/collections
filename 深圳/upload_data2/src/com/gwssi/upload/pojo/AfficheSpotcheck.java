package com.gwssi.upload.pojo;

/**
 * 抽查检查公告详情
 * @author Administrator
 *
 */
public class AfficheSpotcheck {
	
	private String ccjcid;
	private String noticeid;
	private String entname;
	private String regno;
	private String uniscid;
	private String insauth;
	private String insauthCn;
	private String insdate;
	private String insres;
	private String insresCn;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getCcjcid() {
		return ccjcid;
	}
	public void setCcjcid(String ccjcid) {
		this.ccjcid = ccjcid;
	}
	public String getNoticeid() {
		return noticeid;
	}
	public void setNoticeid(String noticeid) {
		this.noticeid = noticeid;
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
	public String getInsauth() {
		return insauth;
	}
	public void setInsauth(String insauth) {
		this.insauth = insauth;
	}
	public String getInsauthCn() {
		return insauthCn;
	}
	public void setInsauthCn(String insauthCn) {
		this.insauthCn = insauthCn;
	}
	public String getInsdate() {
		return insdate;
	}
	public void setInsdate(String insdate) {
		this.insdate = insdate;
	}
	public String getInsres() {
		return insres;
	}
	public void setInsres(String insres) {
		this.insres = insres;
	}
	public String getInsresCn() {
		return insresCn;
	}
	public void setInsresCn(String insresCn) {
		this.insresCn = insresCn;
	}
	
}
