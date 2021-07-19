package com.gwssi.share.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_srv_scheduling]的数据对象类
 * @author Administrator
 *
 */
public class VoShareSrvScheduling extends VoBase
{
	private static final long serialVersionUID = 201308211658410002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SRV_SCHEDULING_ID = "srv_scheduling_id" ;	/* 任务调度ID */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* 服务ID */
	public static final String ITEM_SERVICE_NO = "service_no" ;		/* 服务编号 */
	public static final String ITEM_SERVICE_NAME = "service_name" ;	/* 任务名称 */
	public static final String ITEM_JOB_CLASS_NAME = "job_class_name" ;	/* 触发调用的类名 */
	public static final String ITEM_SCHEDULING_TYPE = "scheduling_type" ;	/* 计划任务类型 */
	public static final String ITEM_SCHEDULING_DAY = "scheduling_day" ;	/* 计划任务日期 */
	public static final String ITEM_START_TIME = "start_time" ;		/* 计划任务开始时间 */
	public static final String ITEM_END_TIME = "end_time" ;			/* 计划任务结束时间 */
	public static final String ITEM_SCHEDULING_COUNT = "scheduling_count" ;	/* 计划任务执行次数 */
	public static final String ITEM_INTERVAL_TIME = "interval_time" ;	/* 每次间隔时间 */
	public static final String ITEM_SCHEDULING_WEEK = "scheduling_week" ;	/* 计划任务周天 */
	public static final String ITEM_SCHEDULING_DAY1 = "scheduling_day1" ;	/* 计划任务周期中文 */
	public static final String ITEM_TASK_EXPRESSION = "task_expression" ;	/* 表达式 */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* 代码表 有效 无效 */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* 创建人ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* 创建时间 */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* 最后修改人ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* 最后修改时间 */
	
	/**
	 * 构造函数
	 */
	public VoShareSrvScheduling()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareSrvScheduling(DataBus value)
	{
		super(value);
	}
	
	/* 任务调度ID : String */
	public String getSrv_scheduling_id()
	{
		return getValue( ITEM_SRV_SCHEDULING_ID );
	}

	public void setSrv_scheduling_id( String srv_scheduling_id1 )
	{
		setValue( ITEM_SRV_SCHEDULING_ID, srv_scheduling_id1 );
	}

	/* 服务ID : String */
	public String getService_id()
	{
		return getValue( ITEM_SERVICE_ID );
	}

	public void setService_id( String service_id1 )
	{
		setValue( ITEM_SERVICE_ID, service_id1 );
	}

	/* 服务编号 : String */
	public String getService_no()
	{
		return getValue( ITEM_SERVICE_NO );
	}

	public void setService_no( String service_no1 )
	{
		setValue( ITEM_SERVICE_NO, service_no1 );
	}

	/* 任务名称 : String */
	public String getService_name()
	{
		return getValue( ITEM_SERVICE_NAME );
	}

	public void setService_name( String service_name1 )
	{
		setValue( ITEM_SERVICE_NAME, service_name1 );
	}

	/* 触发调用的类名 : String */
	public String getJob_class_name()
	{
		return getValue( ITEM_JOB_CLASS_NAME );
	}

	public void setJob_class_name( String job_class_name1 )
	{
		setValue( ITEM_JOB_CLASS_NAME, job_class_name1 );
	}

	/* 计划任务类型 : String */
	public String getScheduling_type()
	{
		return getValue( ITEM_SCHEDULING_TYPE );
	}

	public void setScheduling_type( String scheduling_type1 )
	{
		setValue( ITEM_SCHEDULING_TYPE, scheduling_type1 );
	}

	/* 计划任务日期 : String */
	public String getScheduling_day()
	{
		return getValue( ITEM_SCHEDULING_DAY );
	}

	public void setScheduling_day( String scheduling_day1 )
	{
		setValue( ITEM_SCHEDULING_DAY, scheduling_day1 );
	}

	/* 计划任务开始时间 : String */
	public String getStart_time()
	{
		return getValue( ITEM_START_TIME );
	}

	public void setStart_time( String start_time1 )
	{
		setValue( ITEM_START_TIME, start_time1 );
	}

	/* 计划任务结束时间 : String */
	public String getEnd_time()
	{
		return getValue( ITEM_END_TIME );
	}

	public void setEnd_time( String end_time1 )
	{
		setValue( ITEM_END_TIME, end_time1 );
	}

	/* 计划任务执行次数 : String */
	public String getScheduling_count()
	{
		return getValue( ITEM_SCHEDULING_COUNT );
	}

	public void setScheduling_count( String scheduling_count1 )
	{
		setValue( ITEM_SCHEDULING_COUNT, scheduling_count1 );
	}

	/* 每次间隔时间 : String */
	public String getInterval_time()
	{
		return getValue( ITEM_INTERVAL_TIME );
	}

	public void setInterval_time( String interval_time1 )
	{
		setValue( ITEM_INTERVAL_TIME, interval_time1 );
	}

	/* 计划任务周天 : String */
	public String getScheduling_week()
	{
		return getValue( ITEM_SCHEDULING_WEEK );
	}

	public void setScheduling_week( String scheduling_week1 )
	{
		setValue( ITEM_SCHEDULING_WEEK, scheduling_week1 );
	}

	/* 计划任务周期中文 : String */
	public String getScheduling_day1()
	{
		return getValue( ITEM_SCHEDULING_DAY1 );
	}

	public void setScheduling_day1( String scheduling_day11 )
	{
		setValue( ITEM_SCHEDULING_DAY1, scheduling_day11 );
	}

	/* 表达式 : String */
	public String getTask_expression()
	{
		return getValue( ITEM_TASK_EXPRESSION );
	}

	public void setTask_expression( String task_expression1 )
	{
		setValue( ITEM_TASK_EXPRESSION, task_expression1 );
	}

	/* 代码表 有效 无效 : String */
	public String getIs_markup()
	{
		return getValue( ITEM_IS_MARKUP );
	}

	public void setIs_markup( String is_markup1 )
	{
		setValue( ITEM_IS_MARKUP, is_markup1 );
	}

	/* 创建人ID : String */
	public String getCreator_id()
	{
		return getValue( ITEM_CREATOR_ID );
	}

	public void setCreator_id( String creator_id1 )
	{
		setValue( ITEM_CREATOR_ID, creator_id1 );
	}

	/* 创建时间 : String */
	public String getCreated_time()
	{
		return getValue( ITEM_CREATED_TIME );
	}

	public void setCreated_time( String created_time1 )
	{
		setValue( ITEM_CREATED_TIME, created_time1 );
	}

	/* 最后修改人ID : String */
	public String getLast_modify_id()
	{
		return getValue( ITEM_LAST_MODIFY_ID );
	}

	public void setLast_modify_id( String last_modify_id1 )
	{
		setValue( ITEM_LAST_MODIFY_ID, last_modify_id1 );
	}

	/* 最后修改时间 : String */
	public String getLast_modify_time()
	{
		return getValue( ITEM_LAST_MODIFY_TIME );
	}

	public void setLast_modify_time( String last_modify_time1 )
	{
		setValue( ITEM_LAST_MODIFY_TIME, last_modify_time1 );
	}

}

