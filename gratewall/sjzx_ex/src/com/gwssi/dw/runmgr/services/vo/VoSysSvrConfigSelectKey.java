package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_config]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrConfigSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809081645150003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* 服务对象编码 */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* 共享服务编码 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrConfigSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrConfigSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 服务对象编码 : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

	/* 共享服务编码 : String */
	public String getSys_svr_service_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_ID );
	}

	public void setSys_svr_service_id( String sys_svr_service_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_ID, sys_svr_service_id1 );
	}

}

