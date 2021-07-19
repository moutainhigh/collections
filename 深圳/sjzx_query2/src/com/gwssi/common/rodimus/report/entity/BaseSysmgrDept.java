package com.gwssi.common.rodimus.report.entity;

import java.io.Serializable;


/**
 * This is an object that contains data related to the SYSMGR_DEPT table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="SYSMGR_DEPT"
 */

public abstract class BaseSysmgrDept  implements Serializable {

	private static final long serialVersionUID = 1L;
	public static String REF = "SysmgrDept";
	public static String PROP_DEPT_PRINCIPAL = "DeptPrincipal";
	public static String PROP_DEPT_NAME = "DeptName";
	public static String PROP_PARENT_ID = "ParentId";
	public static String PROP_FLAG = "Flag";
	public static String PROP_LAYER = "Layer";
	public static String PROP_MEMO = "Memo";
	public static String PROP_ORDER_NUM = "OrderNum";
	public static String PROP_ID = "Id";
	public static String PROP_ORG_CODE_FK = "OrgCodeFk";


	// constructors
	public BaseSysmgrDept () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSysmgrDept (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSysmgrDept (
		java.lang.String id,
		java.lang.String deptName,
		java.lang.String layer,
		java.lang.String flag) {

		this.setId(id);
		this.setDeptName(deptName);
		this.setLayer(layer);
		this.setFlag(flag);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String deptName;
	private java.lang.String deptPrincipal;
	private java.lang.String flag;
	private java.lang.String layer;
	private java.lang.String memo;
	private java.lang.Integer orderNum;
	private java.lang.String orgCodeFk;
	private java.lang.String parentId;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="uuid.hex"
     *  column="DEPT_ID"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: DEPT_NAME
	 */
	public java.lang.String getDeptName () {
		return deptName;
	}

	/**
	 * Set the value related to the column: DEPT_NAME
	 * @param deptName the DEPT_NAME value
	 */
	public void setDeptName (java.lang.String deptName) {
		this.deptName = deptName;
	}



	/**
	 * Return the value associated with the column: DEPT_PRINCIPAL
	 */
	public java.lang.String getDeptPrincipal () {
		return deptPrincipal;
	}

	/**
	 * Set the value related to the column: DEPT_PRINCIPAL
	 * @param deptPrincipal the DEPT_PRINCIPAL value
	 */
	public void setDeptPrincipal (java.lang.String deptPrincipal) {
		this.deptPrincipal = deptPrincipal;
	}



	/**
	 * Return the value associated with the column: FLAG
	 */
	public java.lang.String getFlag () {
		return flag;
	}

	/**
	 * Set the value related to the column: FLAG
	 * @param flag the FLAG value
	 */
	public void setFlag (java.lang.String flag) {
		this.flag = flag;
	}



	/**
	 * Return the value associated with the column: LAYER
	 */
	public java.lang.String getLayer () {
		return layer;
	}

	/**
	 * Set the value related to the column: LAYER
	 * @param layer the LAYER value
	 */
	public void setLayer (java.lang.String layer) {
		this.layer = layer;
	}



	/**
	 * Return the value associated with the column: MEMO
	 */
	public java.lang.String getMemo () {
		return memo;
	}

	/**
	 * Set the value related to the column: MEMO
	 * @param memo the MEMO value
	 */
	public void setMemo (java.lang.String memo) {
		this.memo = memo;
	}



	/**
	 * Return the value associated with the column: ORDER_NUM
	 */
	public java.lang.Integer getOrderNum () {
		return orderNum;
	}

	/**
	 * Set the value related to the column: ORDER_NUM
	 * @param orderNum the ORDER_NUM value
	 */
	public void setOrderNum (java.lang.Integer orderNum) {
		this.orderNum = orderNum;
	}



	/**
	 * Return the value associated with the column: ORG_CODE_FK
	 */
	public java.lang.String getOrgCodeFk () {
		return orgCodeFk;
	}

	/**
	 * Set the value related to the column: ORG_CODE_FK
	 * @param orgCodeFk the ORG_CODE_FK value
	 */
	public void setOrgCodeFk (java.lang.String orgCodeFk) {
		this.orgCodeFk = orgCodeFk;
	}



	/**
	 * Return the value associated with the column: PARENT_ID
	 */
	public java.lang.String getParentId () {
		return parentId;
	}

	/**
	 * Set the value related to the column: PARENT_ID
	 * @param parentId the PARENT_ID value
	 */
	public void setParentId (java.lang.String parentId) {
		this.parentId = parentId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof SysmgrDept)) return false;
		else {
			SysmgrDept sysmgrDept = (SysmgrDept) obj;
			if (null == this.getId() || null == sysmgrDept.getId()) return false;
			else return (this.getId().equals(sysmgrDept.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}