package com.gwssi.log.sharelog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_log]的数据对象类
 * @author Administrator
 *
 */
public class VoShareLogPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304031121530004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_LOG_ID = "log_id" ;				/* 主键 */
	
	/**
	 * 构造函数
	 */
	public VoShareLogPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareLogPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 主键 : String */
	public String getLog_id()
	{
		return getValue( ITEM_LOG_ID );
	}

	public void setLog_id( String log_id1 )
	{
		setValue( ITEM_LOG_ID, log_id1 );
	}

}

