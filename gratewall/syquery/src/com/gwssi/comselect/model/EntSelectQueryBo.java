package com.gwssi.comselect.model;

import java.util.Arrays;

/**
 * @author chaihw
 *
 */
public class EntSelectQueryBo {
	private String estdate_start;
	private String estdate_end;
	private String apprdate_start;
	private String apprdate_end;
	private String reccap_start ;
	private String reccap_end ;
	private String regcapcur ;//币种
	private String  industryphy ;//行业类别
	private String[] entname_term;//企业名称条件  包含 不包含
	private String[] entname; //企业名称
	
	private String[] opscope_term;//经营范围条件
	private String[] opscope;
	
	private String[] dom_term;//地址条件
	private String[] dom;
	
	private String[] regorg;//管辖区域
	private String[] adminbrancode;//所属监管所
	private String[] gongzuowangge;//工作网格
	private String[] danyuanwangge;// 单元网格
	
	private String enttype_radio ;//主体类型是大类还是小类 大类为00 小类为01
	private String[] enttype;//主体类型
	private String[] newenttype;//主体类型
	private String[] regstate;//注册状态
	
	private String year;//年报年底

	public String getEstdate_start() {
		return estdate_start;
	}

	public void setEstdate_start(String estdate_start) {
		this.estdate_start = estdate_start;
	}

	public String getEstdate_end() {
		return estdate_end;
	}

	public void setEstdate_end(String estdate_end) {
		this.estdate_end = estdate_end;
	}

	public String getApprdate_start() {
		return apprdate_start;
	}

	public void setApprdate_start(String apprdate_start) {
		this.apprdate_start = apprdate_start;
	}

	public String getApprdate_end() {
		return apprdate_end;
	}

	public void setApprdate_end(String apprdate_end) {
		this.apprdate_end = apprdate_end;
	}

	public String getReccap_start() {
		return reccap_start;
	}

	public void setReccap_start(String reccap_start) {
		this.reccap_start = reccap_start;
	}

	public String getReccap_end() {
		return reccap_end;
	}

	public void setReccap_end(String reccap_end) {
		this.reccap_end = reccap_end;
	}

	public String getRegcapcur() {
		return regcapcur;
	}

	public void setRegcapcur(String regcapcur) {
		this.regcapcur = regcapcur;
	}

	public String getIndustryphy() {
		return industryphy;
	}

	public void setIndustryphy(String industryphy) {
		this.industryphy = industryphy;
	}

	public String[] getEntname_term() {
		return entname_term;
	}

	public void setEntname_term(String[] entname_term) {
		this.entname_term = entname_term;
	}

	public String[] getEntname() {
		return entname;
	}

	public void setEntname(String[] entname) {
		this.entname = entname;
	}

	public String[] getOpscope_term() {
		return opscope_term;
	}

	public void setOpscope_term(String[] opscope_term) {
		this.opscope_term = opscope_term;
	}

	public String[] getOpscope() {
		return opscope;
	}

	public void setOpscope(String[] opscope) {
		this.opscope = opscope;
	}

	public String[] getDom_term() {
		return dom_term;
	}

	public void setDom_term(String[] dom_term) {
		this.dom_term = dom_term;
	}

	public String[] getDom() {
		return dom;
	}

	public void setDom(String[] dom) {
		this.dom = dom;
	}

	public String[] getRegorg() {
		return regorg;
	}

	public void setRegorg(String[] regorg) {
		this.regorg = regorg;
	}

	public String[] getAdminbrancode() {
		return adminbrancode;
	}

	public void setAdminbrancode(String[] adminbrancode) {
		this.adminbrancode = adminbrancode;
	}

	public String[] getGongzuowangge() {
		return gongzuowangge;
	}

	public void setGongzuowangge(String[] gongzuowangge) {
		this.gongzuowangge = gongzuowangge;
	}

	public String[] getDanyuanwangge() {
		return danyuanwangge;
	}

	public void setDanyuanwangge(String[] danyuanwangge) {
		this.danyuanwangge = danyuanwangge;
	}

	public String getEnttype_radio() {
		return enttype_radio;
	}

	public void setEnttype_radio(String enttype_radio) {
		this.enttype_radio = enttype_radio;
	}

	public String[] getEnttype() {
		return enttype;
	}

	public void setEnttype(String[] enttype) {
		this.enttype = enttype;
	}

	public String[] getRegstate() {
		return regstate;
	}

	public void setRegstate(String[] regstate) {
		this.regstate = regstate;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "EntSelectQueryBo [estdate_start=" + estdate_start
				+ ", estdate_end=" + estdate_end + ", apprdate_start="
				+ apprdate_start + ", apprdate_end=" + apprdate_end
				+ ", reccap_start=" + reccap_start + ", reccap_end="
				+ reccap_end + ", regcapcur=" + regcapcur + ", industryphy="
				+ industryphy + ", entname_term="
				+ Arrays.toString(entname_term) + ", entname="
				+ Arrays.toString(entname) + ", opscope_term="
				+ Arrays.toString(opscope_term) + ", opscope="
				+ Arrays.toString(opscope) + ", dom_term="
				+ Arrays.toString(dom_term) + ", dom=" + Arrays.toString(dom)
				+ ", regorg=" + Arrays.toString(regorg) + ", adminbrancode="
				+ Arrays.toString(adminbrancode) + ", gongzuowangge="
				+ Arrays.toString(gongzuowangge) + ", danyuanwangge="
				+ Arrays.toString(danyuanwangge) + ", enttype_radio="
				+ enttype_radio + ", enttype=" + Arrays.toString(enttype)
				+ ", regstate=" + Arrays.toString(regstate) + ", year=" + year
				+ "]";
	}

	public void setNewenttype(String[] newenttype) {
		this.newenttype = newenttype;
	}

	public String[] getNewenttype() {
		return newenttype;
	}
	
}
