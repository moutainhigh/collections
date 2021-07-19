package com.gwssi.upload.pojo;

/**
 * 案件移除信息
 * @author Administrator
 *
 */
public class CaseCfTrans {

	private String casetranid;
	private String caseid;
	private String accptranfauthtype;
	private String accptranfauth;
	private String tranfreatype;
	private String tranfrea;
	private String tranfdate;
	private String tranfnum;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getCasetranid() {
		return casetranid;
	}
	public void setCasetranid(String casetranid) {
		this.casetranid = casetranid;
	}
	public String getCaseid() {
		return caseid;
	}
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}
	public String getAccptranfauthtype() {
		return accptranfauthtype;
	}
	public void setAccptranfauthtype(String accptranfauthtype) {
		this.accptranfauthtype = accptranfauthtype;
	}
	public String getAccptranfauth() {
		return accptranfauth;
	}
	public void setAccptranfauth(String accptranfauth) {
		this.accptranfauth = accptranfauth;
	}
	public String getTranfreatype() {
		return tranfreatype;
	}
	public void setTranfreatype(String tranfreatype) {
		this.tranfreatype = tranfreatype;
	}
	public String getTranfrea() {
		return tranfrea;
	}
	public void setTranfrea(String tranfrea) {
		this.tranfrea = tranfrea;
	}
	public String getTranfdate() {
		return tranfdate;
	}
	public void setTranfdate(String tranfdate) {
		this.tranfdate = tranfdate;
	}
	
	public String getTranfnum() {
		return tranfnum;
	}
	public void setTranfnum(String tranfnum) {
		this.tranfnum = tranfnum;
	}
	@Override
	public String toString() {
		return "CaseCfTrans [casetranid=" + casetranid + ", caseid=" + caseid
				+ ", accptranfauthtype=" + accptranfauthtype
				+ ", accptranfauth=" + accptranfauth + ", tranfreatype="
				+ tranfreatype + ", tranfrea=" + tranfrea + ", tranfdate="
				+ tranfdate + ", tranfnum=" + tranfnum + ", count=" + count
				+ "]";
	}
	
	
}
