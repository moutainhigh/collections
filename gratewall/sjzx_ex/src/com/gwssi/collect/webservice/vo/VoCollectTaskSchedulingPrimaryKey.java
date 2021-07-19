package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_task_scheduling]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectTaskSchedulingPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304181056000004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TASK_SCHEDULING_ID = "task_scheduling_id" ;			/* �ƻ�����ID */
	
	/**
	 * ���캯��
	 */
	public VoCollectTaskSchedulingPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectTaskSchedulingPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �ƻ�����ID : String */
	public String gettask_scheduling_id()
	{
		return getValue( ITEM_TASK_SCHEDULING_ID );
	}

	public void settask_scheduling_id( String task_scheduling_id1 )
	{
		setValue( ITEM_TASK_SCHEDULING_ID, task_scheduling_id1 );
	}

}

