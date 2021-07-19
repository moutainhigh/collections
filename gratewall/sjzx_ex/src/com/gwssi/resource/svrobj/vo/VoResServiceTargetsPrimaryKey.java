package com.gwssi.resource.svrobj.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_service_targets]的数据对象类
 * @author Administrator
 *
 */
public class VoResServiceTargetsPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303131040540004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* 服务对象ID */
	
	/**
	 * 构造函数
	 */
	public VoResServiceTargetsPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResServiceTargetsPrimaryKey(DataBus value)
	{
		super(value);
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

}

