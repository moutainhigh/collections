package com.gwssi.upload.pojo;

/**
 * 许可信息
 * @author Administrator
 *
 */
public class ELicCertifica {

	private String licid; 
	private String pripid; 
	private String licno; 
	private String licname; 
	private String valfrom; 
	private String valto; 
	private String licanth;
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
	public String getLicno() {
		return licno;
	}
	public void setLicno(String licno) {
		this.licno = licno;
	}
	public String getLicname() {
		return licname;
	}
	public void setLicname(String licname) {
		this.licname = licname;
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
	@Override
	public String toString() {
		return "ELicCertifica [licid=" + licid + ", pripid=" + pripid
				+ ", licno=" + licno + ", licname=" + licname + ", valfrom="
				+ valfrom + ", valto=" + valto + ", licanth=" + licanth + "]";
	} 
	
}
