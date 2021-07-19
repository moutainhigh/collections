package com.gwssi.dw.runmgr.services.common;

public class ColumnBean
{
	private String id;
	private String name;
	private String desc;
	private String value;
	private String type;
	
	public ColumnBean()
	{
	}
	
	public ColumnBean(String name, String desc, String type)
	{
		this.name = name;
		this.desc = desc;
		this.type = type;
	}
	
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	public String getDesc()
	{
		return desc;
	}
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name.toUpperCase();
	}
	
	public String toString(){
		return id + " = "+name;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
}
