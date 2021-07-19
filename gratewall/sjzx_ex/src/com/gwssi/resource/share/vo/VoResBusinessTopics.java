package com.gwssi.resource.share.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_business_topics]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResBusinessTopics extends VoBase
{
	private static final long serialVersionUID = 201303151434280002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_BUSINESS_TOPICS_ID = "business_topics_id" ;	/* ҵ������ID */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* �������ID */
	public static final String ITEM_TOPICS_NAME = "topics_name" ;	/* �������� */
	public static final String ITEM_TOPICS_NO = "topics_no" ;		/* ������ */
	public static final String ITEM_TOPICS_DESC = "topics_desc" ;	/* �������� */
	public static final String ITEM_SHOW_ORDER = "show_order" ;		/* ��ʾ˳�� */
	
	/**
	 * ���캯��
	 */
	public VoResBusinessTopics()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResBusinessTopics(DataBus value)
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

	/* �������ID : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* �������� : String */
	public String getTopics_name()
	{
		return getValue( ITEM_TOPICS_NAME );
	}

	public void setTopics_name( String topics_name1 )
	{
		setValue( ITEM_TOPICS_NAME, topics_name1 );
	}

	/* ������ : String */
	public String getTopics_no()
	{
		return getValue( ITEM_TOPICS_NO );
	}

	public void setTopics_no( String topics_no1 )
	{
		setValue( ITEM_TOPICS_NO, topics_no1 );
	}

	/* �������� : String */
	public String getTopics_desc()
	{
		return getValue( ITEM_TOPICS_DESC );
	}

	public void setTopics_desc( String topics_desc1 )
	{
		setValue( ITEM_TOPICS_DESC, topics_desc1 );
	}

	/* ��ʾ˳�� : String */
	public String getShow_order()
	{
		return getValue( ITEM_SHOW_ORDER );
	}

	public void setShow_order( String show_order1 )
	{
		setValue( ITEM_SHOW_ORDER, show_order1 );
	}

}

