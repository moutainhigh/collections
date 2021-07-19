package com.ye.dc.pojo;

public class BaoBiaoUser implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String yhid_pk;
	private String yhzh;
	private String yhxm;
	private String department_code;
	private String department_parent_code;
	private Integer count;

	public String getYhid_pk() {
		return yhid_pk;
	}

	public void setYhid_pk(String yhid_pk) {
		this.yhid_pk = yhid_pk;
	}

	public String getYhzh() {
		return yhzh;
	}

	public void setYhzh(String yhzh) {
		this.yhzh = yhzh;
	}

	public String getYhxm() {
		return yhxm;
	}

	public void setYhxm(String yhxm) {
		this.yhxm = yhxm;
	}

	public String getDepartment_code() {
		return department_code;
	}

	public void setDepartment_code(String department_code) {
		this.department_code = department_code;
	}

	public String getDepartment_parent_code() {
		return department_parent_code;
	}

	public void setDepartment_parent_code(String department_parent_code) {
		this.department_parent_code = department_parent_code;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
