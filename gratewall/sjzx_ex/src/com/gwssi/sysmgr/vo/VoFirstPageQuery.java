package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[first_page_query]的数据对象类
 * @author Administrator
 *
 */
public class VoFirstPageQuery extends VoBase
{
	private static final long serialVersionUID = 201003310853130002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_FIRST_PAGE_QUERY_ID = "first_page_query_id" ;	/* first_page_query_id */
	public static final String ITEM_FIRST_CLS = "first_cls" ;		/* first_cls */
	public static final String ITEM_SECOND_CLS = "second_cls" ;		/* second_cls */
	public static final String ITEM_COUNT = "count" ;				/* count */
	public static final String ITEM_NUM = "num" ;					/* num */
	public static final String ITEM_QUERY_DATE = "query_date" ;		/* query_date */
	public static final String ITEM_QUERY_TIME = "query_time" ;		/* query_time */
	public static final String ITEM_USERNAME = "username" ;			/* username */
	public static final String ITEM_OPERNAME = "opername" ;			/* opername */
	public static final String ITEM_ORGID = "orgid" ;				/* orgid */
	public static final String ITEM_ORGNAME = "orgname" ;			/* orgname */
	public static final String ITEM_IPADDRESS = "ipaddress" ;		/* ipaddress */
	
	/**
	 * 构造函数
	 */
	public VoFirstPageQuery()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoFirstPageQuery(DataBus value)
	{
		super(value);
	}
	
	/* first_page_query_id : String */
	public String getFirst_page_query_id()
	{
		return getValue( ITEM_FIRST_PAGE_QUERY_ID );
	}

	public void setFirst_page_query_id( String first_page_query_id1 )
	{
		setValue( ITEM_FIRST_PAGE_QUERY_ID, first_page_query_id1 );
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

	/* count : String */
	public String getCount()
	{
		return getValue( ITEM_COUNT );
	}

	public void setCount( String count1 )
	{
		setValue( ITEM_COUNT, count1 );
	}

	/* num : String */
	public String getNum()
	{
		return getValue( ITEM_NUM );
	}

	public void setNum( String num1 )
	{
		setValue( ITEM_NUM, num1 );
	}

	/* query_date : String */
	public String getQuery_date()
	{
		return getValue( ITEM_QUERY_DATE );
	}

	public void setQuery_date( String query_date1 )
	{
		setValue( ITEM_QUERY_DATE, query_date1 );
	}

	/* query_time : String */
	public String getQuery_time()
	{
		return getValue( ITEM_QUERY_TIME );
	}

	public void setQuery_time( String query_time1 )
	{
		setValue( ITEM_QUERY_TIME, query_time1 );
	}

	/* username : String */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

	/* opername : String */
	public String getOpername()
	{
		return getValue( ITEM_OPERNAME );
	}

	public void setOpername( String opername1 )
	{
		setValue( ITEM_OPERNAME, opername1 );
	}

	/* orgid : String */
	public String getOrgid()
	{
		return getValue( ITEM_ORGID );
	}

	public void setOrgid( String orgid1 )
	{
		setValue( ITEM_ORGID, orgid1 );
	}

	/* orgname : String */
	public String getOrgname()
	{
		return getValue( ITEM_ORGNAME );
	}

	public void setOrgname( String orgname1 )
	{
		setValue( ITEM_ORGNAME, orgname1 );
	}

	/* ipaddress : String */
	public String getIpaddress()
	{
		return getValue( ITEM_IPADDRESS );
	}

	public void setIpaddress( String ipaddress1 )
	{
		setValue( ITEM_IPADDRESS, ipaddress1 );
	}

}

