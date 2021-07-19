package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[view_sys_func_count]�����ݶ�����
 * @author Administrator
 *
 */
public class VoViewSysFuncCountSelectKey extends VoBase
{
	private static final long serialVersionUID = 200907301527540007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_QUERY_DATE = "query_date" ;		/* ִ������ */
	
	/**
	 * ���캯��
	 */
	public VoViewSysFuncCountSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoViewSysFuncCountSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ִ������ : String */
	public String getQuery_date()
	{
		return getValue( ITEM_QUERY_DATE );
	}

	public void setQuery_date( String query_date1 )
	{
		setValue( ITEM_QUERY_DATE, query_date1 );
	}

}

