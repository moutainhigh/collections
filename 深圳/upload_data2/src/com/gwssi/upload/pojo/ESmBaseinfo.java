package com.gwssi.upload.pojo;

/**
 * 小微企业名录信息
 * @author Administrator
 *
 */
public class ESmBaseinfo {
	
	private String pripid; 
	private String uniscid; 
	private String regno; 
	private String entname; 
	private String enttype; 
	private String estdate; 
	private String regcap; 
	private String regorg; 
	private String industryphy; 
	private String industryco;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	public String getPripid() {
		return pripid;
	}
	public void setPripid(String pripid) {
		this.pripid = pripid;
	}
	public String getUniscid() {
		return uniscid;
	}
	public void setUniscid(String uniscid) {
		this.uniscid = uniscid;
	}
	public String getRegno() {
		return regno;
	}
	public void setRegno(String regno) {
		this.regno = regno;
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
	public String getRegcap() {
		return regcap;
	}
	public void setRegcap(String regcap) {
		this.regcap = regcap;
	}
	
	public String getRegorg() {
		return regorg;
	}
	public void setRegorg(String regorg) {
		this.regorg = regorg;
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
	@Override
	public String toString() {
		return "ESmBaseinfo [pripid=" + pripid + ", uniscid=" + uniscid
				+ ", regno=" + regno + ", entname=" + entname + ", enttype="
				+ enttype + ", estdate=" + estdate + ", regcap=" + regcap
				+ ", regorg=" + regorg + ", industryphy=" + industryphy
				+ ", industryco=" + industryco + ", count=" + count + "]";
	} 

}
