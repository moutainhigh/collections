package com.gwssi.log.systemlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[first_page_query]�����ݶ�����
 * @author Administrator
 *
 */
public class VoFirstPageQuerySystemlogPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304251426190004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_FIRST_PAGE_QUERY_ID = "first_page_query_id" ;	/* ���� */
	
	/**
	 * ���캯��
	 */
	public VoFirstPageQuerySystemlogPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoFirstPageQuerySystemlogPrimaryKey(DataBus value)
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

}

