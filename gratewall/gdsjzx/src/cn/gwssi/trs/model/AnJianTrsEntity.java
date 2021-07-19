package cn.gwssi.trs.model;

public class AnJianTrsEntity {
	
	public static void main(String[] args) {
		String test="AJID,applidique,ACCREGPER,REGTIME,ACCSCE,ACCTIME,KEYWORD,PNAME,PADDR,"+
		"PTEL,ENTTYPE,ENTADDR,ENTTEL,REGDEP,MainTel,MainAddr,MainDept,INVOPT"+
		",MDSENAME,atime,btime,url";
		System.out.println(test.toLowerCase());
	}
	
	
	private String ajid;
	private String applidique;
	private String accregper;
	private String regtime;
	private String accsce;
	private String acctime;
	private String keyword;
	private String pname;
	private String paddr;
	private String ptel;
	private String entaddr;
	private String enttel;
	private String regdep;
	private String invopt;
	private String mdsename;
	public String getAjid() {
		return ajid;
	}
	public void setAjid(String ajid) {
		this.ajid = ajid;
	}
	public String getApplidique() {
		return applidique;
	}
	public void setApplidique(String applidique) {
		this.applidique = applidique;
	}
	public String getAccregper() {
		return accregper;
	}
	public void setAccregper(String accregper) {
		this.accregper = accregper;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	public String getAccsce() {
		return accsce;
	}
	public void setAccsce(String accsce) {
		this.accsce = accsce;
	}
	public String getAcctime() {
		return acctime;
	}
	public void setAcctime(String acctime) {
		this.acctime = acctime;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPaddr() {
		return paddr;
	}
	public void setPaddr(String paddr) {
		this.paddr = paddr;
	}
	public String getPtel() {
		return ptel;
	}
	public void setPtel(String ptel) {
		this.ptel = ptel;
	}
	
	public String getEntaddr() {
		return entaddr;
	}
	public void setEntaddr(String entaddr) {
		this.entaddr = entaddr;
	}
	public String getEnttel() {
		return enttel;
	}
	public void setEnttel(String enttel) {
		this.enttel = enttel;
	}
	public String getRegdep() {
		return regdep;
	}
	public void setRegdep(String regdep) {
		this.regdep = regdep;
	}

	public String getInvopt() {
		return invopt;
	}
	public void setInvopt(String invopt) {
		this.invopt = invopt;
	}
	public String getMdsename() {
		return mdsename;
	}
	public void setMdsename(String mdsename) {
		this.mdsename = mdsename;
	}
	public String getAtime() {
		return atime;
	}
	public void setAtime(String atime) {
		this.atime = atime;
	}
	public String getBtime() {
		return btime;
	}
	public void setBtime(String btime) {
		this.btime = btime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	private String atime;
	private String btime;
	private String url;
	
}