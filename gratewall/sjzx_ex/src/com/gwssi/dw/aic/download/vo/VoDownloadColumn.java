package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_column]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadColumn extends VoBase
{
	private static final long serialVersionUID = 200902201130080006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLUMN_NO = "column_no" ;		/* �ֶα��� */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* ��ʱ����� */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* �ֶ��� */
	public static final String ITEM_COLUMN_NAME_CN = "column_name_cn" ;	/* �ֶ������� */
	public static final String ITEM_COLUMN_ORDER = "column_order" ;	/* �ֶ�˳�� */
	public static final String ITEM_EDIT_TYPE = "edit_type" ;		/* �༭���� */
	public static final String ITEM_EDIT_CONTENT = "edit_content" ;	/* �༭���� */
	public static final String ITEM_DEMO = "demo" ;					/* ��ע */
	
	/**
	 * ���캯��
	 */
	public VoDownloadColumn()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadColumn(DataBus value)
	{
		super(value);
	}
	
	/* �ֶα��� : String */
	public String getColumn_no()
	{
		return getValue( ITEM_COLUMN_NO );
	}

	public void setColumn_no( String column_no1 )
	{
		setValue( ITEM_COLUMN_NO, column_no1 );
	}

	/* ��ʱ����� : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

	/* �ֶ��� : String */
	public String getColumn_name()
	{
		return getValue( ITEM_COLUMN_NAME );
	}

	public void setColumn_name( String column_name1 )
	{
		setValue( ITEM_COLUMN_NAME, column_name1 );
	}

	/* �ֶ������� : String */
	public String getColumn_name_cn()
	{
		return getValue( ITEM_COLUMN_NAME_CN );
	}

	public void setColumn_name_cn( String column_name_cn1 )
	{
		setValue( ITEM_COLUMN_NAME_CN, column_name_cn1 );
	}

	/* �ֶ�˳�� : String */
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

