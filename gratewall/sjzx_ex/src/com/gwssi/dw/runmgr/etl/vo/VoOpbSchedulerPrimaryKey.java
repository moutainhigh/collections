package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[opb_scheduler]的数据对象类
 * @author Administrator
 *
 */
public class VoOpbSchedulerPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200806031123550004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SCHEDULER_ID = "scheduler_id" ;	/* 主键 */
	
	/**
	 * 构造函数
	 */
	public VoOpbSchedulerPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoOpbSchedulerPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 主键 : String */
	public String getScheduler_id()
	{
		return getValue( ITEM_SCHEDULER_ID );
	}

	public void setScheduler_id( String scheduler_id1 )
	{
		setValue( ITEM_SCHEDULER_ID, scheduler_id1 );
	}

}

