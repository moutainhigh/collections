package com.ye.from.pojo;

public class ZHCXUserHy implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String userid;
	private String name;
	private String deptname;
	private String time;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "UserHy [userid=" + userid + ", name=" + name + ", deptname=" + deptname + ", time=" + time + "]";
	}

}
