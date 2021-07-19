package com.dj.pojo;

public class Data05 implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String persname;//姓名或企业名称  001
	private String certype;//证件或证照类型 002
	private String cerno;///证件或证找号码 003
	private String post;//职务004
	private String start_date; //任职开始时间 006
	private String end_date; //结束时间 005
	public String getPersname() {
		return persname;
	}
	public void setPersname(String persname) {
		this.persname = persname;
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
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	
}
