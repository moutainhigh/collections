package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[opb_scheduler]的数据对象类
 * @author Administrator
 *
 */
public class VoOpbSchedulerSelectKey extends VoBase
{
	private static final long serialVersionUID = 200806031123550003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SCHEDULER_ID = "scheduler_id" ;	/* 主键 */
	public static final String ITEM_SCHEDULER_NAME = "scheduler_name" ;	/* 调度任务名称 */
	
	/**
	 * 构造函数
	 */
	public VoOpbSchedulerSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoOpbSchedulerSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 主键 : String */
	public String getScheduler_id()
	{
		return getValue( ITEM_SCHEDULER_ID );
	}

	public void setScheduler_id( String scheduler_id1 )
	{
		setValue( ITEM_SCHEDULER_ID, scheduler_id1 );
	}

	/* 调度任务名称 : String */
	public String getScheduler_name()
	{
		return getValue( ITEM_SCHEDULER_NAME );
	}

	public void setScheduler_name( String scheduler_name1 )
	{
		setValue( ITEM_SCHEDULER_NAME, scheduler_name1 );
	}

}

