package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[first_page_query]�����ݶ�����
 * @author Administrator
 *
 */
public class VoFirstPageQueryPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201003310853150004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_FIRST_PAGE_QUERY_ID = "first_page_query_id" ;	/* first_page_query_id */
	
	/**
	 * ���캯��
	 */
	public VoFirstPageQueryPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoFirstPageQueryPrimaryKey(DataBus value)
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

}

