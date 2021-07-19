package com.gwssi.resource.share.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_share_table]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResShareTable extends VoBase
{
	private static final long serialVersionUID = 201303191807570002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SHARE_TABLE_ID = "share_table_id" ;	/* �����ID */
	public static final String ITEM_BUSINESS_TOPICS_ID = "business_topics_id" ;	/* ҵ������ID */
	public static final String ITEM_TABLE_NAME_EN = "table_name_en" ;	/* ������ */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* ���������� */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* ���� */
	public static final String ITEM_SHOW_ORDER = "show_order" ;		/* ��ʾ˳�� */
	public static final String ITEM_TIME_ = "time_" ;				/* ʱ���ֶ� */
	public static final String ITEM_TABLE_TYPE = "table_type" ;		/* ������ */
	public static final String ITEM_TABLE_INDEX = "table_index" ;	/* ������ */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* ��Ч��� */
	
	/**
	 * ���캯��
	 */
	public VoResShareTable()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResShareTable(DataBus value)
	{
		super(value);
	}
	
	/* �����ID : String */
	public String getShare_table_id()
	{
		return getValue( ITEM_SHARE_TABLE_ID );
	}

	public void setShare_table_id( String share_table_id1 )
	{
		setValue( ITEM_SHARE_TABLE_ID, share_table_id1 );
	}

	/* ҵ������ID : String */
	public String getBusiness_topics_id()
	{
		return getValue( ITEM_BUSINESS_TOPICS_ID );
	}

	public void setBusiness_topics_id( String business_topics_id1 )
	{
		setValue( ITEM_BUSINESS_TOPICS_ID, business_topics_id1 );
	}

	/* ������ : String */
	public String getTable_name_en()
	{
		return getValue( ITEM_TABLE_NAME_EN );
	}

	public void setTable_name_en( String table_name_en1 )
	{
		setValue( ITEM_TABLE_NAME_EN, table_name_en1 );
	}

	/* ���������� : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

	/* ���� : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

	/* ��ʾ˳�� : String */
	public String getShow_order()
	{
		return getValue( ITEM_SHOW_ORDER );
	}

	public void setShow_order( String show_order1 )
	{
		setValue( ITEM_SHOW_ORDER, show_order1 );
	}

	/* ʱ���ֶ� : String */
	public String getTime_()
	{
		return getValue( ITEM_TIME_ );
	}

	public void setTime_( String time_1 )
	{
		setValue( ITEM_TIME_, time_1 );
	}

	/* ������ : String */
	public String getTable_type()
	{
		return getValue( ITEM_TABLE_TYPE );
	}

	public void setTable_type( String table_type1 )
	{
		setValue( ITEM_TABLE_TYPE, table_type1 );
	}

	/* ������ : String */
	public String getTable_index()
	{
		return getValue( ITEM_TABLE_INDEX );
	}

	public void setTable_index( String table_index1 )
	{
		setValue( ITEM_TABLE_INDEX, table_index1 );
	}

	/* ��Ч��� : String */
	public String getIs_markup()
	{
		return getValue( ITEM_IS_MARKUP );
	}

	public void setIs_markup( String is_markup1 )
	{
		setValue( ITEM_IS_MARKUP, is_markup1 );
	}

}

