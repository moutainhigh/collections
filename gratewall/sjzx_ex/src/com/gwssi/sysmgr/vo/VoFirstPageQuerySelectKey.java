package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[first_page_query]�����ݶ�����
 * @author Administrator
 *
 */
public class VoFirstPageQuerySelectKey extends VoBase
{
	private static final long serialVersionUID = 201003310853140003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_QUERY_DATE = "query_date" ;		/* query_date */
	public static final String ITEM_OPERNAME = "opername" ;			/* opername */
	public static final String ITEM_ORGID = "orgid" ;				/* orgid */
	
	/**
	 * ���캯��
	 */
	public VoFirstPageQuerySelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
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

