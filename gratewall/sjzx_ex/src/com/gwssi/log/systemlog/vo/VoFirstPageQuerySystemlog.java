package com.gwssi.log.systemlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[first_page_query]�����ݶ�����
 * @author Administrator
 *
 */
public class VoFirstPageQuerySystemlog extends VoBase
{
	private static final long serialVersionUID = 201304251426190002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_FIRST_PAGE_QUERY_ID = "first_page_query_id" ;	/* ���� */
	public static final String ITEM_COUNT = "count" ;				/* ���� */
	public static final String ITEM_NUM = "num" ;					/* ���� */
	public static final String ITEM_QUERY_DATE = "query_date" ;		/* �������� */
	public static final String ITEM_QUERY_TIME = "query_time" ;		/* ����ʱ�� */
	public static final String ITEM_USERNAME = "username" ;			/* �û��� */
	public static final String ITEM_OPERNAME = "opername" ;			/* �������� */
	public static final String ITEM_ORGID = "orgid" ;				/* ��֯ID */
	public static final String ITEM_ORGNAME = "orgname" ;			/* ��֯���� */
	public static final String ITEM_IPADDRESS = "ipaddress" ;		/* ip��ַ */
	public static final String ITEM_FIRST_CLS = "first_cls" ;		/* first_cls */
	public static final String ITEM_SECOND_CLS = "second_cls" ;		/* second_cls */
	public static final String ITEM_OPERFROM = "operfrom" ;			/* operfrom */
	
	/**
	 * ���캯��
	 */
	public VoFirstPageQuerySystemlog()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoFirstPageQuerySystemlog(DataBus value)
	{
		super(value);
	}
	
	/* ���� : String */
	public String getFirst_page_query_id()
	{
		return getValue( ITEM_FIRST_PAGE_QUERY_ID );
	}

	public void setFirst_page_query_id( String first_page_query_id1 )
	{
		setValue( ITEM_FIRST_PAGE_QUERY_ID, first_page_query_id1 );
	}

	/* ���� : String */
	public String getCount()
	{
		return getValue( ITEM_COUNT );
	}

	public void setCount( String count1 )
	{
		setValue( ITEM_COUNT, count1 );
	}

	/* ���� : String */
	public String getNum()
	{
		return getValue( ITEM_NUM );
	}

	public void setNum( String num1 )
	{
		setValue( ITEM_NUM, num1 );
	}

	/* �������� : String */
	public String getQuery_date()
	{
		return getValue( ITEM_QUERY_DATE );
	}

	public void setQuery_date( String query_date1 )
	{
		setValue( ITEM_QUERY_DATE, query_date1 );
	}

	/* ����ʱ�� : String */
	public String getQuery_time()
	{
		return getValue( ITEM_QUERY_TIME );
	}

	public void setQuery_time( String query_time1 )
	{
		setValue( ITEM_QUERY_TIME, query_time1 );
	}

	/* �û��� : String */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

	/* �������� : String */
	public String getOpername()
	{
		return getValue( ITEM_OPERNAME );
	}

	public void setOpername( String opername1 )
	{
		setValue( ITEM_OPERNAME, opername1 );
	}

	/* ��֯ID : String */
	public String getOrgid()
	{
		return getValue( ITEM_ORGID );
	}

	public void setOrgid( String orgid1 )
	{
		setValue( ITEM_ORGID, orgid1 );
	}

	/* ��֯���� : String */
	public String getOrgname()
	{
		return getValue( ITEM_ORGNAME );
	}

	public void setOrgname( String orgname1 )
	{
		setValue( ITEM_ORGNAME, orgname1 );
	}

	/* ip��ַ : String */
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

