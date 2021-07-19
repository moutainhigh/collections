package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectTaskSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304101123030003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* ����������� */
	public static final String ITEM_TASK_NAME = "task_name" ;		/* �������� */
	public static final String ITEM_COLLECT_TYPE = "collect_type" ;	/* �ɼ����� */
	
	/**
	 * ���캯��
	 */
	public VoCollectTaskSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectTaskSelectKey(DataBus value)
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
	public String getTask_name()
	{
		return getValue( ITEM_TASK_NAME );
	}

	public void setTask_name( String task_name1 )
	{
		setValue( ITEM_TASK_NAME, task_name1 );
	}

	/* �ɼ����� : String */
	public String getCollect_type()
	{
		return getValue( ITEM_COLLECT_TYPE );
	}

	public void setCollect_type( String collect_type1 )
	{
		setValue( ITEM_COLLECT_TYPE, collect_type1 );
	}

}

