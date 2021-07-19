package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_column_semantic]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysColumnSemanticSelectKey extends VoBase
{
	private static final long serialVersionUID = 200804181524160011L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* ҵ������ */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* ҵ���ֶ��� */
	public static final String ITEM_COLUMN_NAME_CN = "column_name_cn" ;	/* ҵ���ֶ������� */
	
	/**
	 * ���캯��
	 */
	public VoSysColumnSemanticSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysColumnSemanticSelectKey(DataBus value)
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

	/* ҵ���ֶ��� : String */
	public String getColumn_name()
	{
		return getValue( ITEM_COLUMN_NAME );
	}

	public void setColumn_name( String column_name1 )
	{
		setValue( ITEM_COLUMN_NAME, column_name1 );
	}

	/* ҵ���ֶ������� : String */
	public String getColumn_name_cn()
	{
		return getValue( ITEM_COLUMN_NAME_CN );
	}

	public void setColumn_name_cn( String column_name_cn1 )
	{
		setValue( ITEM_COLUMN_NAME_CN, column_name_cn1 );
	}

}

