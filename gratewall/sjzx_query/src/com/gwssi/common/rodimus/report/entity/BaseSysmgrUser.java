package com.gwssi.common.rodimus.report.entity;

import java.io.Serializable;


/**
 * This is an object that contains data related to the SYSMGR_USER table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="SYSMGR_USER"
 */

public abstract class BaseSysmgrUser  implements Serializable {

	private static final long serialVersionUID = 7272608762384942492L;
	public static String REF = "SysmgrUser";
	public static String PROP_DEPT_ID_FK = "DeptIdFk";
	public static String PROP_SEX = "Sex";
	public static String PROP_LOGIN_NAME = "LoginName";
	public static String PROP_BIRTHDAY = "Birthday";
	public static String PROP_USER_PWD = "UserPwd";
	public static String PROP_MEMO = "Memo";
	public static String PROP_TEL = "Tel";
	public static String PROP_USER_ROLES = "UserRoles";
	public static String PROP_EMAIL = "Email";
	public static String PROP_FLAG = "Flag";
	public static String PROP_ID_CARD = "IdCard";
	public static String PROP_MOBILE = "Mobile";
	public static String PROP_ADMIN_FLAG = "AdminFlag";
	public static String PROP_ORDER_NUM = "OrderNum";
	public static String PROP_USER_NAME = "UserName";
	public static String PROP_ID = "Id";
	public static String PROP_ORG_CODE_FK = "OrgCodeFk";
	
	public static String PROP_STAFF_NO = "StaffNo";
	public static String PROP_SIGN_PIC_URL = "SignPicUrl";
	public static String PROP_CA_CERT_ID = "CaCertId";			//CA֤����
	public static String PROP_CA_CERT_ID_HIS = "CaCertIdHis";   //��������֤��ID
	
	


