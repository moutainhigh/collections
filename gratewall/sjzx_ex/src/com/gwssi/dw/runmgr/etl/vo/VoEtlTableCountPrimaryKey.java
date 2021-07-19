package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[etl_table_count]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEtlTableCountPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200810301721050004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_NAME = "sys_name" ;			/* �������� */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* ���� */
	public static final String ITEM_ETL_DATE = "etl_date" ;			/* ͳ������ */
	
	/**
	 * ���캯��
	 */
	public VoEtlTableCountPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEtlTableCountPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �������� : String */
	public String getSys_name()
	{
		return getValue( ITEM_SYS_NAME );
	}

	public void setSys_name( String sys_name1 )
	{
		setValue( ITEM_SYS_NAME, sys_name1 );
	}

	/* ���� : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* ͳ������ : String */
	public String getEtl_date()
	{
		return getValue( ITEM_ETL_DATE );
	}

	public void setEtl_date( String etl_date1 )
	{
		setValue( ITEM_ETL_DATE, etl_date1 );
	}

}

