package com.gwssi.ad;

/**
 * AD域用户
 * 
 * @author Leezen
 * 
 */
public class Person {
	/** 姓 **/
	private String sn;

	/** 名 **/
	private String giveName;

	/** 面板显示名称 **/
	private String cn;

	/** 显示姓名 **/
	private String displayName;

	/** 用户登录名 **/
	private String sAMAccountName;

	/** 登录密码 **/
	private String userPassword;

	/** 域名全称 **/
	private String userPrincipalName; // 如: zhangsan@adtest.com

	/** 用户组织单位 **/
	private String ou;

	/** 用户CN **/
	private String distinguishedName; // 如: CN=张三,OU=某部门,DC=adtest,DC=com

	public String getsAMAccountName() {
		return sAMAccountName;
	}

	public void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}

	public String getUserPrincipalName() {
		return userPrincipalName;
	}

	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getGiveName() {
		return giveName;
	}

	public void setGiveName(String giveName) {
		this.giveName = giveName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDistinguishedName() {
		return distinguishedName;
	}

	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	@Override
	public String toString() {
		return "Person [sn=" + sn + ", giveName=" + giveName + ", cn=" + cn + ", displayName=" + displayName
				+ ", sAMAccountName=" + sAMAccountName + ", userPassword=" + userPassword + ", userPrincipalName="
				+ userPrincipalName + ", ou=" + ou + ", distinguishedName=" + distinguishedName + "]";
	}

}
