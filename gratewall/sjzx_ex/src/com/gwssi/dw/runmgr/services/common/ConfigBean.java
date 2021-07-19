package com.gwssi.dw.runmgr.services.common;


public class ConfigBean
{
	private String id;
	private ServiceBean service;
	private String userId;
	private String date;
	private String by;
	private ColumnBean[] permit_column;
	private String tableName;
	
	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public ColumnBean[] getPermit_column()
	{
		return permit_column;
	}

	public void setPermit_column(ColumnBean[] permit_column)
	{
		this.permit_column = permit_column;
	}

	public String getBy()
	{
		return by;
	}

	public void setBy(String by)
	{
		this.by = by;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public ServiceBean getService()
	{
		return service;
	}

	public void setService(ServiceBean service)
	{
		this.service = service;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	
	
}
