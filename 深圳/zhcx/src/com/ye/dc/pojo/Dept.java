package com.ye.dc.pojo;

public class Dept implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code;
	private String deptname;
	private String parentCode;
	
	
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	@Override
	public String toString() {
		return "Dept [code=" + code + ", deptname=" + deptname + "]";
	}

	
	
}
