package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[opb_scheduler]的数据对象类
 * @author Administrator
 *
 */
public class VoOpbScheduler extends VoBase
{
	private static final long serialVersionUID = 200806031123540002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SCHEDULER_ID = "scheduler_id" ;	/* 主键 */
	public static final String ITEM_START_TIME = "start_time" ;		/* 开始时间 */
	public static final String ITEM_END_TIME = "end_time" ;			/* 结束时间 */
	public static final String ITEM_RUN_OPTIONS = "run_options" ;	/* 运行选项 */
	public static final String ITEM_END_OPTIONS = "end_options" ;	/* 结束选项 */
	public static final String ITEM_SCHEDULER_NAME = "scheduler_name" ;	/* 调度任务名称 */
	public static final String ITEM_RUN_COUNT = "run_count" ;		/* 运行次数 */
	
	/**
	 * 构造函数
	 */
	public VoOpbScheduler()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoOpbScheduler(DataBus value)
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

	/* 开始时间 : String */
	public String getStart_time()
	{
		return getValue( ITEM_START_TIME );
	}

	public void setStart_time( String start_time1 )
	{
		setValue( ITEM_START_TIME, start_time1 );
	}

	/* 结束时间 : String */
	public String getEnd_time()
	{
		return getValue( ITEM_END_TIME );
	}

	public void setEnd_time( String end_time1 )
	{
		setValue( ITEM_END_TIME, end_time1 );
	}

	/* 运行选项 : String */
	public String getRun_options()
	{
		return getValue( ITEM_RUN_OPTIONS );
	}

	public void setRun_options( String run_options1 )
	{
		setValue( ITEM_RUN_OPTIONS, run_options1 );
	}

	/* 结束选项 : String */
	public String getEnd_options()
	{
		return getValue( ITEM_END_OPTIONS );
	}

	public void setEnd_options( String end_options1 )
	{
		setValue( ITEM_END_OPTIONS, end_options1 );
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

	/* 运行次数 : String */
	public String getRun_count()
	{
		return getValue( ITEM_RUN_COUNT );
	}

	public void setRun_count( String run_count1 )
	{
		setValue( ITEM_RUN_COUNT, run_count1 );
	}

}

