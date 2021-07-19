package com.gwssi.upload.pojo;

/**
 * E_BASEINFO企业基本信息
 * @author Administrator
 *
 */
public class EBaseinfo {
	private String pripid;
	private String entname;
	private String regno;
	private String enttype;
	private String regcap;
	private String industryphy;
	private String industryco;
	private String estdate;
	private String regorg;
	private String opscotype;
	private String opscotypeCn;
	private String reporttype;
	private String opscope;
	private String opfrom;
	private String opto;
	private String domdistrict;
	private String regstate;
	private String dom;
	private String name;
	private String empnum;
	private String uniscid;
	private String regcapcur;
	private String regcapusd;
	private String reccap;
	private String reccapusd;
	private String country;
	private String town;
	private String regorgCn;
	private String regstateCn;
	private String regcapcurCn;
	private String enttypeCn;
	private String apprdate;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getApprdate() {
		return apprdate;
	}
	public void setApprdate(String apprdate) {
		this.apprdate = apprdate;
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
	public String getEnttype() {
		return enttype;
	}
	public void setEnttype(String enttype) {
		this.enttype = enttype;
	}
	public String getRegcap() {
		return regcap;
	}
	public void setRegcap(String regcap) {
		this.regcap = regcap;
	}
	public String getIndustryphy() {
		return industryphy;
	}
	public void setIndustryphy(String industryphy) {
		this.industryphy = industryphy;
	}
	public String getIndustryco() {
		return industryco;
	}
	public void setIndustryco(String industryco) {
		this.industryco = industryco;
	}
	public String getEstdate() {
		return estdate;
	}
	public void setEstdate(String estdate) {
		this.estdate = estdate;
	}
	public String getRegorg() {
		return regorg;
	}
	public void setRegorg(String regorg) {
		this.regorg = regorg;
	}
	public String getOpscotype() {
		return opscotype;
	}
	public void setOpscotype(String opscotype) {
		this.opscotype = opscotype;
	}
	public String getOpscotypeCn() {
		return opscotypeCn;
	}
	public void setOpscotypeCn(String opscotypeCn) {
		this.opscotypeCn = opscotypeCn;
	}
	public String getReporttype() {
		return reporttype;
	}
	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}
	public String getOpscope() {
		return opscope;
	}
	public void setOpscope(String opscope) {
		this.opscope = opscope;
	}
	public String getOpfrom() {
		return opfrom;
	}
	public void setOpfrom(String opfrom) {
		this.opfrom = opfrom;
	}
	public String getOpto() {
		return opto;
	}
	public void setOpto(String opto) {
		this.opto = opto;
	}
	public String getDomdistrict() {
		return domdistrict;
	}
	public void setDomdistrict(String domdistrict) {
		this.domdistrict = domdistrict;
	}
	public String getRegstate() {
		return regstate;
	}
	public void setRegstate(String regstate) {
		this.regstate = regstate;
	}
	public String getDom() {
		return dom;
	}
	public void setDom(String dom) {
		this.dom = dom;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmpnum() {
		return empnum;
	}
	public void setEmpnum(String empnum) {
		this.empnum = empnum;
	}
	public String getUniscid() {
		return uniscid;
	}
	public void setUniscid(String uniscid) {
		this.uniscid = uniscid;
	}
	public String getRegcapcur() {
		return regcapcur;
	}
	public void setRegcapcur(String regcapcur) {
		this.regcapcur = regcapcur;
	}
	public String getRegcapusd() {
		return regcapusd;
	}
	public void setRegcapusd(String regcapusd) {
		this.regcapusd = regcapusd;
	}
	public String getReccap() {
		return reccap;
	}
	public void setReccap(String reccap) {
		this.reccap = reccap;
	}
	public String getReccapusd() {
		return reccapusd;
	}
	public void setReccapusd(String reccapusd) {
		this.reccapusd = reccapusd;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getRegorgCn() {
		return regorgCn;
	}
	public void setRegorgCn(String regorgCn) {
		this.regorgCn = regorgCn;
	}
	public String getRegstateCn() {
		return regstateCn;
	}
	public void setRegstateCn(String regstateCn) {
		this.regstateCn = regstateCn;
	}
	public String getRegcapcurCn() {
		return regcapcurCn;
	}
	public void setRegcapcurCn(String regcapcurCn) {
		this.regcapcurCn = regcapcurCn;
	}
	public String getEnttypeCn() {
		return enttypeCn;
	}
	public void setEnttypeCn(String enttypeCn) {
		this.enttypeCn = enttypeCn;
	}
	@Override
	public String toString() {
		return "EBaseinfo [pripid=" + pripid + ", entname=" + entname
				+ ", regno=" + regno + ", enttype=" + enttype + ", regcap="
				+ regcap + ", industryphy=" + industryphy + ", industryco="
				+ industryco + ", estdate=" + estdate + ", regorg=" + regorg
				+ ", opscotype=" + opscotype + ", opscotypeCn=" + opscotypeCn
				+ ", reporttype=" + reporttype + ", opscope=" + opscope
				+ ", opfrom=" + opfrom + ", opto=" + opto + ", domdistrict="
				+ domdistrict + ", regstate=" + regstate + ", dom=" + dom
				+ ", name=" + name + ", empnum=" + empnum + ", uniscid="
				+ uniscid + ", regcapcur=" + regcapcur + ", regcapusd="
				+ regcapusd + ", reccap=" + reccap + ", reccapusd=" + reccapusd
				+ ", country=" + country + ", town=" + town + ", regorgCn="
				+ regorgCn + ", regstateCn=" + regstateCn + ", regcapcurCn="
				+ regcapcurCn + ", enttypeCn=" + enttypeCn + ", apprdate="
				+ apprdate + ", count=" + count + "]";
	}
	
}
