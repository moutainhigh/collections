package com.gwssi.collect.database.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_database_task]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectDatabaseTask extends VoBase
{
	private static final long serialVersionUID = 201307021053290002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DATABASE_TASK_ID = "database_task_id" ;	/* 采集数据库主键ID */
	public static final String ITEM_COLLECT_TASK_ID = "collect_task_id" ;	/* 采集任务ID */
	
	/**
	 * 构造函数
	 */
	public VoCollectDatabaseTask()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectDatabaseTask(DataBus value)
	{
		super(value);
	}
	
	/* 采集数据库主键ID : String */
	public String getDatabase_task_id()
	{
		return getValue( ITEM_DATABASE_TASK_ID );
	}

	public void setDatabase_task_id( String database_task_id1 )
	{
		setValue( ITEM_DATABASE_TASK_ID, database_task_id1 );
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

