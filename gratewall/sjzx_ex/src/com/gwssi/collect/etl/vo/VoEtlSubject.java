package com.gwssi.collect.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[etl_subject]的数据对象类
 * @author Administrator
 *
 */
public class VoEtlSubject extends VoBase
{
	private static final long serialVersionUID = 201308130950380002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SUBJ_ID = "subj_id" ;			/* 主键ID */
	public static final String ITEM_SUBJ_NAME = "subj_name" ;		/* 工作包名称 */
	public static final String ITEM_SUBJ_DESC = "subj_desc" ;		/* 工作包描述 */
	public static final String ITEM_IS_SHOW = "is_show" ;			/* 是否显示 */
	public static final String ITEM_SUBJ_SORT = "subj_sort" ;		/* 排序号 */
	public static final String ITEM_START_TIME = "start_time" ;		/* 开始执行时间 */
	public static final String ITEM_INTEVAL = "inteval" ;			/* 运行周期 */
	public static final String ITEM_WORKFLOW_NAME = "workflow_name" ;	/* 任务名称 */
	public static final String ITEM_WORKFLOW_DESC = "workflow_desc" ;	/* 任务描述 */
	public static final String ITEM_ADD_TYPE = "add_type" ;			/* 数据处理类型 */
	public static final String ITEM_WORKFLOW_ID = "workflow_id" ;	/* 任务ID */
	public static final String ITEM_SCHEDULE_JSON = "schedule_json" ;			/* 运行周期实际数据 */
	
	/**
	 * 构造函数
	 */
	public VoEtlSubject()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEtlSubject(DataBus value)
	{
		super(value);
	}
	
	/* 主键ID : String */
	public String getSubj_id()
	{
		return getValue( ITEM_SUBJ_ID );
	}

	public void setSubj_id( String subj_id1 )
	{
		setValue( ITEM_SUBJ_ID, subj_id1 );
	}

	/* 工作包名称 : String */
	public String getSubj_name()
	{
		return getValue( ITEM_SUBJ_NAME );
	}

	public void setSubj_name( String subj_name1 )
	{
		setValue( ITEM_SUBJ_NAME, subj_name1 );
	}

	/* 工作包描述 : String */
	public String getSubj_desc()
	{
		return getValue( ITEM_SUBJ_DESC );
	}

	public void setSubj_desc( String subj_desc1 )
	{
		setValue( ITEM_SUBJ_DESC, subj_desc1 );
	}

	/* 是否显示 : String */
	public String getIs_show()
	{
		return getValue( ITEM_IS_SHOW );
	}

	public void setIs_show( String is_show1 )
	{
		setValue( ITEM_IS_SHOW, is_show1 );
	}

	/* 排序号 : String */
	public String getSubj_sort()
	{
		return getValue( ITEM_SUBJ_SORT );
	}

	public void setSubj_sort( String subj_sort1 )
	{
		setValue( ITEM_SUBJ_SORT, subj_sort1 );
	}

	/* 开始执行时间 : String */
	public String getStart_time()
	{
		return getValue( ITEM_START_TIME );
	}

	public void setStart_time( String start_time1 )
	{
		setValue( ITEM_START_TIME, start_time1 );
	}

	/* 运行周期 : String */
	public String getInteval()
	{
		return getValue( ITEM_INTEVAL );
	}

	public void setInteval( String inteval1 )
	{
		setValue( ITEM_INTEVAL, inteval1 );
	}

	/* 任务名称 : String */
	public String getWorkflow_name()
	{
		return getValue( ITEM_WORKFLOW_NAME );
	}

	public void setWorkflow_name( String workflow_name1 )
	{
		setValue( ITEM_WORKFLOW_NAME, workflow_name1 );
	}

	/* 任务描述 : String */
	public String getWorkflow_desc()
	{
		return getValue( ITEM_WORKFLOW_DESC );
	}

	public void setWorkflow_desc( String workflow_desc1 )
	{
		setValue( ITEM_WORKFLOW_DESC, workflow_desc1 );
	}

	/* 数据处理类型 : String */
	public String getAdd_type()
	{
		return getValue( ITEM_ADD_TYPE );
	}

	public void setAdd_type( String add_type1 )
	{
		setValue( ITEM_ADD_TYPE, add_type1 );
	}

	/* 任务ID : String */
	public String getWorkflow_id()
	{
		return getValue( ITEM_WORKFLOW_ID );
	}

	public void setWorkflow_id( String workflow_id1 )
	{
		setValue( ITEM_WORKFLOW_ID, workflow_id1 );
	}

	
	/* 运行周期原始数据 : String */
	public String getSchedule_json()
	{
		return getValue( ITEM_SCHEDULE_JSON );
	}

	public void setSchedule_json( String Schedule_json1 )
	{
		setValue( ITEM_SCHEDULE_JSON, Schedule_json1);
	}
}

