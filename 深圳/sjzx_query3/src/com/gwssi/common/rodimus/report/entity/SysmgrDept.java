package com.gwssi.common.rodimus.report.entity;

import java.util.Map;


public class SysmgrDept extends BaseSysmgrDept {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SysmgrDept () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SysmgrDept (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SysmgrDept (
		java.lang.String id,
		java.lang.String deptName,
		java.lang.String layer,
		java.lang.String flag) {

		super (
			id,
			deptName,
			layer,
			flag);
	}

/*[CONSTRUCTOR MARKER END]*/
	@SuppressWarnings("rawtypes")
	private Map mapSysMgrUser;

	@SuppressWarnings("rawtypes")
	public Map getMapSysMgrUser() {
		return mapSysMgrUser;
	}

	@SuppressWarnings("rawtypes")
	public void setMapSysMgrUser(Map mapSysMgrUser) {
		this.mapSysMgrUser = mapSysMgrUser;
	}



}