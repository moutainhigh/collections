package com.gwssi.resource.svrobj.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_data_source]的数据对象类
 * @author Administrator
 *
 */
public class VoResDataSourceSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303141052490003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DATA_SOURCE_NAME = "data_source_name" ;	/* 数据源名称 */
	public static final String ITEM_DATA_SOURCE_TYPE = "data_source_type" ;	/* 数据源类型 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* 服务对象ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* 创建时间 */
	
	/**
	 * 构造函数
	 */
	public VoResDataSourceSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResDataSourceSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 数据源名称 : String */
	public String getData_source_name()
	{
		return getValue( ITEM_DATA_SOURCE_NAME );
	}

	public void setData_source_name( String data_source_name1 )
	{
		setValue( ITEM_DATA_SOURCE_NAME, data_source_name1 );
	}

	/* 数据源类型 : String */
	public String getData_source_type()
	{
		return getValue( ITEM_DATA_SOURCE_TYPE );
	}

	public void setData_source_type( String data_source_type1 )
	{
		setValue( ITEM_DATA_SOURCE_TYPE, data_source_type1 );
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

	/* 创建时间 : String */
	public String getCreated_time()
	{
		return getValue( ITEM_CREATED_TIME );
	}

	public void setCreated_time( String created_time1 )
	{
		setValue( ITEM_CREATED_TIME, created_time1 );
	}

}

