package com.gwssi.resource.svrobj.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_service_targets]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResServiceTargetsSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303131040540003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SERVICE_TARGETS_NAME = "service_targets_name" ;	/* ����������� */
	public static final String ITEM_SERVICE_TARGETS_TYPE = "service_targets_type" ;	/* ����������� */
	public static final String ITEM_SERVICE_STATUS = "service_status" ;	/* ����״̬ */
	public static final String ITEM_CREATED_TIME_START = "created_time_start" ;	/* ����ʱ�� */
	public static final String ITEM_CREATED_TIME_END = "created_time_end" ;	/* ����ʱ�� */
	
	/**
	 * ���캯��
	 */
	public VoResServiceTargetsSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResServiceTargetsSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ����������� : String */
	public String getService_targets_name()
	{
		return getValue( ITEM_SERVICE_TARGETS_NAME );
	}

	public void setService_targets_name( String service_targets_name1 )
	{
		setValue( ITEM_SERVICE_TARGETS_NAME, service_targets_name1 );
	}

	/* ����������� : String */
	public String getService_targets_type()
	{
		return getValue( ITEM_SERVICE_TARGETS_TYPE );
	}

	public void setService_targets_type( String service_targets_type1 )
	{
		setValue( ITEM_SERVICE_TARGETS_TYPE, service_targets_type1 );
	}

	/* ����״̬ : String */
	public String getService_status()
	{
		return getValue( ITEM_SERVICE_STATUS );
	}

	public void setService_status( String service_status1 )
	{
		setValue( ITEM_SERVICE_STATUS, service_status1 );
	}

	/* ����ʱ�� : String */
	public String getCreated_time_start()
	{
		return getValue( ITEM_CREATED_TIME_START );
	}

	public void setCreated_time_start( String created_time_start1 )
	{
		setValue( ITEM_CREATED_TIME_START, created_time_start1 );
	}

	/* ����ʱ�� : String */
	public String getCreated_time_end()
	{
		return getValue( ITEM_CREATED_TIME_END );
	}

	public void setCreated_time_end( String created_time_end1 )
	{
		setValue( ITEM_CREATED_TIME_END, created_time_end1 );
	}

}

