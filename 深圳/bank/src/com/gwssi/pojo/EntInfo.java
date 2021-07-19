package com.gwssi.pojo;

import java.util.Date;


/**
 * 企业信息
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class EntInfo {
	
	public EntInfo() {}
	
	private String apprdate;		//核准日期
	private String dom;			//住所
	private String entname;		//企业名称
	private String enttype;		//企业类型
	private String estdate;		//成立日期
	private String industryphy;	//行业类别
	private String lerep;		//法定代表人
	private String cerno;		//法人证件号码
	private String opfrom;		//经营期限自
	private String opscope;		//经营范围
	private String opto;		//经营期限至
	//private Double regcap;		//注册资本
	private String regcap;		//注册资本
	private String regno;		//注册号
	private String unifsocicrediden;	//统一社会信用代码
	
	public String getApprdate() {
		return apprdate;
	}

	public void setApprdate(String apprdate) {
		this.apprdate = apprdate;
	}

	public String getDom() {
		return dom;
	}

	public void setDom(String dom) {
		this.dom = dom;
	}

	public String getEntname() {
		return entname;
	}

	public void setEntname(String entname) {
		this.entname = entname;
	}

	public String getEnttype() {
		return enttype;
	}

	public void setEnttype(String enttype) {
		this.enttype = enttype;
	}

	public String getEstdate() {
		return estdate;
	}

	public void setEstdate(String estdate) {
		this.estdate = estdate;
	}

	public String getIndustryphy() {
		return industryphy;
	}

	public void setIndustryphy(String industryphy) {
		this.industryphy = industryphy;
	}

	public String getLerep() {
		return lerep;
	}

	public void setLerep(String lerep) {
		this.lerep = lerep;
	}

	public String getCerno() {
		return cerno;
	}

	public void setCerno(String cerno) {
		this.cerno = cerno;
	}

	public String getOpfrom() {
		return opfrom;
	}

	public void setOpfrom(String opfrom) {
		this.opfrom = opfrom;
	}

	public String getOpscope() {
		return opscope;
	}

	public void setOpscope(String opscope) {
		this.opscope = opscope;
	}

	public String getOpto() {
		return opto;
	}

	public void setOpto(String opto) {
		this.opto = opto;
	}

	public String getRegcap() {
		return regcap;
	}

	public void setRegcap(String regcap) {
		this.regcap = regcap;
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public String getUnifsocicrediden() {
		return unifsocicrediden;
	}

	public void setUnifsocicrediden(String unifsocicrediden) {
		this.unifsocicrediden = unifsocicrediden;
	}

	@Override
	public String toString() {
		return "EntInfo [apprdate=" + apprdate + ", dom=" + dom + ", entname="
				+ entname + ", enttype=" + enttype + ", estdate=" + estdate
				+ ", industryphy=" + industryphy + ", lerep=" + lerep
				+ ", cerno=" + cerno + ", opfrom=" + opfrom + ", opscope="
				+ opscope + ", opto=" + opto + ", regcap=" + regcap
				+ ", regno=" + regno + ", unifsocicrediden=" + unifsocicrediden
				+ "]";
	}
	
}
