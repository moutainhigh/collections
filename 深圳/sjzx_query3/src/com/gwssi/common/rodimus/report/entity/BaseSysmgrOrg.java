package com.gwssi.common.rodimus.report.entity;

import java.io.Serializable;


/**
 * This is an object that contains data related to the SYSMGR_ORG table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="SYSMGR_ORG"
 */

public abstract class BaseSysmgrOrg  implements Serializable {

	private static final long serialVersionUID = 7272608772384952494L;
	public static String REF = "SysmgrOrg";
	public static String PROP_LKMAN_NAME = "LkmanName";
	public static String PROP_ZIP_CODE = "ZipCode";
	public static String PROP_MEMO = "Memo";
	public static String PROP_ORG_TYPE = "OrgType";
	public static String PROP_ORG_ADD = "OrgAdd";
	public static String PROP_PARENT_ID = "ParentId";
	public static String PROP_SYS_PRINCIPAL = "SysPrincipal";
	public static String PROP_LKMAN_EMAIL = "LkmanEmail";
	public static String PROP_FLAG = "Flag";
	public static String PROP_LAYER = "Layer";
	public static String PROP_ORG_NAME = "OrgName";
	public static String PROP_LKMAN_TEL = "LkmanTel";
	public static String PROP_ORDER_NUM = "OrderNum";
	public static String PROP_ID = "Id";


	// constructors
	public BaseSysmgrOrg () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSysmgrOrg (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSysmgrOrg (
		java.lang.String id,
		java.lang.String orgName,
		java.lang.String orgType,
		java.lang.String flag,
		java.lang.String layer) {

		this.setId(id);
		this.setOrgName(orgName);
		this.setOrgType(orgType);
		this.setFlag(flag);
		this.setLayer(layer);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String flag;
	private java.lang.String layer;
	private java.lang.String lkmanEmail;
	private java.lang.String lkmanName;
	private java.lang.String lkmanTel;
	private java.lang.String memo;
	private java.lang.Integer orderNum;
	private java.lang.String orgAdd;
	private java.lang.String orgName;
	private java.lang.String orgType;
	private java.lang.String parentId;
	private java.lang.String sysPrincipal;
	private java.lang.String zipCode;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="ORG_CODE"
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
	 * Return the value associated with the column: LKMAN_EMAIL
	 */
	public java.lang.String getLkmanEmail () {
		return lkmanEmail;
	}

	/**
	 * Set the value related to the column: LKMAN_EMAIL
	 * @param lkmanEmail the LKMAN_EMAIL value
	 */
	public void setLkmanEmail (java.lang.String lkmanEmail) {
		this.lkmanEmail = lkmanEmail;
	}



	/**
	 * Return the value associated with the column: LKMAN_NAME
	 */
	public java.lang.String getLkmanName () {
		return lkmanName;
	}

	/**
	 * Set the value related to the column: LKMAN_NAME
	 * @param lkmanName the LKMAN_NAME value
	 */
	public void setLkmanName (java.lang.String lkmanName) {
		this.lkmanName = lkmanName;
	}



	/**
	 * Return the value associated with the column: LKMAN_TEL
	 */
	public java.lang.String getLkmanTel () {
		return lkmanTel;
	}

	/**
	 * Set the value related to the column: LKMAN_TEL
	 * @param lkmanTel the LKMAN_TEL value
	 */
	public void setLkmanTel (java.lang.String lkmanTel) {
		this.lkmanTel = lkmanTel;
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
	 * Return the value associated with the column: ORG_ADD
	 */
	public java.lang.String getOrgAdd () {
		return orgAdd;
	}

	/**
	 * Set the value related to the column: ORG_ADD
	 * @param orgAdd the ORG_ADD value
	 */
	public void setOrgAdd (java.lang.String orgAdd) {
		this.orgAdd = orgAdd;
	}



	/**
	 * Return the value associated with the column: ORG_NAME
	 */
	public java.lang.String getOrgName () {
		return orgName;
	}

	/**
	 * Set the value related to the column: ORG_NAME
	 * @param orgName the ORG_NAME value
	 */
	public void setOrgName (java.lang.String orgName) {
		this.orgName = orgName;
	}



	/**
	 * Return the value associated with the column: ORG_TYPE
	 */
	public java.lang.String getOrgType () {
		return orgType;
	}

	/**
	 * Set the value related to the column: ORG_TYPE
	 * @param orgType the ORG_TYPE value
	 */
	public void setOrgType (java.lang.String orgType) {
		this.orgType = orgType;
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



	/**
	 * Return the value associated with the column: SYS_PRINCIPAL
	 */
	public java.lang.String getSysPrincipal () {
		return sysPrincipal;
	}

	/**
	 * Set the value related to the column: SYS_PRINCIPAL
	 * @param sysPrincipal the SYS_PRINCIPAL value
	 */
	public void setSysPrincipal (java.lang.String sysPrincipal) {
		this.sysPrincipal = sysPrincipal;
	}



	/**
	 * Return the value associated with the column: ZIP_CODE
	 */
	public java.lang.String getZipCode () {
		return zipCode;
	}

	/**
	 * Set the value related to the column: ZIP_CODE
	 * @param zipCode the ZIP_CODE value
	 */
	public void setZipCode (java.lang.String zipCode) {
		this.zipCode = zipCode;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof SysmgrOrg)) return false;
		else {
			SysmgrOrg sysmgrOrg = (SysmgrOrg) obj;
			if (null == this.getId() || null == sysmgrOrg.getId()) return false;
			else return (this.getId().equals(sysmgrOrg.getId()));
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