	// constructors
	public BaseSysmgrUser () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSysmgrUser (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSysmgrUser (
		java.lang.String id,
		java.lang.String orgCodeFk,
		java.lang.String loginName,
		java.lang.String userName,
		java.lang.String userPwd,
		java.lang.String flag,
		java.lang.String adminFlag) {

		this.setId(id);
		this.setOrgCodeFk(orgCodeFk);
		this.setLoginName(loginName);
		this.setUserName(userName);
		this.setUserPwd(userPwd);
		this.setFlag(flag);
		this.setAdminFlag(adminFlag);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String deptIdFk;
	private java.lang.String orgCodeFk;
	private java.lang.String loginName;
	private java.lang.String userName;
	private java.lang.String userPwd;
	private java.lang.String idCard;
	private java.util.Date birthday;
	private java.lang.String sex;
	private java.lang.String tel;
	private java.lang.String mobile;
	private java.lang.String email;
	private java.lang.Integer orderNum;
	private java.lang.String flag;
	private java.lang.String userRoles;
	private java.lang.String adminFlag;
	private java.lang.String memo;

	private java.lang.String staffNo;
	private String signPicUrl;
	private String caCertId;
	private String caCertIdHis;


	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="uuid.hex"
     *  column="USER_ID"
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
	 * Return the value associated with the column: DEPT_ID_FK
	 */
	public java.lang.String getDeptIdFk () {
		return deptIdFk;
	}

	/**
	 * Set the value related to the column: DEPT_ID_FK
	 * @param deptIdFk the DEPT_ID_FK value
	 */
	public void setDeptIdFk (java.lang.String deptIdFk) {
		this.deptIdFk = deptIdFk;
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
	 * Return the value associated with the column: LOGIN_NAME
	 */
	public java.lang.String getLoginName () {
		return loginName;
	}

	/**
	 * Set the value related to the column: LOGIN_NAME
	 * @param loginName the LOGIN_NAME value
	 */
	public void setLoginName (java.lang.String loginName) {
		this.loginName = loginName;
	}



	/**
	 * Return the value associated with the column: USER_NAME
	 */
	public java.lang.String getUserName () {
		return userName;
	}

	/**
	 * Set the value related to the column: USER_NAME
	 * @param userName the USER_NAME value
	 */
	public void setUserName (java.lang.String userName) {
		this.userName = userName;
	}



	/**
	 * Return the value associated with the column: USER_PWD
	 */
	public java.lang.String getUserPwd () {
		return userPwd;
	}

	/**
	 * Set the value related to the column: USER_PWD
	 * @param userPwd the USER_PWD value
	 */
	public void setUserPwd (java.lang.String userPwd) {
		this.userPwd = userPwd;
	}



	/**
	 * Return the value associated with the column: ID_CARD
	 */
	public java.lang.String getIdCard () {
		return idCard;
	}

	/**
	 * Set the value related to the column: ID_CARD
	 * @param idCard the ID_CARD value
	 */
	public void setIdCard (java.lang.String idCard) {
		this.idCard = idCard;
	}



	/**
	 * Return the value associated with the column: BIRTHDAY
	 */
	public java.util.Date getBirthday () {
		return birthday;
	}

	/**
	 * Set the value related to the column: BIRTHDAY
	 * @param birthday the BIRTHDAY value
	 */
	public void setBirthday (java.util.Date birthday) {
		this.birthday = birthday;
	}



	/**
	 * Return the value associated with the column: SEX
	 */
	public java.lang.String getSex () {
		return sex;
	}

	/**
	 * Set the value related to the column: SEX
	 * @param sex the SEX value
	 */
	public void setSex (java.lang.String sex) {
		this.sex = sex;
	}



	/**
	 * Return the value associated with the column: TEL
	 */
	public java.lang.String getTel () {
		return tel;
	}

	/**
	 * Set the value related to the column: TEL
	 * @param tel the TEL value
	 */
	public void setTel (java.lang.String tel) {
		this.tel = tel;
	}



	/**
	 * Return the value associated with the column: MOBILE
	 */
	public java.lang.String getMobile () {
		return mobile;
	}

	/**
	 * Set the value related to the column: MOBILE
	 * @param mobile the MOBILE value
	 */
	public void setMobile (java.lang.String mobile) {
		this.mobile = mobile;
	}



	/**
	 * Return the value associated with the column: EMAIL
	 */
	public java.lang.String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: EMAIL
	 * @param email the EMAIL value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
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
	 * Return the value associated with the column: USER_ROLES
	 */
	public java.lang.String getUserRoles () {
		return userRoles;
	}

	/**
	 * Set the value related to the column: USER_ROLES
	 * @param userRoles the USER_ROLES value
	 */
	public void setUserRoles (java.lang.String userRoles) {
		this.userRoles = userRoles;
	}



	/**
	 * Return the value associated with the column: ADMIN_FLAG
	 */
	public java.lang.String getAdminFlag () {
		return adminFlag;
	}

	/**
	 * Set the value related to the column: ADMIN_FLAG
	 * @param adminFlag the ADMIN_FLAG value
	 */
	public void setAdminFlag (java.lang.String adminFlag) {
		this.adminFlag = adminFlag;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof SysmgrUser)) return false;
		else {
			SysmgrUser sysmgrUser = (SysmgrUser) obj;
			if (null == this.getId() || null == sysmgrUser.getId()) return false;
			else return (this.getId().equals(sysmgrUser.getId()));
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

	public java.lang.String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(java.lang.String staffNo) {
		this.staffNo = staffNo;
	}

	public String getSignPicUrl() {
		return signPicUrl;
	}

	public void setSignPicUrl(String signPicUrl) {
		this.signPicUrl = signPicUrl;
	}

	public String getCaCertId() {
		return caCertId;
	}

	public void setCaCertId(String caCertId) {
		this.caCertId = caCertId;
	}

	public String getCaCertIdHis() {
		return caCertIdHis;
	}

	public void setCaCertIdHis(String caCertIdHis) {
		this.caCertIdHis = caCertIdHis;
	}
	

}