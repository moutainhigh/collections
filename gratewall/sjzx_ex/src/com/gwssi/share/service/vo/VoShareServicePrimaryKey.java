package com.gwssi.share.service.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_service]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareServicePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303261650000008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* ����ID */
	
	/**
	 * ���캯��
	 */
	public VoShareServicePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareServicePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ID : String */
	public String getService_id()
	{
		return getValue( ITEM_SERVICE_ID );
	}

	public void setService_id( String service_id1 )
	{
		setValue( ITEM_SERVICE_ID, service_id1 );
	}

}

