package com.gwssi.resource.svrobj.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_service_targets]的数据对象类
 * @author Administrator
 *
 */
public class VoResServiceTargetsSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303131040540003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SERVICE_TARGETS_NAME = "service_targets_name" ;	/* 服务对象名称 */
	public static final String ITEM_SERVICE_TARGETS_TYPE = "service_targets_type" ;	/* 服务对象类型 */
	public static final String ITEM_SERVICE_STATUS = "service_status" ;	/* 服务状态 */
	public static final String ITEM_CREATED_TIME_START = "created_time_start" ;	/* 创建时间 */
	public static final String ITEM_CREATED_TIME_END = "created_time_end" ;	/* 创建时间 */
	
	/**
	 * 构造函数
	 */
	public VoResServiceTargetsSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResServiceTargetsSelectKey(DataBus value)
	{
		super(value);
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

	/* 服务对象类型 : String */
	public String getService_targets_type()
	{
		return getValue( ITEM_SERVICE_TARGETS_TYPE );
	}

	public void setService_targets_type( String service_targets_type1 )
	{
		setValue( ITEM_SERVICE_TARGETS_TYPE, service_targets_type1 );
	}

	/* 服务状态 : String */
	public String getService_status()
	{
		return getValue( ITEM_SERVICE_STATUS );
	}

	public void setService_status( String service_status1 )
	{
		setValue( ITEM_SERVICE_STATUS, service_status1 );
	}

	/* 创建时间 : String */
	public String getCreated_time_start()
	{
		return getValue( ITEM_CREATED_TIME_START );
	}

	public void setCreated_time_start( String created_time_start1 )
	{
		setValue( ITEM_CREATED_TIME_START, created_time_start1 );
	}

	/* 创建时间 : String */
	public String getCreated_time_end()
	{
		return getValue( ITEM_CREATED_TIME_END );
	}

	public void setCreated_time_end( String created_time_end1 )
	{
		setValue( ITEM_CREATED_TIME_END, created_time_end1 );
	}

}

