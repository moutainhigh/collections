package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_log]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrLogPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200811281608000004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_LOG_ID = "sys_svr_log_id" ;	/* 共享服务日志编号 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrLogPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrLogPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 共享服务日志编号 : String */
	public String getSys_svr_log_id()
	{
		return getValue( ITEM_SYS_SVR_LOG_ID );
	}

	public void setSys_svr_log_id( String sys_svr_log_id1 )
	{
		setValue( ITEM_SYS_SVR_LOG_ID, sys_svr_log_id1 );
	}

}

