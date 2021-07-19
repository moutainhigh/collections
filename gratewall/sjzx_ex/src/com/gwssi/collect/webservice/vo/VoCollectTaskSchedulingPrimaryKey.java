package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_task_scheduling]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectTaskSchedulingPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304181056000004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TASK_SCHEDULING_ID = "task_scheduling_id" ;			/* 计划任务ID */
	
	/**
	 * 构造函数
	 */
	public VoCollectTaskSchedulingPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectTaskSchedulingPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 计划任务ID : String */
	public String gettask_scheduling_id()
	{
		return getValue( ITEM_TASK_SCHEDULING_ID );
	}

	public void settask_scheduling_id( String task_scheduling_id1 )
	{
		setValue( ITEM_TASK_SCHEDULING_ID, task_scheduling_id1 );
	}

}

