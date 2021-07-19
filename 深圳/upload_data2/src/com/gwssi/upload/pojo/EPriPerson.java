package com.gwssi.upload.pojo;

/**
 * 主要人员信息
 * @author Administrator
 *
 */
public class EPriPerson {
	
	private String personid; 
	private String pripid; 
	private String name; 
	private String sex; 
	private String natdate; 
	private String certype; 
	private String cerno; 
	private String position; 
	private String positionCn; 
	private String posbrform; 
	private String occstbeapp; 
	private String lerepsign; 
	private String appounit; 
	private String tel; 
	private String country; 
	private String telnumber; 
	private String mobtel; 
	private String email; 
	private String houseadd; 
	private String arrchdate; 
	private String repcarfrom; 
	private String repcarto; 
	private String postalcode;
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid = personid;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNatdate() {
		return natdate;
	}
	public void setNatdate(String natdate) {
		this.natdate = natdate;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPositionCn() {
		return positionCn;
	}
	public void setPositionCn(String positionCn) {
		this.positionCn = positionCn;
	}
	public String getPosbrform() {
		return posbrform;
	}
	public void setPosbrform(String posbrform) {
		this.posbrform = posbrform;
	}
	public String getOccstbeapp() {
		return occstbeapp;
	}
	public void setOccstbeapp(String occstbeapp) {
		this.occstbeapp = occstbeapp;
	}
	public String getLerepsign() {
		return lerepsign;
	}
	public void setLerepsign(String lerepsign) {
		this.lerepsign = lerepsign;
	}
	public String getAppounit() {
		return appounit;
	}
	public void setAppounit(String appounit) {
		this.appounit = appounit;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTelnumber() {
		return telnumber;
	}
	public void setTelnumber(String telnumber) {
		this.telnumber = telnumber;
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
	public String getHouseadd() {
		return houseadd;
	}
	public void setHouseadd(String houseadd) {
		this.houseadd = houseadd;
	}
	public String getArrchdate() {
		return arrchdate;
	}
	public void setArrchdate(String arrchdate) {
		this.arrchdate = arrchdate;
	}
	public String getRepcarfrom() {
		return repcarfrom;
	}
	public void setRepcarfrom(String repcarfrom) {
		this.repcarfrom = repcarfrom;
	}
	public String getRepcarto() {
		return repcarto;
	}
	public void setRepcarto(String repcarto) {
		this.repcarto = repcarto;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	@Override
	public String toString() {
		return "EPriPerson [personid=" + personid + ", pripid=" + pripid
				+ ", name=" + name + ", sex=" + sex + ", natdate=" + natdate
				+ ", certype=" + certype + ", cerno=" + cerno + ", position="
				+ position + ", positionCn=" + positionCn + ", posbrform="
				+ posbrform + ", occstbeapp=" + occstbeapp + ", lerepsign="
				+ lerepsign + ", appounit=" + appounit + ", tel=" + tel
				+ ", country=" + country + ", telnumber=" + telnumber
				+ ", mobtel=" + mobtel + ", email=" + email + ", houseadd="
				+ houseadd + ", arrchdate=" + arrchdate + ", repcarfrom="
				+ repcarfrom + ", repcarto=" + repcarto + ", postalcode="
				+ postalcode + "]";
	} 

}
