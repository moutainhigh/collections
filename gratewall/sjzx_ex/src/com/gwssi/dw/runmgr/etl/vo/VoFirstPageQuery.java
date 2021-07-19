package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoFirstPageQuery extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_FIRST_PAGE_QUERY_ID = "first_page_query_id";			/* 1 */
	public static final String ITEM_FIRST_CLS = "first_cls";			/* 2 */
	public static final String ITEM_SECOND_CLS = "second_cls";			/* 3 */
	public static final String ITEM_COUNT = "count";			/* 4 */
	public static final String ITEM_NUM = "num";			/* 5 */
	public static final String ITEM_QUERY_DATE = "query_date";			/* 6 */
	public static final String ITEM_QUERY_TIME = "query_time";			/* 7 */
	public static final String ITEM_USERNAME = "username";			/* 8 */
	public static final String ITEM_OPERNAME = "opername";			/* 9 */
	public static final String ITEM_ORGID = "orgid";			/* 10 */
	public static final String ITEM_ORGNAME = "orgname";			/* 11 */
	public static final String ITEM_IPADDRESS = "ipaddress";			/* 12 */
	public static final String ITEM_USER_ID= "user_id";	

	public VoFirstPageQuery(DataBus value)
	{
		super(value);
	}

	public VoFirstPageQuery()
	{
		super();
	}

	/* 1 */
	public String getFirst_page_query_id()
	{
		return getValue( ITEM_FIRST_PAGE_QUERY_ID );
	}

	public void setFirst_page_query_id( String first_page_query_id1 )
	{
		setValue( ITEM_FIRST_PAGE_QUERY_ID, first_page_query_id1 );
	}

	/* 2 */
	public String getFirst_cls()
	{
		return getValue( ITEM_FIRST_CLS );
	}

	public void setFirst_cls( String first_cls1 )
	{
		setValue( ITEM_FIRST_CLS, first_cls1 );
	}

	/* 3 */
	public String getSecond_cls()
	{
		return getValue( ITEM_SECOND_CLS );
	}

	public void setSecond_cls( String second_cls1 )
	{
		setValue( ITEM_SECOND_CLS, second_cls1 );
	}

	/* 4 */
	public String getCount()
	{
		return getValue( ITEM_COUNT );
	}

	public void setCount( String count1 )
	{
		setValue( ITEM_COUNT, count1 );
	}

	/* 5 */
	public String getNum()
	{
		return getValue( ITEM_NUM );
	}

	public void setNum( String num1 )
	{
		setValue( ITEM_NUM, num1 );
	}

	/* 6 */
	public String getQuery_date()
	{
		return getValue( ITEM_QUERY_DATE );
	}

	public void setQuery_date( String query_date1 )
	{
		setValue( ITEM_QUERY_DATE, query_date1 );
	}

	/* 7 */
	public String getQuery_time()
	{
		return getValue( ITEM_QUERY_TIME );
	}

	public void setQuery_time( String query_time1 )
	{
		setValue( ITEM_QUERY_TIME, query_time1 );
	}

	/* 8 */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

	/* 9 */
	public String getOpername()
	{
		return getValue( ITEM_OPERNAME );
	}

	public void setOpername( String opername1 )
	{
		setValue( ITEM_OPERNAME, opername1 );
	}

	/* 10 */
	public String getOrgid()
	{
		return getValue( ITEM_ORGID );
	}

	public void setOrgid( String orgid1 )
	{
		setValue( ITEM_ORGID, orgid1 );
	}

	/* 11 */
	public String getOrgname()
	{
		return getValue( ITEM_ORGNAME );
	}

	public void setOrgname( String orgname1 )
	{
		setValue( ITEM_ORGNAME, orgname1 );
	}

	/* 12 */
	public String getIpaddress()
	{
		return getValue( ITEM_IPADDRESS );
	}

	public void setIpaddress( String ipaddress1 )
	{
		setValue( ITEM_IPADDRESS, ipaddress1 );
	}

	public static String getUserId()
	{
		return ITEM_USER_ID;
	}
	
	public  void setUserId(String userId)
	{
		setValue(ITEM_USER_ID, userId);
	}

	

}

