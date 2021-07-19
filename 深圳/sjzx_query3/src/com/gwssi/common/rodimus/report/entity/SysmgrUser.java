package com.gwssi.common.rodimus.report.entity;

import java.util.Map;


@SuppressWarnings("rawtypes")
public class SysmgrUser extends BaseSysmgrUser {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SysmgrUser () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SysmgrUser (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SysmgrUser (
		java.lang.String id,
		java.lang.String orgCodeFk,
		java.lang.String loginName,
		java.lang.String userName,
		java.lang.String userPwd,
		java.lang.String flag,
		java.lang.String adminFlag) {

		super (
			id,
			orgCodeFk,
			loginName,
			userName,
			userPwd,
			flag,
			adminFlag);
	}

/*[CONSTRUCTOR MARKER END]*/


	public SysmgrOrg org;
	public SysmgrDept dept;
	public Map roleMap;

	public Map getRoleMap() {
		return roleMap;
	}

	public void setRoleMap(Map roleMap) {
		this.roleMap = roleMap;
	}

	public SysmgrDept getDept() {
		return dept;
	}

	public void setDept(SysmgrDept dept) {
		this.dept = dept;
	}

	public SysmgrOrg getOrg() {
		return org;
	}

	public void setOrg(SysmgrOrg org) {
		this.org = org;
	}

	
	
	
}