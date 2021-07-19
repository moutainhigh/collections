package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[first_page_query]的数据对象类
 * @author Administrator
 *
 */
public class VoFirstPageQuerySelectKey extends VoBase
{
	private static final long serialVersionUID = 201003310853140003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_QUERY_DATE = "query_date" ;		/* query_date */
	public static final String ITEM_OPERNAME = "opername" ;			/* opername */
	public static final String ITEM_ORGID = "orgid" ;				/* orgid */
	
	/**
	 * 构造函数
	 */
	public VoFirstPageQuerySelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoFirstPageQuerySelectKey(DataBus value)
	{
		super(value);
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

}

