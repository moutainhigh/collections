package com.gwssi.upload.pojo;

/**
 * 企业公示_许可信息
 * @author Administrator
 *
 */
public class EImPermit {

	private String licid; 
	private String pripid; 
	private String entname; 
	private String regno; 
	private String uniscid; 
	private String licno; 
	private String licnameCn; 
	private String valfrom; 
	private String valto; 
	private String licanth; 
	private String licitem; 
	private String type; 
	private String candate; 
	private String equplecanrea; 
	private String revdate; 
	private String sugrevreason; 
	private String invaliddate; 
	private String invalidrea; 
	private String publicdate;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getLicid() {
		return licid;
	}
	public void setLicid(String licid) {
		this.licid = licid;
	}
	public String getPripid() {
		return pripid;
	}
	public void setPripid(String pripid) {
		this.pripid = pripid;
	}
	public String getEntname() {
		return entname;
	}
	public void setEntname(String entname) {
		this.entname = entname;
	}
	public String getRegno() {
		return regno;
	}
	public void setRegno(String regno) {
		this.regno = regno;
	}
	public String getUniscid() {
		return uniscid;
	}
	public void setUniscid(String uniscid) {
		this.uniscid = uniscid;
	}
	public String getLicno() {
		return licno;
	}
	public void setLicno(String licno) {
		this.licno = licno;
	}
	public String getLicnameCn() {
		return licnameCn;
	}
	public void setLicnameCn(String licnameCn) {
		this.licnameCn = licnameCn;
	}
	public String getValfrom() {
		return valfrom;
	}
	public void setValfrom(String valfrom) {
		this.valfrom = valfrom;
	}
	public String getValto() {
		return valto;
	}
	public void setValto(String valto) {
		this.valto = valto;
	}
	public String getLicanth() {
		return licanth;
	}
	public void setLicanth(String licanth) {
		this.licanth = licanth;
	}
	public String getLicitem() {
		return licitem;
	}
	public void setLicitem(String licitem) {
		this.licitem = licitem;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCandate() {
		return candate;
	}
	public void setCandate(String candate) {
		this.candate = candate;
	}
	public String getEquplecanrea() {
		return equplecanrea;
	}
	public void setEquplecanrea(String equplecanrea) {
		this.equplecanrea = equplecanrea;
	}
	public String getRevdate() {
		return revdate;
	}
	public void setRevdate(String revdate) {
		this.revdate = revdate;
	}
	public String getSugrevreason() {
		return sugrevreason;
	}
	public void setSugrevreason(String sugrevreason) {
		this.sugrevreason = sugrevreason;
	}
	public String getInvaliddate() {
		return invaliddate;
	}
	public void setInvaliddate(String invaliddate) {
		this.invaliddate = invaliddate;
	}
	public String getInvalidrea() {
		return invalidrea;
	}
	public void setInvalidrea(String invalidrea) {
		this.invalidrea = invalidrea;
	}
	public String getPublicdate() {
		return publicdate;
	}
	public void setPublicdate(String publicdate) {
		this.publicdate = publicdate;
	}
	@Override
	public String toString() {
		return "EImPermit [licid=" + licid + ", pripid=" + pripid
				+ ", entname=" + entname + ", regno=" + regno + ", uniscid="
				+ uniscid + ", licno=" + licno + ", licnameCn=" + licnameCn
				+ ", valfrom=" + valfrom + ", valto=" + valto + ", licanth="
				+ licanth + ", licitem=" + licitem + ", type=" + type
				+ ", candate=" + candate + ", equplecanrea=" + equplecanrea
				+ ", revdate=" + revdate + ", sugrevreason=" + sugrevreason
				+ ", invaliddate=" + invaliddate + ", invalidrea=" + invalidrea
				+ ", publicdate=" + publicdate + "]";
	} 
	
}
