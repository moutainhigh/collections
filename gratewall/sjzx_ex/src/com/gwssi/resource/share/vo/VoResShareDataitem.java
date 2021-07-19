package com.gwssi.resource.share.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_share_dataitem]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResShareDataitem extends VoBase
{
	private static final long serialVersionUID = 201303191809040006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SHARE_DATAITEM_ID = "share_dataitem_id" ;	/* ����������ID */
	public static final String ITEM_SHARE_TABLE_ID = "share_table_id" ;	/* �����ID */
	public static final String ITEM_DATAITEM_NAME_EN = "dataitem_name_en" ;	/* ���������� */
	public static final String ITEM_DATAITEM_NAME_CN = "dataitem_name_cn" ;	/* �������������� */
	public static final String ITEM_DATAITEM_TYPE = "dataitem_type" ;	/* ���������� */
	public static final String ITEM_DATAITEM_LONG = "dataitem_long" ;	/* ������� */
	public static final String ITEM_CODE_TABLE_NAME = "code_table_name" ;	/* ϵͳ������ */
	public static final String ITEM_CODE_TABLE = "code_table" ;		/* ��Ӧ���뼯 */
	public static final String ITEM_IS_KEY = "is_key" ;				/* �Ƿ����� */
	public static final String ITEM_DATAITEM_DESC = "dataitem_desc" ;	/* ���� */
	public static final String ITEM_SHOW_ORDER = "show_order" ;		/* ��ʾ˳�� */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* ��Ч��� */
	
	/**
	 * ���캯��
	 */
	public VoResShareDataitem()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResShareDataitem(DataBus value)
	{
		super(value);
	}
	
	/* ����������ID : String */
	public String getShare_dataitem_id()
	{
		return getValue( ITEM_SHARE_DATAITEM_ID );
	}

	public void setShare_dataitem_id( String share_dataitem_id1 )
	{
		setValue( ITEM_SHARE_DATAITEM_ID, share_dataitem_id1 );
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

	/* ���������� : String */
	public String getDataitem_name_en()
	{
		return getValue( ITEM_DATAITEM_NAME_EN );
	}

	public void setDataitem_name_en( String dataitem_name_en1 )
	{
		setValue( ITEM_DATAITEM_NAME_EN, dataitem_name_en1 );
	}

	/* �������������� : String */
	public String getDataitem_name_cn()
	{
		return getValue( ITEM_DATAITEM_NAME_CN );
	}

	public void setDataitem_name_cn( String dataitem_name_cn1 )
	{
		setValue( ITEM_DATAITEM_NAME_CN, dataitem_name_cn1 );
	}

	/* ���������� : String */
	public String getDataitem_type()
	{
		return getValue( ITEM_DATAITEM_TYPE );
	}

	public void setDataitem_type( String dataitem_type1 )
	{
		setValue( ITEM_DATAITEM_TYPE, dataitem_type1 );
	}

	/* ������� : String */
	public String getDataitem_long()
	{
		return getValue( ITEM_DATAITEM_LONG );
	}

	public void setDataitem_long( String dataitem_long1 )
	{
		setValue( ITEM_DATAITEM_LONG, dataitem_long1 );
	}

	/* ϵͳ������ : String */
	public String getCode_table_name()
	{
		return getValue( ITEM_CODE_TABLE_NAME );
	}

	public void setCode_table_name( String code_table_name1 )
	{
		setValue( ITEM_CODE_TABLE_NAME, code_table_name1 );
	}

	/* ��Ӧ���뼯 : String */
	public String getCode_table()
	{
		return getValue( ITEM_CODE_TABLE );
	}

	public void setCode_table( String code_table1 )
	{
		setValue( ITEM_CODE_TABLE, code_table1 );
	}

	/* �Ƿ����� : String */
	public String getIs_key()
	{
		return getValue( ITEM_IS_KEY );
	}

	public void setIs_key( String is_key1 )
	{
		setValue( ITEM_IS_KEY, is_key1 );
	}

	/* ���� : String */
	public String getDataitem_desc()
	{
		return getValue( ITEM_DATAITEM_DESC );
	}

	public void setDataitem_desc( String dataitem_desc1 )
	{
		setValue( ITEM_DATAITEM_DESC, dataitem_desc1 );
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

