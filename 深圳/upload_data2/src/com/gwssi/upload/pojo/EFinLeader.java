package com.gwssi.upload.pojo;

/**
 * 财务负责人信息
 * @author Administrator
 *
 */
public class EFinLeader {
	
	private String fpid; 
	private String pripid; 
	private String name; 
	private String certype; 
	private String cerno; 
	private String tel; 
	private String mobtel; 
	private String email;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobtel() {
		return mobtel;
	}
	public void setMobtel(String mobtel) {
		this.mobtel = mobtel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFpid() {
		return fpid;
	}
	public void setFpid(String fpid) {
		this.fpid = fpid;
	}
	@Override
	public String toString() {
		return "EFinLeader [fpid=" + fpid + ", pripid=" + pripid + ", name="
				+ name + ", certype=" + certype + ", cerno=" + cerno + ", tel="
				+ tel + ", mobtel=" + mobtel + ", email=" + email + "]";
	}
	
	

}
