package com.gwssi.share.service.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_service_condition]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareServiceConditionPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304021334180004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CONDITION_ID = "condition_id" ;	/* �����ѯ����ID */
	
	/**
	 * ���캯��
	 */
	public VoShareServiceConditionPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareServiceConditionPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �����ѯ����ID : String */
	public String getCondition_id()
	{
		return getValue( ITEM_CONDITION_ID );
	}

	public void setCondition_id( String condition_id1 )
	{
		setValue( ITEM_CONDITION_ID, condition_id1 );
	}

}

