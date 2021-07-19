package com.gwssi.log.systemlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[first_page_query]的数据对象类
 * @author Administrator
 *
 */
public class VoFirstPageQuerySystemlog extends VoBase
{
	private static final long serialVersionUID = 201304251426190002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_FIRST_PAGE_QUERY_ID = "first_page_query_id" ;	/* 主键 */
	public static final String ITEM_COUNT = "count" ;				/* 数量 */
	public static final String ITEM_NUM = "num" ;					/* 总数 */
	public static final String ITEM_QUERY_DATE = "query_date" ;		/* 操作日期 */
	public static final String ITEM_QUERY_TIME = "query_time" ;		/* 操作时间 */
	public static final String ITEM_USERNAME = "username" ;			/* 用户名 */
	public static final String ITEM_OPERNAME = "opername" ;			/* 操作名称 */
	public static final String ITEM_ORGID = "orgid" ;				/* 组织ID */
	public static final String ITEM_ORGNAME = "orgname" ;			/* 组织名称 */
	public static final String ITEM_IPADDRESS = "ipaddress" ;		/* ip地址 */
	public static final String ITEM_FIRST_CLS = "first_cls" ;		/* first_cls */
	public static final String ITEM_SECOND_CLS = "second_cls" ;		/* second_cls */
	public static final String ITEM_OPERFROM = "operfrom" ;			/* operfrom */
	
	/**
	 * 构造函数
	 */
	public VoFirstPageQuerySystemlog()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoFirstPageQuerySystemlog(DataBus value)
	{
		super(value);
	}
	
	/* 主键 : String */
	public String getFirst_page_query_id()
	{
		return getValue( ITEM_FIRST_PAGE_QUERY_ID );
	}

	public void setFirst_page_query_id( String first_page_query_id1 )
	{
		setValue( ITEM_FIRST_PAGE_QUERY_ID, first_page_query_id1 );
	}

	/* 数量 : String */
	public String getCount()
	{
		return getValue( ITEM_COUNT );
	}

	public void setCount( String count1 )
	{
		setValue( ITEM_COUNT, count1 );
	}

	/* 总数 : String */
	public String getNum()
	{
		return getValue( ITEM_NUM );
	}

	public void setNum( String num1 )
	{
		setValue( ITEM_NUM, num1 );
	}

	/* 操作日期 : String */
	public String getQuery_date()
	{
		return getValue( ITEM_QUERY_DATE );
	}

	public void setQuery_date( String query_date1 )
	{
		setValue( ITEM_QUERY_DATE, query_date1 );
	}

	/* 操作时间 : String */
	public String getQuery_time()
	{
		return getValue( ITEM_QUERY_TIME );
	}

	public void setQuery_time( String query_time1 )
	{
		setValue( ITEM_QUERY_TIME, query_time1 );
	}

	/* 用户名 : String */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

	/* 操作名称 : String */
	public String getOpername()
	{
		return getValue( ITEM_OPERNAME );
	}

	public void setOpername( String opername1 )
	{
		setValue( ITEM_OPERNAME, opername1 );
	}

	/* 组织ID : String */
	public String getOrgid()
	{
		return getValue( ITEM_ORGID );
	}

	public void setOrgid( String orgid1 )
	{
		setValue( ITEM_ORGID, orgid1 );
	}

	/* 组织名称 : String */
	public String getOrgname()
	{
		return getValue( ITEM_ORGNAME );
	}

	public void setOrgname( String orgname1 )
	{
		setValue( ITEM_ORGNAME, orgname1 );
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

	/* first_cls : String */
	public String getFirst_cls()
	{
		return getValue( ITEM_FIRST_CLS );
	}

	public void setFirst_cls( String first_cls1 )
	{
		setValue( ITEM_FIRST_CLS, first_cls1 );
	}

	/* second_cls : String */
	public String getSecond_cls()
	{
		return getValue( ITEM_SECOND_CLS );
	}

	public void setSecond_cls( String second_cls1 )
	{
		setValue( ITEM_SECOND_CLS, second_cls1 );
	}

	/* operfrom : String */
	public String getOperfrom()
	{
		return getValue( ITEM_OPERFROM );
	}

	public void setOperfrom( String operfrom1 )
	{
		setValue( ITEM_OPERFROM, operfrom1 );
	}

}

