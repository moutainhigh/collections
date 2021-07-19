package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_column_semantic]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysColumnSemantic extends VoBase
{
	private static final long serialVersionUID = 200804181524160010L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* ҵ������ */
	public static final String ITEM_COLUMN_NO = "column_no" ;		/* ҵ���ֶα��� */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* ҵ���ֶ��� */
	public static final String ITEM_COLUMN_NAME_CN = "column_name_cn" ;	/* ҵ���ֶ������� */
	public static final String ITEM_COLUMN_ORDER = "column_order" ;	/* ����˳�� */
	public static final String ITEM_EDIT_TYPE = "edit_type" ;		/* �༭���� */
	public static final String ITEM_EDIT_CONTENT = "edit_content" ;	/* �༭���� */
	public static final String ITEM_DEMO = "demo" ;					/* ��ע */
	
	/**
	 * ���캯��
	 */
	public VoSysColumnSemantic()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysColumnSemantic(DataBus value)
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

	/* ҵ���ֶα��� : String */
	public String getColumn_no()
	{
		return getValue( ITEM_COLUMN_NO );
	}

	public void setColumn_no( String column_no1 )
	{
		setValue( ITEM_COLUMN_NO, column_no1 );
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

	/* ����˳�� : String */
	public String getColumn_order()
	{
		return getValue( ITEM_COLUMN_ORDER );
	}

	public void setColumn_order( String column_order1 )
	{
		setValue( ITEM_COLUMN_ORDER, column_order1 );
	}

	/* �༭���� : String */
	public String getEdit_type()
	{
		return getValue( ITEM_EDIT_TYPE );
	}

	public void setEdit_type( String edit_type1 )
	{
		setValue( ITEM_EDIT_TYPE, edit_type1 );
	}

	/* �༭���� : String */
	public String getEdit_content()
	{
		return getValue( ITEM_EDIT_CONTENT );
	}

	public void setEdit_content( String edit_content1 )
	{
		setValue( ITEM_EDIT_CONTENT, edit_content1 );
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

}

