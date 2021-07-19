package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[view_sys_app_log_query]的数据对象类
 * @author Administrator
 *
 */
public class VoViewSysAppLogQuery extends VoBase
{
	private static final long serialVersionUID = 201208010743380002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_USERNAME = "username" ;			/* 用户姓名 */
	public static final String ITEM_QUERY_TIME = "query_time" ;		/* 查询时间 */
	public static final String ITEM_ORGID = "orgid" ;				/* 机构id */
	public static final String ITEM_IPADDRESS = "ipaddress" ;		/* ip地址 */
	public static final String ITEM_TYPE = "type" ;					/* 类型 */
	
	/**
	 * 构造函数
	 */
	public VoViewSysAppLogQuery()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoViewSysAppLogQuery(DataBus value)
	{
		super(value);
	}
	
	/* 用户姓名 : String */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

	/* 查询时间 : String */
	public String getQuery_time()
	{
		return getValue( ITEM_QUERY_TIME );
	}

	public void setQuery_time( String query_time1 )
	{
		setValue( ITEM_QUERY_TIME, query_time1 );
	}

	/* 机构id : String */
	public String getOrgid()
	{
		return getValue( ITEM_ORGID );
	}

	public void setOrgid( String orgid1 )
	{
		setValue( ITEM_ORGID, orgid1 );
	}

	/* ip地址 : String */
	public String getIpaddress()
	{
		return getValue( ITEM_IPADDRESS );
	}

	public void setIpaddress( String ipaddress1 )
	{
		setValue( ITEM_IPADDRESS, ipaddress1 );
	}

	/* 类型 : String */
	public String getType()
	{
		return getValue( ITEM_TYPE );
	}

	public void setType( String type1 )
	{
		setValue( ITEM_TYPE, type1 );
	}

}

