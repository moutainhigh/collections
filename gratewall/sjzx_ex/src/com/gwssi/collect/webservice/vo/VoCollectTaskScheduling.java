package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_task_scheduling]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectTaskScheduling extends VoBase
{
	private static final long serialVersionUID = 201304181056000002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_task_scheduling_id = "task_scheduling_id" ;			/* 计划任务ID */
	public static final String ITEM_collect_task_id = "collect_task_id" ;			/* 采集任务ID */
//	public static final String ITEM_task_name = "task_name" ;			/* 计划任务名称 */
	
	public static final String ITEM_JHRW_LX = "scheduling_type" ;			/* 计划任务类型 */
	public static final String ITEM_JHRW_RQ = "scheduling_day" ;			/* 计划任务日期 */
	public static final String ITEM_JHRW_STAET_SJ = "start_time" ;			/* 计划任务开始时间 */
	public static final String ITEM_JHRW_END_SJ = "end_time" ;			/* 计划任务结束时间 */
	public static final String ITEM_JHRW_ZQ = "scheduling_day1" ;			/* 计划任务周期 */
	public static final String ITEM_JHRW_ZT = "scheduling_week" ;			/* 计划任务周天 */
	public static final String ITEM_JHRWZX_CS = "scheduling_count" ;		/* 计划任务执行次数 */
	public static final String ITEM_JHRWZX_JG = "interval_time" ;		/* 计划任务执行每次间隔时间 */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* 创建人 */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* 创建时间 */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* 修改人 */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* 修改时间 */
	public static final String ITEM_YX_BJ = "is_markup" ;				/* 有效标记 */
	public static final String JOB_CLASS_NAME = "job_class_name" ;				/* 触发调用的类名 */
	
	/**
	 * 构造函数
	 */
	public VoCollectTaskScheduling()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectTaskScheduling(DataBus value)
	{
		super(value);
	}
	
	/* 计划任务ID : String */
	public String gettask_scheduling_id()
	{
		return getValue( ITEM_task_scheduling_id );
	}

	public void settask_scheduling_id( String task_scheduling_id1 )
	{
		setValue( ITEM_task_scheduling_id, task_scheduling_id1 );
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

	
	/* 采集任务ID : String */
	public String getcollect_task_id()
	{
		return getValue( ITEM_collect_task_id );
	}

	public void setcollect_task_id( String collect_task_id)
	{
		setValue( ITEM_collect_task_id, collect_task_id );
	}
	

	/* 计划任务类型 : String */
	public String getJhrw_lx()
	{
		return getValue( ITEM_JHRW_LX );
	}

	public void setJhrw_lx( String jhrw_lx1 )
	{
		setValue( ITEM_JHRW_LX, jhrw_lx1 );
	}

	/* 计划任务日期 : String */
	public String getJhrw_rq()
	{
		return getValue( ITEM_JHRW_RQ );
	}

	public void setJhrw_rq( String jhrw_rq1 )
	{
		setValue( ITEM_JHRW_RQ, jhrw_rq1 );
	}

	/* 计划任务开始时间 : String */
	public String getJhrw_start_sj()
	{
		return getValue( ITEM_JHRW_STAET_SJ );
	}

	public void setJhrw_start_sj( String jhrw_sj1 )
	{
		setValue( ITEM_JHRW_STAET_SJ, jhrw_sj1 );
	}
	
	/* 计划任务结束时间 : String */
	public String getJhrw_end_sj()
	{
		return getValue( ITEM_JHRW_END_SJ );
	}

	public void setJhrw_end_sj( String jhrw_sj1 )
	{
		setValue( ITEM_JHRW_END_SJ, jhrw_sj1 );
	}

	/* 计划任务周期 : String */
	public String getJhrw_zq()
	{
		return getValue( ITEM_JHRW_ZQ );
	}

	public void setJhrw_zq( String jhrw_zq1 )
	{
		setValue( ITEM_JHRW_ZQ, jhrw_zq1 );
	}

	/* 计划任务周天 : String */
	public String getJhrw_zt()
	{
		return getValue( ITEM_JHRW_ZT );
	}

	public void setJhrw_zt( String jhrw_zt1 )
	{
		setValue( ITEM_JHRW_ZT, jhrw_zt1 );
	}


	/* 计划任务执行次数 : String */
	public String getJhrwzx_cs()
	{
		return getValue( ITEM_JHRWZX_CS );
	}

	public void setJhrwzx_cs( String jhrwzx_cs1 )
	{
		setValue( ITEM_JHRWZX_CS, jhrwzx_cs1 );
	}
	
	/* 计划任务执行间隔 : String */
	public String getJhrwzx_jg()
	{
		return getValue( ITEM_JHRWZX_JG );
	}

	public void setJhrwzx_jg( String jhrwzx_jg1 )
	{
		setValue( ITEM_JHRWZX_JG, jhrwzx_jg1 );
	}

	/* 创建人 : String */
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

	/* 修改人 : String */
	public String getLast_modify_id()
	{
		return getValue( ITEM_LAST_MODIFY_ID );
	}

	public void setLast_modify_id( String last_modify_id1 )
	{
		setValue( ITEM_LAST_MODIFY_ID, last_modify_id1 );
	}

	/* 修改时间 : String */
	public String getLast_modify_time()
	{
		return getValue( ITEM_LAST_MODIFY_TIME );
	}

	public void setLast_modify_time( String last_modify_time1 )
	{
		setValue( ITEM_LAST_MODIFY_TIME, last_modify_time1 );
	}

	/* 有效标记 : String */
	public String getYx_bj()
	{
		return getValue( ITEM_YX_BJ );
	}

	public void setYx_bj( String yx_bj1 )
	{
		setValue( ITEM_YX_BJ, yx_bj1 );
	}
	
	/* 触发调用的类名 : String */
	public String getJob_class_name()
	{
		return getValue( JOB_CLASS_NAME );
	}

	public void setJob_class_name( String job_class_name1 )
	{
		setValue( JOB_CLASS_NAME, job_class_name1 );
	}

}

