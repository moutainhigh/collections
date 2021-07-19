package com.gwssi.resource.svrobj.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_data_source]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResDataSourcePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303141052490004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DATA_SOURCE_ID = "data_source_id" ;	/* ����ԴID */
	
	/**
	 * ���캯��
	 */
	public VoResDataSourcePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResDataSourcePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ԴID : String */
	public String getData_source_id()
	{
		return getValue( ITEM_DATA_SOURCE_ID );
	}

	public void setData_source_id( String data_source_id1 )
	{
		setValue( ITEM_DATA_SOURCE_ID, data_source_id1 );
	}

}

