package com.gwssi.share.service.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_service]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareServiceSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303261650000007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* ����������� */
	public static final String ITEM_SERVICE_NAME = "service_name" ;	/* �������� */
	public static final String ITEM_SERVICE_TYPE = "service_type" ;	/* �������� */
	public static final String ITEM_SERVICE_STATE = "service_state" ;	/* ����״̬ */
	public static final String ITEM_INTERFACE_ID = "interface_id" ;	/* �ӿ�ID */
	
	/**
	 * ���캯��
	 */
	public VoShareServiceSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareServiceSelectKey(DataBus value)
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

	/* �������� : String */
	public String getService_name()
	{
		return getValue( ITEM_SERVICE_NAME );
	}

	public void setService_name( String service_name1 )
	{
		setValue( ITEM_SERVICE_NAME, service_name1 );
	}

	/* �������� : String */
	public String getService_type()
	{
		return getValue( ITEM_SERVICE_TYPE );
	}

	public void setService_type( String service_type1 )
	{
		setValue( ITEM_SERVICE_TYPE, service_type1 );
	}

	/* ����״̬ : String */
	public String getService_state()
	{
		return getValue( ITEM_SERVICE_STATE );
	}

	public void setService_state( String service_state1 )
	{
		setValue( ITEM_SERVICE_STATE, service_state1 );
	}

	/* �ӿ�ID : String */
	public String getInterface_id()
	{
		return getValue( ITEM_INTERFACE_ID );
	}

	public void setInterface_id( String interface_id1 )
	{
		setValue( ITEM_INTERFACE_ID, interface_id1 );
	}

}

