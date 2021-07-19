package com.ye.dc.pojo;

public class User implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String userid;
	private String name;
	private String deptcode;
	private Integer count;
	
	

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

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

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", name=" + name + ", deptcode=" + deptcode + "]";
	}

}
