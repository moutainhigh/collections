package com.gwssi.log.collectlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_joumal]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectJoumal extends VoBase
{
	private static final long serialVersionUID = 201304101519320002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_COLLECT_JOUMAL_ID = "collect_joumal_id" ;	/* 采集日志ID */
	public static final String ITEM_COLLECT_TASK_ID = "collect_task_id" ;	/* 采集任务ID */
	public static final String ITEM_TASK_NAME = "task_name" ;		/* 任务名称 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* 服务对象ID */
	public static final String ITEM_SERVICE_TARGETS_NAME = "service_targets_name" ;	/* 服务对象名称 */
	public static final String ITEM_COLLECT_TYPE = "collect_type" ;	/* 代码表 ftp */
	public static final String ITEM_TASK_ID = "task_id" ;		/* 任务ID */
	public static final String ITEM_SERVICE_NO = "service_no" ;		/* 任务ID */
	public static final String ITEM_TASK_START_TIME = "task_start_time" ;	/* 任务开始时间 */
	public static final String ITEM_TASK_END_TIME = "task_end_time" ;	/* 任务结束时间 */
	public static final String ITEM_TASK_CONSUME_TIME = "task_consume_time" ;	/* 单位毫秒 */
	public static final String ITEM_COLLECT_DATA_AMOUNT = "collect_data_amount" ;	/* 本次采集数据量 */
	public static final String ITEM_TASK_STATUS = "task_status" ;	/* 代码表 启用停用归档 */
	public static final String ITEM_PATAMETER = "patameter" ;		/* 入口参数 */
	public static final String ITEM_RETURN_CODES = "return_codes" ;	/* 代码表 数据交换产生错误数据编号 */
	
	/**
	 * 构造函数
	 */
	public VoCollectJoumal()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectJoumal(DataBus value)
	{
		super(value);
	}
	
	/* 采集日志ID : String */
	public String getCollect_joumal_id()
	{
		return getValue( ITEM_COLLECT_JOUMAL_ID );
	}

	public void setCollect_joumal_id( String collect_joumal_id1 )
	{
		setValue( ITEM_COLLECT_JOUMAL_ID, collect_joumal_id1 );
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

	/* 任务名称 : String */
	public String getTask_name()
	{
		return getValue( ITEM_TASK_NAME );
	}

	public void setTask_name( String task_name1 )
	{
		setValue( ITEM_TASK_NAME, task_name1 );
	}

	/* 服务对象ID : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* 服务对象名称 : String */
	public String getService_targets_name()
	{
		return getValue( ITEM_SERVICE_TARGETS_NAME );
	}

	public void setService_targets_name( String service_targets_name1 )
	{
		setValue( ITEM_SERVICE_TARGETS_NAME, service_targets_name1 );
	}

	/* 代码表 ftp : String */
	public String getCollect_type()
	{
		return getValue( ITEM_COLLECT_TYPE );
	}

	public void setCollect_type( String collect_type1 )
	{
		setValue( ITEM_COLLECT_TYPE, collect_type1 );
	}

	/* 任务ID : String */
	public String getTask_id()
	{
		return getValue( ITEM_TASK_ID );
	}

	public void setTask_id( String task_id1 )
	{
		setValue( ITEM_TASK_ID, task_id1 );
	}

	/* 任务ID : String */
	public String getService_no()
	{
		return getValue( ITEM_SERVICE_NO );
	}

	public void setService_no( String service_no1 )
	{
		setValue( ITEM_SERVICE_NO, service_no1 );
	}
	
	/* 任务开始时间 : String */
	public String getTask_start_time()
	{
		return getValue( ITEM_TASK_START_TIME );
	}

	public void setTask_start_time( String task_start_time1 )
	{
		setValue( ITEM_TASK_START_TIME, task_start_time1 );
	}

	/* 任务结束时间 : String */
	public String getTask_end_time()
	{
		return getValue( ITEM_TASK_END_TIME );
	}

	public void setTask_end_time( String task_end_time1 )
	{
		setValue( ITEM_TASK_END_TIME, task_end_time1 );
	}

	/* 单位毫秒 : String */
	public String getTask_consume_time()
	{
		return getValue( ITEM_TASK_CONSUME_TIME );
	}

	public void setTask_consume_time( String task_consume_time1 )
	{
		setValue( ITEM_TASK_CONSUME_TIME, task_consume_time1 );
	}

	/* 本次采集数据量 : String */
	public String getCollect_data_amount()
	{
		return getValue( ITEM_COLLECT_DATA_AMOUNT );
	}

	public void setCollect_data_amount( String collect_data_amount1 )
	{
		setValue( ITEM_COLLECT_DATA_AMOUNT, collect_data_amount1 );
	}

	/* 代码表 启用停用归档 : String */
	public String getTask_status()
	{
		return getValue( ITEM_TASK_STATUS );
	}

	public void setTask_status( String task_status1 )
	{
		setValue( ITEM_TASK_STATUS, task_status1 );
	}

	/* 入口参数 : String */
	public String getPatameter()
	{
		return getValue( ITEM_PATAMETER );
	}

	public void setPatameter( String patameter1 )
	{
		setValue( ITEM_PATAMETER, patameter1 );
	}

	/* 代码表 数据交换产生错误数据编号 : String */
	public String getReturn_codes()
	{
		return getValue( ITEM_RETURN_CODES );
	}

	public void setReturn_codes( String return_codes1 )
	{
		setValue( ITEM_RETURN_CODES, return_codes1 );
	}

}

