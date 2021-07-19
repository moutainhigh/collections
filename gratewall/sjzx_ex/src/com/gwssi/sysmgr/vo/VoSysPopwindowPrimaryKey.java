package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_popwindow]的数据对象类
 * @author Administrator
 *
 */
public class VoSysPopwindowPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200907271007440004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_POPWINDOW_ID = "sys_popwindow_id" ;	/* 系统任务ID */
	
	/**
	 * 构造函数
	 */
	public VoSysPopwindowPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysPopwindowPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 系统任务ID : String */
	public String getSys_popwindow_id()
	{
		return getValue( ITEM_SYS_POPWINDOW_ID );
	}

	public void setSys_popwindow_id( String sys_popwindow_id1 )
	{
		setValue( ITEM_SYS_POPWINDOW_ID, sys_popwindow_id1 );
	}

}

