package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_task]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectTask extends VoBase
{
	private static final long serialVersionUID = 201304101123030002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_COLLECT_TASK_ID = "collect_task_id" ;	/* 采集任务ID */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* 所属服务对象 */
	public static final String ITEM_DATA_SOURCE_ID = "data_source_id" ;	/* 数据源 */
	public static final String ITEM_TASK_NAME = "task_name" ;		/* 任务名称 */
	public static final String ITEM_COLLECT_TYPE = "collect_type" ;	/* 采集类型 */
	public static final String ITEM_TASK_DESCRIPTION = "task_description" ;	/* 任务描述 */
	public static final String ITEM_RECORD = "record" ;				/* 备案说明 */
	public static final String ITEM_TASK_STATUS = "task_status" ;	/* 任务状态 */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* 有效标记 */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* 创建人ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* 创建时间 */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* 最后修改人ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* 最后修改时间 */
	public static final String ITEM_FJ_FK = "fj_fk" ;				/* 附件id */
	public static final String ITEM_FJMC = "fjmc" ;					/* 附件名称 */
	public static final String ITEM_DELNAMES = "delNAMEs";			/* delNAMEs */
	public static final String ITEM_DELIDS = "delIDs";			/* delIDs */
	
	/**
	 * 构造函数
	 */
	public VoCollectTask()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectTask(DataBus value)
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

	/* 所属服务对象 : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* 数据源 : String */
	public String getData_source_id()
	{
		return getValue( ITEM_DATA_SOURCE_ID );
	}

	public void setData_source_id( String data_source_id1 )
	{
		setValue( ITEM_DATA_SOURCE_ID, data_source_id1 );
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

	/* 任务描述 : String */
	public String getTask_description()
	{
		return getValue( ITEM_TASK_DESCRIPTION );
	}

	public void setTask_description( String task_description1 )
	{
		setValue( ITEM_TASK_DESCRIPTION, task_description1 );
	}

	/* 备案说明 : String */
	public String getRecord()
	{
		return getValue( ITEM_RECORD );
	}

	public void setRecord( String record1 )
	{
		setValue( ITEM_RECORD, record1 );
	}

	/* 任务状态 : String */
	public String getTask_status()
	{
		return getValue( ITEM_TASK_STATUS );
	}

	public void setTask_status( String task_status1 )
	{
		setValue( ITEM_TASK_STATUS, task_status1 );
	}

	/* 有效标记 : String */
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

