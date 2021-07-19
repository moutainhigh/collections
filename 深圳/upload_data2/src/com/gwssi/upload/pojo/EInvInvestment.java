package com.gwssi.upload.pojo;

/**
 * 非自然人投资信息
 * @author Administrator
 *
 */
public class EInvInvestment {
	
	private String invid; 
	private String pripid; 
	private String inv; 
	private String invtype; 
	private String invtypeCn; 
	private String blictype; 
	private String blictypeCn; 
	private String blicno; 
	private String subconam; 
	private String subconamusd; 
	private String subconform; 
	private String subconprop; 
	private String condate; 
	private String acconam; 
	private String acconamusd; 
	private String dom; 
	private String currency; 
	private String currencyCn; 
	private String country; 
	private String countryCn; 
	private String exeaffsign; 
	private String respform; 
	private String respformCn;
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
	public String getInv() {
		return inv;
	}
	public void setInv(String inv) {
		this.inv = inv;
	}
	public String getInvtype() {
		return invtype;
	}
	public void setInvtype(String invtype) {
		this.invtype = invtype;
	}
	public String getInvtypeCn() {
		return invtypeCn;
	}
	public void setInvtypeCn(String invtypeCn) {
		this.invtypeCn = invtypeCn;
	}
	public String getBlictype() {
		return blictype;
	}
	public void setBlictype(String blictype) {
		this.blictype = blictype;
	}
	public String getBlictypeCn() {
		return blictypeCn;
	}
	public void setBlictypeCn(String blictypeCn) {
		this.blictypeCn = blictypeCn;
	}
	public String getBlicno() {
		return blicno;
	}
	public void setBlicno(String blicno) {
		this.blicno = blicno;
	}
	public String getSubconam() {
		return subconam;
	}
	public void setSubconam(String subconam) {
		this.subconam = subconam;
	}
	public String getSubconamusd() {
		return subconamusd;
	}
	public void setSubconamusd(String subconamusd) {
		this.subconamusd = subconamusd;
	}
	public String getSubconform() {
		return subconform;
	}
	public void setSubconform(String subconform) {
		this.subconform = subconform;
	}
	public String getSubconprop() {
		return subconprop;
	}
	public void setSubconprop(String subconprop) {
		this.subconprop = subconprop;
	}
	public String getCondate() {
		return condate;
	}
	public void setCondate(String condate) {
		this.condate = condate;
	}
	public String getAcconam() {
		return acconam;
	}
	public void setAcconam(String acconam) {
		this.acconam = acconam;
	}
	public String getAcconamusd() {
		return acconamusd;
	}
	public void setAcconamusd(String acconamusd) {
		this.acconamusd = acconamusd;
	}
	public String getDom() {
		return dom;
	}
	public void setDom(String dom) {
		this.dom = dom;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrencyCn() {
		return currencyCn;
	}
	public void setCurrencyCn(String currencyCn) {
		this.currencyCn = currencyCn;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountryCn() {
		return countryCn;
	}
	public void setCountryCn(String countryCn) {
		this.countryCn = countryCn;
	}
	public String getExeaffsign() {
		return exeaffsign;
	}
	public void setExeaffsign(String exeaffsign) {
		this.exeaffsign = exeaffsign;
	}
	public String getRespform() {
		return respform;
	}
	public void setRespform(String respform) {
		this.respform = respform;
	}
	public String getRespformCn() {
		return respformCn;
	}
	public void setRespformCn(String respformCn) {
		this.respformCn = respformCn;
	}
	@Override
	public String toString() {
		return "EInvInvestment [invid=" + invid + ", pripid=" + pripid
				+ ", inv=" + inv + ", invtype=" + invtype + ", invtypeCn="
				+ invtypeCn + ", blictype=" + blictype + ", blictypeCn="
				+ blictypeCn + ", blicno=" + blicno + ", subconam=" + subconam
				+ ", subconamusd=" + subconamusd + ", subconform=" + subconform
				+ ", subconprop=" + subconprop + ", condate=" + condate
				+ ", acconam=" + acconam + ", acconamusd=" + acconamusd
				+ ", dom=" + dom + ", currency=" + currency + ", currencyCn="
				+ currencyCn + ", country=" + country + ", countryCn="
				+ countryCn + ", exeaffsign=" + exeaffsign + ", respform="
				+ respform + ", respformCn=" + respformCn + "]";
	} 
	

}
