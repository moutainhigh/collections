package com.gwssi.ad;

/**
 * AD域组织单位
 * 
 * @author Leezen
 * 
 */
public class Ou {

	/** 名称 **/
	private String name;

	/** 组织名称 **/
	private String ou;

	/** 组织ou **/
	private String distinguishedName; // 如: OU=某部门,DC=adtest,DC=com

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getDistinguishedName() {
		return distinguishedName;
	}

	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}

}
