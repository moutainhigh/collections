package com.gwssi.report.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * 结构参考ODS数据库(dc_code)
 * @author wuyincheng ,Oct 14, 2016
 */
@Entity
@Table(name = "DC_CODE.TCDEPT")
public class DeptInfo extends AbsDaoBussinessObject {

	private String code;
	private String deptName;
	private int valid;
	private String abbr;
	private String fullName;
	private int deptType;
	private String upperdept;
	private String addr;
	private String phone;
	
	public DeptInfo() {
		// TODO Auto-generated constructor stub
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		markChange("code", code);
	}

	@Column(name = "deptName")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
		markChange("deptName", deptName);
	}

	@Column(name = "valid")
	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
		markChange("valid", valid);
	}

	@Column(name = "abbr")
	public String getAbbr() {
		return abbr;
	}
	
	public void setAbbr(String abbr) {
		this.abbr = abbr;
		markChange("abbr", abbr);
	}

	@Column(name = "fullName")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
		markChange("fullName", fullName);
	}

	@Column(name = "deptType")
	public int getDeptType() {
		return deptType;
	}

	public void setDeptType(int deptType) {
		this.deptType = deptType;
		markChange("deptType", deptType);
	}

	@Column(name = "upperdept")
	public String getUpperdept() {
		return upperdept;
	}

	public void setUpperdept(String upperdept) {
		this.upperdept = upperdept;
		markChange("upperdept", upperdept);
	}

	@Column(name = "addr")
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
		markChange("addr", addr);
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
		markChange("phone", phone);
	}
}
