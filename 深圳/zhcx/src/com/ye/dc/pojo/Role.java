package com.ye.dc.pojo;

public class Role implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String userid;
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

}
