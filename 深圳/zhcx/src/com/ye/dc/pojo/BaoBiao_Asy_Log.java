package com.ye.dc.pojo;

public class BaoBiao_Asy_Log {

	private String userid;
	private String name;
	private String deptname;
	private String time;
	private String isAdd;
	private String msg;
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

	public String getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Zhcx_Asy_Log [userid=" + userid + ", name=" + name + ", deptname=" + deptname + ", time=" + time
				+ ", isAdd=" + isAdd + ", msg=" + msg + ", count=" + count + "]";
	}

}
