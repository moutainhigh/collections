package com.gwssi.share.service.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_service]的数据对象类
 * @author Administrator
 *
 */
public class VoShareServiceSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303261650000007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* 所属服务对象 */
	public static final String ITEM_SERVICE_NAME = "service_name" ;	/* 服务名称 */
	public static final String ITEM_SERVICE_TYPE = "service_type" ;	/* 服务类型 */
	public static final String ITEM_SERVICE_STATE = "service_state" ;	/* 服务状态 */
	public static final String ITEM_INTERFACE_ID = "interface_id" ;	/* 接口ID */
	
	/**
	 * 构造函数
	 */
	public VoShareServiceSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareServiceSelectKey(DataBus value)
	{
		super(value);
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

	/* 服务名称 : String */
	public String getService_name()
	{
		return getValue( ITEM_SERVICE_NAME );
	}

	public void setService_name( String service_name1 )
	{
		setValue( ITEM_SERVICE_NAME, service_name1 );
	}

	/* 服务类型 : String */
	public String getService_type()
	{
		return getValue( ITEM_SERVICE_TYPE );
	}

	public void setService_type( String service_type1 )
	{
		setValue( ITEM_SERVICE_TYPE, service_type1 );
	}

	/* 服务状态 : String */
	public String getService_state()
	{
		return getValue( ITEM_SERVICE_STATE );
	}

	public void setService_state( String service_state1 )
	{
		setValue( ITEM_SERVICE_STATE, service_state1 );
	}

	/* 接口ID : String */
	public String getInterface_id()
	{
		return getValue( ITEM_INTERFACE_ID );
	}

	public void setInterface_id( String interface_id1 )
	{
		setValue( ITEM_INTERFACE_ID, interface_id1 );
	}

}

