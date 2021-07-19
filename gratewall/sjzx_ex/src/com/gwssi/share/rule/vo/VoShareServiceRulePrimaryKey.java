package com.gwssi.share.rule.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_service_rule]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareServiceRulePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304081757160004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_RULE_ID = "rule_id" ;			/* ������ʹ���ID */
	
	/**
	 * ���캯��
	 */
	public VoShareServiceRulePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareServiceRulePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ������ʹ���ID : String */
	public String getRule_id()
	{
		return getValue( ITEM_RULE_ID );
	}

	public void setRule_id( String rule_id1 )
	{
		setValue( ITEM_RULE_ID, rule_id1 );
	}

}

