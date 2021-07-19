package com.gwssi.log.sharelog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_log]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareLogSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304031121530003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* ����������� */
	public static final String ITEM_SERVICE_TYPE = "service_type" ;	/* service_type */
	public static final String ITEM_SERVICE_START_TIME = "service_start_time" ;	/* service_start_time */
	public static final String ITEM_SERVICE_END_TIME = "service_end_time" ;	/* service_end_time */
	
	/**
	 * ���캯��
	 */
	public VoShareLogSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareLogSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ����������� : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* service_type : String */
	public String getService_type()
	{
		return getValue( ITEM_SERVICE_TYPE );
	}

	public void setService_type( String service_type1 )
	{
		setValue( ITEM_SERVICE_TYPE, service_type1 );
	}

	/* service_start_time : String */
	public String getService_start_time()
	{
		return getValue( ITEM_SERVICE_START_TIME );
	}

	public void setService_start_time( String service_start_time1 )
	{
		setValue( ITEM_SERVICE_START_TIME, service_start_time1 );
	}

	/* service_end_time : String */
	public String getService_end_time()
	{
		return getValue( ITEM_SERVICE_END_TIME );
	}

	public void setService_end_time( String service_end_time1 )
	{
		setValue( ITEM_SERVICE_END_TIME, service_end_time1 );
	}

}

