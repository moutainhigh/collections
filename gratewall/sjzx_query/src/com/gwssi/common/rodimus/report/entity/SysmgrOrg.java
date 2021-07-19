package com.gwssi.common.rodimus.report.entity;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class SysmgrOrg extends BaseSysmgrOrg {
	private static final long serialVersionUID = 1L;


public SysmgrOrg(Map mapSysMgrDept) {
		super();
		this.mapSysMgrDept = mapSysMgrDept;
	}

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public SysmgrOrg () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SysmgrOrg (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SysmgrOrg (
		java.lang.String id,
		java.lang.String orgName,
		java.lang.String orgType,
		java.lang.String flag,
		java.lang.String layer) {

		super (
			id,
			orgName,
			orgType,
			flag,
			layer);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	private Map mapSysMgrDept;

	public Map getMapSysMgrDept() {
		return mapSysMgrDept;
	}

	public void setMapSysMgrDept(Map mapSysMgrDept) {
		this.mapSysMgrDept = mapSysMgrDept;
	}

}