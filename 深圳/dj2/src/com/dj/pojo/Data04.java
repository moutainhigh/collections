package com.dj.pojo;

public class Data04 implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String gdlx;// 股东类型，1个人，2公司  001
	private String inv;//姓名或企业名称  002
	private String certype;//证件或证照类型 003
	private String cerno;///证件或证找号码 004
	private String subconam;//认缴出资额 005
	private String subconam_date;//认缴出资日期 006
	private String conprop; //持股比例007
	private String acconam;//实缴出资额008
	private String acconam_fs;//实缴方式009
	
	
	public String getGdlx() {
		return gdlx;
	}
	public void setGdlx(String gdlx) {
		this.gdlx = gdlx;
	}
	public String getInv() {
		return inv;
	}
	public void setInv(String inv) {
		this.inv = inv;
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
	public String getSubconam() {
		return subconam;
	}
	public void setSubconam(String subconam) {
		this.subconam = subconam;
	}
	public String getSubconam_date() {
		return subconam_date;
	}
	public void setSubconam_date(String subconam_date) {
		this.subconam_date = subconam_date;
	}
	public String getConprop() {
		return conprop;
	}
	public void setConprop(String conprop) {
		this.conprop = conprop;
	}
	public String getAcconam() {
		return acconam;
	}
	public void setAcconam(String acconam) {
		this.acconam = acconam;
	}
	public String getAcconam_fs() {
		return acconam_fs;
	}
	public void setAcconam_fs(String acconam_fs) {
		this.acconam_fs = acconam_fs;
	}
	
	
	

}
