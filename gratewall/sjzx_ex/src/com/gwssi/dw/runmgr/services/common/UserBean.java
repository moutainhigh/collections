package com.gwssi.dw.runmgr.services.common;

public class UserBean
{
	private String id;
	private String name;
	private String pwd;
	private String type;
	private String createDate;
	private String createBy;
	private String desc;
	private String state;
	
	//DC2-jufeng
	private String is_ip_bind;
	private String ip_bind;
	private String is_limit;
	private String limit_services;
	//private String 
	private String is_week_limit;
	private String is_time_limit;
	private String is_number_limit;
	private String is_total_limit;
	
	private String lock_time;
	private String lock_reason;
	private String lock_reason_desp;
	
	
	public String getCreateBy()
	{
		return createBy;
	}
	public void setCreateBy(String createBy)
	{
		this.createBy = createBy;
	}
	public String getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
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
		this.name = name;
	}
	public String getPwd()
	{
		return pwd;
	}
	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getIs_ip_bind()
	{
		return is_ip_bind;
	}
	public void setIs_ip_bind(String is_ip_bind)
	{
		this.is_ip_bind = is_ip_bind;
	}
	public String getIs_week_limit()
	{
		return is_week_limit;
	}
	public void setIs_week_limit(String is_week_limit)
	{
		this.is_week_limit = is_week_limit;
	}
	public String getIs_time_limit()
	{
		return is_time_limit;
	}
	public void setIs_time_limit(String is_time_limit)
	{
		this.is_time_limit = is_time_limit;
	}
	public String getIs_number_limit()
	{
		return is_number_limit;
	}
	public void setIs_number_limit(String is_number_limit)
	{
		this.is_number_limit = is_number_limit;
	}
	public String getIs_total_limit()
	{
		return is_total_limit;
	}
	public void setIs_total_limit(String is_total_limit)
	{
		this.is_total_limit = is_total_limit;
	}
	public String getIp_bind()
	{
		return ip_bind;
	}
	public void setIp_bind(String ip_bind)
	{
		this.ip_bind = ip_bind;
	}
	public String getLock_time()
	{
		return lock_time;
	}
	public void setLock_time(String lock_time)
	{
		this.lock_time = lock_time;
	}
	public String getLock_reason()
	{
		return lock_reason;
	}
	public void setLock_reason(String lock_reason)
	{
		this.lock_reason = lock_reason;
	}
	public String getLock_reason_desp()
	{
		return lock_reason_desp;
	}
	public void setLock_reason_desp(String lock_reason_desp)
	{
		this.lock_reason_desp = lock_reason_desp;
	}
	public String getIs_limit()
	{
		return is_limit;
	}
	public void setIs_limit(String is_limit)
	{
		this.is_limit = is_limit;
	}
	public String getLimit_services()
	{
		return limit_services;
	}
	public void setLimit_services(String limit_services)
	{
		this.limit_services = limit_services;
	}
}	
