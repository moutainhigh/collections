package com.gwssi.share.rule.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_service_rule]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareServiceRuleSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304081757160003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* ����ID */
	
	/**
	 * ���캯��
	 */
	public VoShareServiceRuleSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareServiceRuleSelectKey(DataBus value)
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

