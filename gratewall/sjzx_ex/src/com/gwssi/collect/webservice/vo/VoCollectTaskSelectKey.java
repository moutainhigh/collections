package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_task]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectTaskSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304101123030003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* 所属服务对象 */
	public static final String ITEM_TASK_NAME = "task_name" ;		/* 任务名称 */
	public static final String ITEM_COLLECT_TYPE = "collect_type" ;	/* 采集类型 */
	
	/**
	 * 构造函数
	 */
	public VoCollectTaskSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectTaskSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 所属服务对象 : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* 任务名称 : String */
	public String getTask_name()
	{
		return getValue( ITEM_TASK_NAME );
	}

	public void setTask_name( String task_name1 )
	{
		setValue( ITEM_TASK_NAME, task_name1 );
	}

	/* 采集类型 : String */
	public String getCollect_type()
	{
		return getValue( ITEM_COLLECT_TYPE );
	}

	public void setCollect_type( String collect_type1 )
	{
		setValue( ITEM_COLLECT_TYPE, collect_type1 );
	}

}

