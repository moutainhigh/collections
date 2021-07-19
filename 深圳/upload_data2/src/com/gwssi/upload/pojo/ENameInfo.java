package com.gwssi.upload.pojo;

/**
 * 企业名称信息
 * @author Administrator
 *
 */
public class ENameInfo {
	
	private String nameId; 
	private String pripid; 
	private String entname; 
	private String namedistrict; 
	private String enttra; 
	private String nameind; 
	private String orgform; 
	private String tradpiny; 
	private String grpshform; 
	private String savedperto; 
	private String apprno; 
	private String apprauth; 
	private String preregorg; 
	private String apprdate;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getNameId() {
		return nameId;
	}
	public void setNameId(String nameId) {
		this.nameId = nameId;
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
	public String getNamedistrict() {
		return namedistrict;
	}
	public void setNamedistrict(String namedistrict) {
		this.namedistrict = namedistrict;
	}
	public String getEnttra() {
		return enttra;
	}
	public void setEnttra(String enttra) {
		this.enttra = enttra;
	}
	public String getNameind() {
		return nameind;
	}
	public void setNameind(String nameind) {
		this.nameind = nameind;
	}
	public String getOrgform() {
		return orgform;
	}
	public void setOrgform(String orgform) {
		this.orgform = orgform;
	}
	public String getTradpiny() {
		return tradpiny;
	}
	public void setTradpiny(String tradpiny) {
		this.tradpiny = tradpiny;
	}
	public String getGrpshform() {
		return grpshform;
	}
	public void setGrpshform(String grpshform) {
		this.grpshform = grpshform;
	}
	public String getSavedperto() {
		return savedperto;
	}
	public void setSavedperto(String savedperto) {
		this.savedperto = savedperto;
	}
	public String getApprno() {
		return apprno;
	}
	public void setApprno(String apprno) {
		this.apprno = apprno;
	}
	public String getApprauth() {
		return apprauth;
	}
	public void setApprauth(String apprauth) {
		this.apprauth = apprauth;
	}
	public String getPreregorg() {
		return preregorg;
	}
	public void setPreregorg(String preregorg) {
		this.preregorg = preregorg;
	}
	public String getApprdate() {
		return apprdate;
	}
	public void setApprdate(String apprdate) {
		this.apprdate = apprdate;
	}
	@Override
	public String toString() {
		return "ENameInfo [nameId=" + nameId + ", pripid=" + pripid + ", entname=" + entname + ", namedistrict="
				+ namedistrict + ", enttra=" + enttra + ", nameind=" + nameind + ", orgform=" + orgform + ", tradpiny="
				+ tradpiny + ", grpshform=" + grpshform + ", savedperto=" + savedperto + ", apprno=" + apprno
				+ ", apprauth=" + apprauth + ", preregorg=" + preregorg + ", apprdate=" + apprdate + ", count=" + count
				+ "]";
	} 
	
	

}
