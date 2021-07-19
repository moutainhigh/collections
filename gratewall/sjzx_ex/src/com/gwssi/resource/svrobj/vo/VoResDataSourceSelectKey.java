package com.gwssi.resource.svrobj.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_data_source]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResDataSourceSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303141052490003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DATA_SOURCE_NAME = "data_source_name" ;	/* ����Դ���� */
	public static final String ITEM_DATA_SOURCE_TYPE = "data_source_type" ;	/* ����Դ���� */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* �������ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	
	/**
	 * ���캯��
	 */
	public VoResDataSourceSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResDataSourceSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ����Դ���� : String */
	public String getData_source_name()
	{
		return getValue( ITEM_DATA_SOURCE_NAME );
	}

	public void setData_source_name( String data_source_name1 )
	{
		setValue( ITEM_DATA_SOURCE_NAME, data_source_name1 );
	}

	/* ����Դ���� : String */
	public String getData_source_type()
	{
		return getValue( ITEM_DATA_SOURCE_TYPE );
	}

	public void setData_source_type( String data_source_type1 )
	{
		setValue( ITEM_DATA_SOURCE_TYPE, data_source_type1 );
	}

	/* �������ID : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
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

}

