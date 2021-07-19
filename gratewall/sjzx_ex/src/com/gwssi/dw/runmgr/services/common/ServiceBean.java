package com.gwssi.dw.runmgr.services.common;


public class ServiceBean
{
	private String id;
	private String code;
	private String name;
	private String dczd_dm;
	private String table_no;
	private String shared_columns;
	private String param_columns;
	private String create_date;
	private String create_by;
	private String desc;
	private int max_records;
	private String type;
	private String url;
	private ColumnBean[] sharedColumns;
	private ColumnBean[] paramColumns;
	
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public String getCreate_by()
	{
		return create_by;
	}
	public void setCreate_by(String create_by)
	{
		this.create_by = create_by;
	}
	public String getCreate_date()
	{
		return create_date;
	}
	public void setCreate_date(String create_date)
	{
		this.create_date = create_date;
	}
	public String getDczd_dm()
	{
		return dczd_dm;
	}
	public void setDczd_dm(String dczd_dm)
	{
		this.dczd_dm = dczd_dm;
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
	public int getMax_records()
	{
		return max_records;
	}
	public void setMax_records(int max_records)
	{
		this.max_records = max_records;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getTable_no()
	{
		return table_no;
	}
	public void setTable_no(String table_no)
	{
		this.table_no = table_no;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getParam_columns()
	{
		return param_columns;
	}
	public void setParam_columns(String param_columns)
	{
		this.param_columns = param_columns;
	}
	public String getShared_columns()
	{
		return shared_columns;
	}
	public void setShared_columns(String shared_columns)
	{
		this.shared_columns = shared_columns;
	}
	public ColumnBean[] getParamColumns()
	{
		return paramColumns;
	}
	public void setParamColumns(ColumnBean[] paramColumns)
	{
		this.paramColumns = paramColumns;
	}
	public ColumnBean[] getSharedColumns()
	{
		return sharedColumns;
	}
	public void setSharedColumns(ColumnBean[] sharedColumns)
	{
		this.sharedColumns = sharedColumns;
	}
}
