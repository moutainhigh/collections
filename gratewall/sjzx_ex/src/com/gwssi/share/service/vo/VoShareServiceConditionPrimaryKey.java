package com.gwssi.share.service.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_service_condition]的数据对象类
 * @author Administrator
 *
 */
public class VoShareServiceConditionPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304021334180004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CONDITION_ID = "condition_id" ;	/* 服务查询条件ID */
	
	/**
	 * 构造函数
	 */
	public VoShareServiceConditionPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareServiceConditionPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 服务查询条件ID : String */
	public String getCondition_id()
	{
		return getValue( ITEM_CONDITION_ID );
	}

	public void setCondition_id( String condition_id1 )
	{
		setValue( ITEM_CONDITION_ID, condition_id1 );
	}

}

