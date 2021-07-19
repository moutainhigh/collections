package com.gwssi.share.service.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_service]的数据对象类
 * @author Administrator
 *
 */
public class VoShareServicePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303261650000008L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* 服务ID */
	
	/**
	 * 构造函数
	 */
	public VoShareServicePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareServicePrimaryKey(DataBus value)
	{
		super(value);
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

}

