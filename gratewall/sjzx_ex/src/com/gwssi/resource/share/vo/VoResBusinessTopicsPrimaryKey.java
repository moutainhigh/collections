package com.gwssi.resource.share.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_business_topics]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResBusinessTopicsPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303151434280004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_BUSINESS_TOPICS_ID = "business_topics_id" ;	/* ҵ������ID */
	
	/**
	 * ���캯��
	 */
	public VoResBusinessTopicsPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResBusinessTopicsPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ҵ������ID : String */
	public String getBusiness_topics_id()
	{
		return getValue( ITEM_BUSINESS_TOPICS_ID );
	}

	public void setBusiness_topics_id( String business_topics_id1 )
	{
		setValue( ITEM_BUSINESS_TOPICS_ID, business_topics_id1 );
	}

}

