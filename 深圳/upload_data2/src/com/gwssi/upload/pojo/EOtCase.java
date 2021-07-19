package com.gwssi.upload.pojo;

/**
 * 其他部门行政处罚信息
 * @author Administrator
 *
 */
public class EOtCase {
	
	private String caseid; 
	private String pripid; 
	private String regno; 
	private String uniscid; 
	private String entname; 
	private String pendecno; 
	private String illegacttype; 
	private String pentype; 
	private String pentypeCn; 
	private String penam; 
	private String forfam; 
	private String pencontent; 
	private String judauth; 
	private String pendecissdate; 
	private String remark; 
	private String publicdate; 
	private String datadept;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getCaseid() {
		return caseid;
	}
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}
	public String getPripid() {
		return pripid;
	}
	public void setPripid(String pripid) {
		this.pripid = pripid;
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
	public String getEntname() {
		return entname;
	}
	public void setEntname(String entname) {
		this.entname = entname;
	}
	public String getPendecno() {
		return pendecno;
	}
	public void setPendecno(String pendecno) {
		this.pendecno = pendecno;
	}
	public String getIllegacttype() {
		return illegacttype;
	}
	public void setIllegacttype(String illegacttype) {
		this.illegacttype = illegacttype;
	}
	public String getPentype() {
		return pentype;
	}
	public void setPentype(String pentype) {
		this.pentype = pentype;
	}
	public String getPentypeCn() {
		return pentypeCn;
	}
	public void setPentypeCn(String pentypeCn) {
		this.pentypeCn = pentypeCn;
	}
	public String getPenam() {
		return penam;
	}
	public void setPenam(String penam) {
		this.penam = penam;
	}
	public String getForfam() {
		return forfam;
	}
	public void setForfam(String forfam) {
		this.forfam = forfam;
	}
	public String getPencontent() {
		return pencontent;
	}
	public void setPencontent(String pencontent) {
		this.pencontent = pencontent;
	}
	public String getJudauth() {
		return judauth;
	}
	public void setJudauth(String judauth) {
		this.judauth = judauth;
	}
	public String getPendecissdate() {
		return pendecissdate;
	}
	public void setPendecissdate(String pendecissdate) {
		this.pendecissdate = pendecissdate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPublicdate() {
		return publicdate;
	}
	public void setPublicdate(String publicdate) {
		this.publicdate = publicdate;
	}
	public String getDatadept() {
		return datadept;
	}
	public void setDatadept(String datadept) {
		this.datadept = datadept;
	}
	@Override
	public String toString() {
		return "EOtCase [caseid=" + caseid + ", pripid=" + pripid + ", regno="
				+ regno + ", uniscid=" + uniscid + ", entname=" + entname
				+ ", pendecno=" + pendecno + ", illegacttype=" + illegacttype
				+ ", pentype=" + pentype + ", pentypeCn=" + pentypeCn
				+ ", penam=" + penam + ", forfam=" + forfam + ", pencontent="
				+ pencontent + ", judauth=" + judauth + ", pendecissdate="
				+ pendecissdate + ", remark=" + remark + ", publicdate="
				+ publicdate + ", datadept=" + datadept + "]";
	} 

}
