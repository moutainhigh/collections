package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_table_semantic]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysTableSemantic extends VoBase
{
	private static final long serialVersionUID = 200804181523520006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* ҵ������ */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* ҵ����� */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* ҵ��������� */
	public static final String ITEM_TABLE_ORDER = "table_order" ;	/* ����˳�� */
	public static final String ITEM_DEMO = "demo" ;					/* ��ע */
	public static final String ITEM_SYS_ID = "sys_id" ;				/* ҵ��ϵͳ���� */
	
	/**
	 * ���캯��
	 */
	public VoSysTableSemantic()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysTableSemantic(DataBus value)
	{
		super(value);
	}
	
	/* ҵ������ : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
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

	/* ����˳�� : String */
	public String getTable_order()
	{
		return getValue( ITEM_TABLE_ORDER );
	}

	public void setTable_order( String table_order1 )
	{
		setValue( ITEM_TABLE_ORDER, table_order1 );
	}

	/* ��ע : String */
	public String getDemo()
	{
		return getValue( ITEM_DEMO );
	}

	public void setDemo( String demo1 )
	{
		setValue( ITEM_DEMO, demo1 );
	}

	/* ҵ��ϵͳ���� : String */
	public String getSys_id()
	{
		return getValue( ITEM_SYS_ID );
	}

	public void setSys_no( String sys_id1 )
	{
		setValue( ITEM_SYS_ID, sys_id1 );
	}

}

