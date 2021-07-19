package com.gwssi.resource.collect.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_collect_dataitem]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResCollectDataitem extends VoBase
{
	private static final long serialVersionUID = 201303221103430006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLLECT_DATAITEM_ID = "collect_dataitem_id" ;	/* �ɼ�������ID */
	public static final String ITEM_COLLECT_TABLE_ID = "collect_table_id" ;	/* �ɼ����ݱ�ID */
	public static final String ITEM_DATAITEM_NAME_EN = "dataitem_name_en" ;	/* ���������� */
	public static final String ITEM_DATAITEM_NAME_CN = "dataitem_name_cn" ;	/* �������������� */
	public static final String ITEM_DATAITEM_TYPE = "dataitem_type" ;	/* ���������� */
	public static final String ITEM_DATAITEM_LONG = "dataitem_long" ;	/* ������� */
	public static final String ITEM_IS_KEY = "is_key" ;				/* �Ƿ����� */
	public static final String ITEM_IS_CODE_TABLE = "is_code_table" ;	/* �Ƿ����� */
	public static final String ITEM_CODE_TABLE = "code_table" ;		/* ��Ӧ����� */
	public static final String ITEM_DATAITEM_LONG_DESC = "dataitem_long_desc" ;	/* ���������� */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* ��Ч��� */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* ������ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* ����޸���ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* ����޸�ʱ�� */
	public static String ITEM_DATAITEM_STATE = "dataitem_state" ;	/* ������Ƿ������ɸ������� */
	
	/**
	 * ���캯��
	 */
	public VoResCollectDataitem()
	{
		super();
	}
	
	/**
	 * @return the itemDataitemState
	 */
	public static String getItemDataitemState()
	{
		return ITEM_DATAITEM_STATE;
	}

	/**
	 * @param itemDataitemState the itemDataitemState to set
	 */
	public static void setItemDataitemState(String itemDataitemState)
	{
		ITEM_DATAITEM_STATE = itemDataitemState;
	}

	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResCollectDataitem(DataBus value)
	{
		super(value);
	}
	
	/* �ɼ�������ID : String */
	public String getCollect_dataitem_id()
	{
		return getValue( ITEM_COLLECT_DATAITEM_ID );
	}

	public void setCollect_dataitem_id( String collect_dataitem_id1 )
	{
		setValue( ITEM_COLLECT_DATAITEM_ID, collect_dataitem_id1 );
	}

	/* �ɼ����ݱ�ID : String */
	public String getCollect_table_id()
	{
		return getValue( ITEM_COLLECT_TABLE_ID );
	}

	public void setCollect_table_id( String collect_table_id1 )
	{
		setValue( ITEM_COLLECT_TABLE_ID, collect_table_id1 );
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

	/* �Ƿ����� : String */
	public String getIs_key()
	{
		return getValue( ITEM_IS_KEY );
	}

	public void setIs_key( String is_key1 )
	{
		setValue( ITEM_IS_KEY, is_key1 );
	}

	/* �Ƿ����� : String */
	public String getIs_code_table()
	{
		return getValue( ITEM_IS_CODE_TABLE );
	}

	public void setIs_code_table( String is_code_table1 )
	{
		setValue( ITEM_IS_CODE_TABLE, is_code_table1 );
	}

	/* ��Ӧ����� : String */
	public String getCode_table()
	{
		return getValue( ITEM_CODE_TABLE );
	}

	public void setCode_table( String code_table1 )
	{
		setValue( ITEM_CODE_TABLE, code_table1 );
	}

	/* ���������� : String */
	public String getDataitem_long_desc()
	{
		return getValue( ITEM_DATAITEM_LONG_DESC );
	}

	public void setDataitem_long_desc( String dataitem_long_desc1 )
	{
		setValue( ITEM_DATAITEM_LONG_DESC, dataitem_long_desc1 );
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

	/* ������ID : String */
	public String getCreator_id()
	{
		return getValue( ITEM_CREATOR_ID );
	}

	public void setCreator_id( String creator_id1 )
	{
		setValue( ITEM_CREATOR_ID, creator_id1 );
	}

	/* ����ʱ�� : String */
	public String getCreated_time()
	{
		return getValue( ITEM_CREATED_TIME );
	}

	public void setCreated_time( String created_time1 )
	{
		setValue( ITEM_CREATED_TIME, created_time1 );
	}

	/* ����޸���ID : String */
	public String getLast_modify_id()
	{
		return getValue( ITEM_LAST_MODIFY_ID );
	}

	public void setLast_modify_id( String last_modify_id1 )
	{
		setValue( ITEM_LAST_MODIFY_ID, last_modify_id1 );
	}

	/* ����޸�ʱ�� : String */
	public String getLast_modify_time()
	{
		return getValue( ITEM_LAST_MODIFY_TIME );
	}

	public void setLast_modify_time( String last_modify_time1 )
	{
		setValue( ITEM_LAST_MODIFY_TIME, last_modify_time1 );
	}

}

