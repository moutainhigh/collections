package com.gwssi.resource.collect.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_collect_dataitem]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResCollectDataitemSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303221103430007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLLECT_TABLE_ID = "collect_table_id" ;	/* �ɼ����ݱ�ID */
	public static final String ITEM_DATAITEM_NAME_EN = "dataitem_name_en" ;	/* ���������� */
	public static final String ITEM_DATAITEM_NAME_CN = "dataitem_name_cn" ;	/* �������������� */
	public static final String ITEM_DATAITEM_TYPE = "dataitem_type" ;	/* ���������� */
	public static final String ITEM_CODE_TABLE = "code_table" ;		/* ��Ӧ����� */
	
	/**
	 * ���캯��
	 */
	public VoResCollectDataitemSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResCollectDataitemSelectKey(DataBus value)
	{
		super(value);
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

	/* ��Ӧ����� : String */
	public String getCode_table()
	{
		return getValue( ITEM_CODE_TABLE );
	}

	public void setCode_table( String code_table1 )
	{
		setValue( ITEM_CODE_TABLE, code_table1 );
	}

}

