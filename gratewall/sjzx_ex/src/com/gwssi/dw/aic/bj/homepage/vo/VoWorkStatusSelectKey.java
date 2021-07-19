package com.gwssi.dw.aic.bj.homepage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[work_status]�����ݶ�����
 * @author Administrator
 *
 */
public class VoWorkStatusSelectKey extends VoBase
{
	private static final long serialVersionUID = 200812041106360003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SCOUR_TABLE_NAME = "scour_table_name" ;	/* Դ���� */
	public static final String ITEM_SCOUR_TABLE_KEY_COL = "scour_table_key_col" ;	/* Դ������ID */
	public static final String ITEM_WORK_STATUS_NAME = "work_status_name" ;	/* ������������ */
	
	/**
	 * ���캯��
	 */
	public VoWorkStatusSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoWorkStatusSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* Դ���� : String */
	public String getScour_table_name()
	{
		return getValue( ITEM_SCOUR_TABLE_NAME );
	}

	public void setScour_table_name( String scour_table_name1 )
	{
		setValue( ITEM_SCOUR_TABLE_NAME, scour_table_name1 );
	}

	/* Դ������ID : String */
	public String getScour_table_key_col()
	{
		return getValue( ITEM_SCOUR_TABLE_KEY_COL );
	}

	public void setScour_table_key_col( String scour_table_key_col1 )
	{
		setValue( ITEM_SCOUR_TABLE_KEY_COL, scour_table_key_col1 );
	}

	/* ������������ : String */
	public String getWork_status_name()
	{
		return getValue( ITEM_WORK_STATUS_NAME );
	}

	public void setWork_status_name( String work_status_name1 )
	{
		setValue( ITEM_WORK_STATUS_NAME, work_status_name1 );
	}

}

