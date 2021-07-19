package com.gwssi.comselect.model;



/**
 * 案件查询对应Bo类
 * @author yzh
 */
public class CaseSelectQueryBo{

	private String caseNo;//案件编号
	private String caseName;//案件名称
	private String caseState;//案件状态
	private String caseFiauth;//立案机关
	private String caseFiDate;//立案日期
	private String cloCaseDate;//销案日期
	private String LITIGANTTYPE;//当事人类型
	private String unitName;//当事人名称
	private String regNo;//注册号
	private String uniScid;//统一社会信用代码
	private String cerNo;//证件号码
	
	public String getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCaseState() {
		return caseState;
	}
	public void setCaseState(String caseState) {
		this.caseState = caseState;
	}
	public String getCaseFiauth() {
		return caseFiauth;
	}
	public void setCaseFiauth(String caseFiauth) {
		this.caseFiauth = caseFiauth;
	}
	public String getCaseFiDate() {
		return caseFiDate;
	}
	public void setCaseFiDate(String caseFiDate) {
		this.caseFiDate = caseFiDate;
	}
	public String getCloCaseDate() {
		return cloCaseDate;
	}
	public void setCloCaseDate(String cloCaseDate) {
		this.cloCaseDate = cloCaseDate;
	}

	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getUniScid() {
		return uniScid;
	}
	public void setUniScid(String uniScid) {
		this.uniScid = uniScid;
	}
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	
	public void setLITIGANTTYPE(String lITIGANTTYPE) {
		LITIGANTTYPE = lITIGANTTYPE;
	}
	public String getLITIGANTTYPE() {
		return LITIGANTTYPE;
	}
	
	@Override
	public String toString() {
		return "CaseSelectQueryBo [caseNo=" + caseNo + ", caseName=" + caseName
				+ ", caseState=" + caseState + ", caseFiauth=" + caseFiauth
				+ ", caseFiDate=" + caseFiDate + ", cloCaseDate=" + cloCaseDate
				+ ", partyType=" + LITIGANTTYPE + ", unitName=" + unitName
				+ ", regNo=" + regNo + ", uniScid=" + uniScid + ", cerNo="
				+ cerNo + "]";
	}
}
