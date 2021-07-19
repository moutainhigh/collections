package com.gwssi.share.service.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_service_condition]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareServiceConditionSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304021334180003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* ����ID */
	public static final String ITEM_NEED_INPUT = "need_input" ;		/* �Ƿ���Ҫ���� */
	
	/**
	 * ���캯��
	 */
	public VoShareServiceConditionSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareServiceConditionSelectKey(DataBus value)
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

	/* �Ƿ���Ҫ���� : String */
	public String getNeed_input()
	{
		return getValue( ITEM_NEED_INPUT );
	}

	public void setNeed_input( String need_input1 )
	{
		setValue( ITEM_NEED_INPUT, need_input1 );
	}

}

