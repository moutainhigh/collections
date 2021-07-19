package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_task_scheduling]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectTaskSchedulingSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304181056000003L;
	
	/**
	 * 变量列表
	 */
	//public static final String ITEM_task_name = "task_name" ;			/* 计划任务名称 */
	public static final String ITEM_JHRW_LX = "scheduling_type" ;			/* 计划任务类型 */
	
	/**
	 * 构造函数
	 */
	public VoCollectTaskSchedulingSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectTaskSchedulingSelectKey(DataBus value)
	{
		super(value);
	}
	
//	/* 计划任务名称 : String */
//	public String gettask_name()
//	{
//		return getValue( ITEM_task_name );
//	}
//
//	public void settask_name( String task_name1 )
//	{
//		setValue( ITEM_task_name, task_name1 );
//	}

	/* 计划任务类型 : String */
	public String getJhrw_lx()
	{
		return getValue( ITEM_JHRW_LX );
	}

	public void setJhrw_lx( String jhrw_lx1 )
	{
		setValue( ITEM_JHRW_LX, jhrw_lx1 );
	}

}

