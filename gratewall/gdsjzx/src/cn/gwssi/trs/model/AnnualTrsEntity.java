package cn.gwssi.trs.model;

public class AnnualTrsEntity {
	
	/*企业名称 注册号 企业类型 联系电话 通讯地址 电子邮箱 经营状态    登记机关     Market主键   年报提交年份(2014)
	    投资人	        投资类型　*/
	
	private String entName;
	private String regNo;
	private String entType;
	private String tel;
	private String addr;
	private String email;
	private String busSt;
	//private String department;
	private String pripid;
	private String invName;
	private String anCheYears;
	private String anCheYear;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEntName() {
		return entName;
	}
	public void setEntName(String entName) {
		this.entName = entName;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getEntType() {
		return entType;
	}
	public void setEntType(String entType) {
		this.entType = entType;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBusSt() {
		return busSt;
	}
	public void setBusSt(String busSt) {
		this.busSt = busSt;
	}

	public String getPripid() {
		return pripid;
	}
	public void setPripid(String pripid) {
		this.pripid = pripid;
	}
	public String getInvName() {
		return invName;
	}
	public void setInvName(String invName) {
		this.invName = invName;
	}
	public String getAnCheYears() {
		return anCheYears;
	}
	public void setAnCheYears(String anCheYears) {
		this.anCheYears = anCheYears;
	}
	public String getAnCheYear() {
		return anCheYear;
	}
	public void setAnCheYear(String anCheYear) {
		this.anCheYear = anCheYear;
	}
	
}