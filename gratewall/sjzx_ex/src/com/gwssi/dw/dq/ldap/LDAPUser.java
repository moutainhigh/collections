package com.gwssi.dw.dq.ldap;

public class LDAPUser
{
	private String groupIds;
	private String uid;
	private String cn;
	
	public LDAPUser(String groupIds, String uid, String cn)
	{
		this.groupIds = groupIds;
		this.uid = uid;
		this.cn = cn;
	}

	public String getGroupIds()
	{
		return groupIds;
	}

	public String getUid()
	{
		return uid;
	}

	public String getCn()
	{
		return cn;
	}

}
