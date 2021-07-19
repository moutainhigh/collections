package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[view_sys_func_count]�����ݶ�����
 * @author Administrator
 *
 */
public class VoViewSysFuncCount extends VoBase
{
	private static final long serialVersionUID = 200907301527540006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_QUERYTIMES = "querytimes" ;		/* ���� */
	public static final String ITEM_QUERY_DATE = "query_date" ;		/* ִ������ */
	public static final String ITEM_FUNC_NAME = "func_name" ;		/* ����ģ�� */
	public static final String ITEM_SJJGID_FK = "sjjgid_fk" ;		/* ����ID */
	public static final String ITEM_FUNC_INDEX = "func_index" ;		/* ��� */
	
	/**
	 * ���캯��
	 */
	public VoViewSysFuncCount()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoViewSysFuncCount(DataBus value)
	{
		super(value);
	}
	
	/* ���� : String */
	public String getQuerytimes()
	{
		return getValue( ITEM_QUERYTIMES );
	}

	public void setQuerytimes( String querytimes1 )
	{
		setValue( ITEM_QUERYTIMES, querytimes1 );
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

	/* ����ģ�� : String */
	public String getFunc_name()
	{
		return getValue( ITEM_FUNC_NAME );
	}

	public void setFunc_name( String func_name1 )
	{
		setValue( ITEM_FUNC_NAME, func_name1 );
	}

	/* ����ID : String */
	public String getSjjgid_fk()
	{
		return getValue( ITEM_SJJGID_FK );
	}

	public void setSjjgid_fk( String sjjgid_fk1 )
	{
		setValue( ITEM_SJJGID_FK, sjjgid_fk1 );
	}

	/* ��� : String */
	public String getFunc_index()
	{
		return getValue( ITEM_FUNC_INDEX );
	}

	public void setFunc_index( String func_index1 )
	{
		setValue( ITEM_FUNC_INDEX, func_index1 );
	}

}

