package com.gwssi.share.service.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_service_condition]的数据对象类
 * @author Administrator
 *
 */
public class VoShareServiceConditionSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304021334180003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* 服务ID */
	public static final String ITEM_NEED_INPUT = "need_input" ;		/* 是否需要参数 */
	
	/**
	 * 构造函数
	 */
	public VoShareServiceConditionSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareServiceConditionSelectKey(DataBus value)
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

	/* 是否需要参数 : String */
	public String getNeed_input()
	{
		return getValue( ITEM_NEED_INPUT );
	}

	public void setNeed_input( String need_input1 )
	{
		setValue( ITEM_NEED_INPUT, need_input1 );
	}

}

