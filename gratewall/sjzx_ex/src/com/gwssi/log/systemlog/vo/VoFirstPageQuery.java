package com.gwssi.log.systemlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoFirstPageQuery extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_FIRST_PAGE_QUERY_ID = "first_page_query_id";			/* ���� */
	public static final String ITEM_COUNT = "count";			/* ���� */
	public static final String ITEM_NUM = "num";			/* ���� */
	public static final String ITEM_QUERY_DATE = "query_date";			/* �������� */
	public static final String ITEM_QUERY_TIME = "query_time";			/* ����ʱ�� */
	public static final String ITEM_USERNAME = "username";			/* �û��� */
	public static final String ITEM_OPERNAME = "opername";			/* �������� */
	public static final String ITEM_ORGID = "orgid";			/* ��֯ID */
	public static final String ITEM_ORGNAME = "orgname";			/* ��֯���� */
	public static final String ITEM_IPADDRESS = "ipaddress";			/* ip��ַ */
	public static final String ITEM_FIRST_CLS = "first_cls";			/* first_cls */
	public static final String ITEM_SECOND_CLS = "second_cls";			/* second_cls */
	public static final String ITEM_OPERFROM = "operfrom";			/* operfrom */
	public static final String ITEM_CREAT_START_TIME = "creat_start_time";			/* creat_start_time */
	public static final String ITEM_CREAT_END_TIME = "creat_end_time";			/* creat_end_time */

	public VoFirstPageQuery(DataBus value)
	{
		super(value);
	}

	public VoFirstPageQuery()
	{
		super();
	}

	/* ���� */
	public String getFirst_page_query_id()
	{
		return getValue( ITEM_FIRST_PAGE_QUERY_ID );
	}

	public void setFirst_page_query_id( String first_page_query_id1 )
	{
		setValue( ITEM_FIRST_PAGE_QUERY_ID, first_page_query_id1 );
	}

	/* ���� */
	public String getCount()
	{
		return getValue( ITEM_COUNT );
	}

	public void setCount( String count1 )
	{
		setValue( ITEM_COUNT, count1 );
	}

	/* ���� */
	public String getNum()
	{
		return getValue( ITEM_NUM );
	}

	public void setNum( String num1 )
	{
		setValue( ITEM_NUM, num1 );
	}

	/* �������� */
	public String getQuery_date()
	{
		return getValue( ITEM_QUERY_DATE );
	}

	public void setQuery_date( String query_date1 )
	{
		setValue( ITEM_QUERY_DATE, query_date1 );
	}

	/* ����ʱ�� */
	public String getQuery_time()
	{
		return getValue( ITEM_QUERY_TIME );
	}

	public void setQuery_time( String query_time1 )
	{
		setValue( ITEM_QUERY_TIME, query_time1 );
	}

	/* �û��� */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

	/* �������� */
	public String getOpername()
	{
		return getValue( ITEM_OPERNAME );
	}

	public void setOpername( String opername1 )
	{
		setValue( ITEM_OPERNAME, opername1 );
	}

	/* ��֯ID */
	public String getOrgid()
	{
		return getValue( ITEM_ORGID );
	}

	public void setOrgid( String orgid1 )
	{
		setValue( ITEM_ORGID, orgid1 );
	}

	/* ��֯���� */
	public String getOrgname()
	{
		return getValue( ITEM_ORGNAME );
	}

	public void setOrgname( String orgname1 )
	{
		setValue( ITEM_ORGNAME, orgname1 );
	}

	/* ip��ַ */
	public String getIpaddress()
	{
		return getValue( ITEM_IPADDRESS );
	}

	public void setIpaddress( String ipaddress1 )
	{
		setValue( ITEM_IPADDRESS, ipaddress1 );
	}

	/* first_cls */
	public String getFirst_cls()
	{
		return getValue( ITEM_FIRST_CLS );
	}

	public void setFirst_cls( String first_cls1 )
	{
		setValue( ITEM_FIRST_CLS, first_cls1 );
	}

	/* second_cls */
	public String getSecond_cls()
	{
		return getValue( ITEM_SECOND_CLS );
	}

	public void setSecond_cls( String second_cls1 )
	{
		setValue( ITEM_SECOND_CLS, second_cls1 );
	}

	/* operfrom */
	public String getOperfrom()
	{
		return getValue( ITEM_OPERFROM );
	}

	public void setOperfrom( String operfrom1 )
	{
		setValue( ITEM_OPERFROM, operfrom1 );
	}

	/* creat_start_time */
	public String getCreat_start_time()
	{
		return getValue( ITEM_CREAT_START_TIME );
	}

	public void setCreat_start_time( String creat_start_time1 )
	{
		setValue( ITEM_CREAT_START_TIME, creat_start_time1 );
	}

	/* creat_end_time */
	public String getCreat_end_time()
	{
		return getValue( ITEM_CREAT_END_TIME );
	}

	public void setCreat_end_time( String creat_end_time1 )
	{
		setValue( ITEM_CREAT_END_TIME, creat_end_time1 );
	}

}

