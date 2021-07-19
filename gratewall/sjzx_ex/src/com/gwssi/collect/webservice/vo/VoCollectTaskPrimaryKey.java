package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_task]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectTaskPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304101123030004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_COLLECT_TASK_ID = "collect_task_id" ;	/* 采集任务ID */
	
	/**
	 * 构造函数
	 */
	public VoCollectTaskPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectTaskPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 采集任务ID : String */
	public String getCollect_task_id()
	{
		return getValue( ITEM_COLLECT_TASK_ID );
	}

	public void setCollect_task_id( String collect_task_id1 )
	{
		setValue( ITEM_COLLECT_TASK_ID, collect_task_id1 );
	}

}

