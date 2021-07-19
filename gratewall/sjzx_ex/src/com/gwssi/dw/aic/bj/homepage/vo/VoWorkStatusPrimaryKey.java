package com.gwssi.dw.aic.bj.homepage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[work_status]的数据对象类
 * @author Administrator
 *
 */
public class VoWorkStatusPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200812041106360004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_WORK_STATUS_ID = "work_status_id" ;	/* 工作状态ID */
	
	/**
	 * 构造函数
	 */
	public VoWorkStatusPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoWorkStatusPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 工作状态ID : String */
	public String getWork_status_id()
	{
		return getValue( ITEM_WORK_STATUS_ID );
	}

	public void setWork_status_id( String work_status_id1 )
	{
		setValue( ITEM_WORK_STATUS_ID, work_status_id1 );
	}

}

