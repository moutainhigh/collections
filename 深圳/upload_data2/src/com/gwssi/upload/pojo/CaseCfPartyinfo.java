package com.gwssi.upload.pojo;

/**
 * 案件当事人信息
 * @author Administrator
 *
 */
public class CaseCfPartyinfo {

	private String casepartyid;
	private String caseid;
	private String partytype;
	private String unitname;
	private String pripid;
	private String regno;
	private String enttype;
	private String uniscid;
	private String lerep;
	private String sex;
	private String age;
	private String occupation;
	private String dom;
	private String tel;
	private String postalcode;
	private String workunit;
	private String certype;
	private String cerno;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getCasepartyid() {
		return casepartyid;
	}
	public void setCasepartyid(String casepartyid) {
		this.casepartyid = casepartyid;
	}
	public String getCaseid() {
		return caseid;
	}
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}
	public String getPartytype() {
		return partytype;
	}
	public void setPartytype(String partytype) {
		this.partytype = partytype;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
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
	public String getEnttype() {
		return enttype;
	}
	public void setEnttype(String enttype) {
		this.enttype = enttype;
	}
	public String getUniscid() {
		return uniscid;
	}
	public void setUniscid(String uniscid) {
		this.uniscid = uniscid;
	}
	public String getLerep() {
		return lerep;
	}
	public void setLerep(String lerep) {
		this.lerep = lerep;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getDom() {
		return dom;
	}
	public void setDom(String dom) {
		this.dom = dom;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getWorkunit() {
		return workunit;
	}
	public void setWorkunit(String workunit) {
		this.workunit = workunit;
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
	@Override
	public String toString() {
		return "CaseCfPartyinfo [casepartyid=" + casepartyid + ", caseid="
				+ caseid + ", partytype=" + partytype + ", unitname="
				+ unitname + ", pripid=" + pripid + ", regno=" + regno
				+ ", enttype=" + enttype + ", uniscid=" + uniscid + ", lerep="
				+ lerep + ", sex=" + sex + ", age=" + age + ", occupation="
				+ occupation + ", dom=" + dom + ", tel=" + tel
				+ ", postalcode=" + postalcode + ", workunit=" + workunit
				+ ", certype=" + certype + ", cerno=" + cerno + "]";
	}
	
	
	
}
