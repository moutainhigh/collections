package com.gwssi.upload.pojo;

/**
 * 案件基本信息
 * @author Administrator
 *
 */
public class CaseCfBaseinfo {

	private String caseid;
	private String caseno;
	private String casename;
	private String casescedistrict;
	private String casespot;
	private String casetime;
	private String casereason;
	private String caseval;
	private String appprocedure;
	public String getAppprocedure() {
		return appprocedure;
	}
	public void setAppprocedure(String appprocedure) {
		this.appprocedure = appprocedure;
	}

	private String caseinternetsign;
	private String caseforsign;
	private String casestate;
	private String casefiauth;
	private String casefidate;
	private String exedate;
	private String exesort;
	private String unexereasort;
	private String caseresult;
	private String casedep;
	private String clocaserea;
	private String clocasedate;
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
	public String getCaseno() {
		return caseno;
	}
	public void setCaseno(String caseno) {
		this.caseno = caseno;
	}
	public String getCasename() {
		return casename;
	}
	public void setCasename(String casename) {
		this.casename = casename;
	}
	public String getCasescedistrict() {
		return casescedistrict;
	}
	public void setCasescedistrict(String casescedistrict) {
		this.casescedistrict = casescedistrict;
	}
	public String getCasespot() {
		return casespot;
	}
	public void setCasespot(String casespot) {
		this.casespot = casespot;
	}
	public String getCasetime() {
		return casetime;
	}
	public void setCasetime(String casetime) {
		this.casetime = casetime;
	}
	public String getCasereason() {
		return casereason;
	}
	public void setCasereason(String casereason) {
		this.casereason = casereason;
	}
	public String getCaseval() {
		return caseval;
	}
	public void setCaseval(String caseval) {
		this.caseval = caseval;
	}
	public String getCaseinternetsign() {
		return caseinternetsign;
	}
	public void setCaseinternetsign(String caseinternetsign) {
		this.caseinternetsign = caseinternetsign;
	}
	public String getCaseforsign() {
		return caseforsign;
	}
	public void setCaseforsign(String caseforsign) {
		this.caseforsign = caseforsign;
	}
	public String getCasestate() {
		return casestate;
	}
	public void setCasestate(String casestate) {
		this.casestate = casestate;
	}
	public String getCasefiauth() {
		return casefiauth;
	}
	public void setCasefiauth(String casefiauth) {
		this.casefiauth = casefiauth;
	}
	public String getCasefidate() {
		return casefidate;
	}
	public void setCasefidate(String casefidate) {
		this.casefidate = casefidate;
	}
	public String getExedate() {
		return exedate;
	}
	public void setExedate(String exedate) {
		this.exedate = exedate;
	}
	public String getExesort() {
		return exesort;
	}
	public void setExesort(String exesort) {
		this.exesort = exesort;
	}
	public String getUnexereasort() {
		return unexereasort;
	}
	public void setUnexereasort(String unexereasort) {
		this.unexereasort = unexereasort;
	}
	public String getCaseresult() {
		return caseresult;
	}
	public void setCaseresult(String caseresult) {
		this.caseresult = caseresult;
	}
	public String getCasedep() {
		return casedep;
	}
	public void setCasedep(String casedep) {
		this.casedep = casedep;
	}
	public String getClocaserea() {
		return clocaserea;
	}
	public void setClocaserea(String clocaserea) {
		this.clocaserea = clocaserea;
	}
	public String getClocasedate() {
		return clocasedate;
	}
	public void setClocasedate(String clocasedate) {
		this.clocasedate = clocasedate;
	}
	
	@Override
	public String toString() {
		return "CaseCfBaseinfo [caseid=" + caseid + ", caseno=" + caseno
				+ ", casename=" + casename + ", casescedistrict="
				+ casescedistrict + ", casespot=" + casespot + ", casetime="
				+ casetime + ", casereason=" + casereason + ", caseval="
				+ caseval + ", appprocedure=" + appprocedure
				+ ", caseinternetsign=" + caseinternetsign + ", caseforsign="
				+ caseforsign + ", casestate=" + casestate + ", casefiauth="
				+ casefiauth + ", casefidate=" + casefidate + ", exedate="
				+ exedate + ", exesort=" + exesort + ", unexereasort="
				+ unexereasort + ", caseresult=" + caseresult + ", casedep="
				+ casedep + ", clocaserea=" + clocaserea + ", clocasedate="
				+ clocasedate + ", count=" + count + "]";
	}
	
	
	
}
