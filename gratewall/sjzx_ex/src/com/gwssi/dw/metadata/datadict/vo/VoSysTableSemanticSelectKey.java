package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_table_semantic]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysTableSemanticSelectKey extends VoBase
{
	private static final long serialVersionUID = 200804181523530007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_ID = "sys_id" ;				/* ҵ��ϵͳ���� */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* ҵ����� */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* ҵ��������� */
	
	/**
	 * ���캯��
	 */
	public VoSysTableSemanticSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysTableSemanticSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ҵ��ϵͳ���� : String */
	public String getSys_id()
	{
		return getValue( ITEM_SYS_ID );
	}

	public void setSys_id( String sys_id1 )
	{
		setValue( ITEM_SYS_ID, sys_id1 );
	}

	/* ҵ����� : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* ҵ��������� : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

}

