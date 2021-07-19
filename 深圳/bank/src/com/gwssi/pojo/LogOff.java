package com.gwssi.pojo;

import java.security.Timestamp;
import java.util.Date;

/**
 * 企业注销信息
 * @author Administrator
 *
 */
public class LogOff {
	
	private String apprdate;	//核准日期
	private String entname;		//企业名称
	private String processnum;	//注销流程号
	private String industryphy;	//行业类别
	private String lerep;		//法定代表人
	private String cerno;		//法人证件号
	private String canrea;		//注销原因
	private String remarks;		//注销备注
	private String printdate;	//打印时间
	private String regno;		//注册号
	private String unifsocicrediden;	//统一社会信用代码
	
	public String getApprdate() {
		return apprdate;
	}

	public void setApprdate(String apprdate) {
		this.apprdate = apprdate;
	}

	public String getEntname() {
		return entname;
	}

	public void setEntname(String entname) {
		this.entname = entname;
	}

	public String getProcessnum() {
		return processnum;
	}

	public void setProcessnum(String processnum) {
		this.processnum = processnum;
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

	public String getCanrea() {
		return canrea;
	}

	public void setCanrea(String canrea) {
		this.canrea = canrea;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPrintdate() {
		return printdate;
	}

	public void setPrintdate(String printdate) {
		this.printdate = printdate;
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
		return "LogOff [apprdate=" + apprdate + ", entname=" + entname
				+ ", processnum=" + processnum + ", industryphy=" + industryphy
				+ ", lerep=" + lerep + ", cerno=" + cerno + ", canrea="
				+ canrea + ", remarks=" + remarks + ", printdate=" + printdate
				+ ", regno=" + regno + ", unifsocicrediden=" + unifsocicrediden
				+ "]";
	}
}
