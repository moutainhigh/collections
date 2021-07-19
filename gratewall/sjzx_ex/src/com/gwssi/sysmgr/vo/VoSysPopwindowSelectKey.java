package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_popwindow]的数据对象类
 * @author Administrator
 *
 */
public class VoSysPopwindowSelectKey extends VoBase
{
	private static final long serialVersionUID = 200907271007430003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_POPWINDOW_ID = "sys_popwindow_id" ;	/* 系统任务ID */
	public static final String ITEM_EXPIRE_DATE = "expire_date" ;	/* 到期日期 */
	
	/**
	 * 构造函数
	 */
	public VoSysPopwindowSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysPopwindowSelectKey(DataBus value)
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

	/* 到期日期 : String */
	public String getExpire_date()
	{
		return getValue( ITEM_EXPIRE_DATE );
	}

	public void setExpire_date( String expire_date1 )
	{
		setValue( ITEM_EXPIRE_DATE, expire_date1 );
	}

}

