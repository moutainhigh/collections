package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_limit]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrLimitSelectKey extends VoBase
{
	private static final long serialVersionUID = 201207121643140003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* 用户ID */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* 服务ID */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrLimitSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrLimitSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 用户ID : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

	/* 服务ID : String */
	public String getSys_svr_service_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_ID );
	}

	public void setSys_svr_service_id( String sys_svr_service_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_ID, sys_svr_service_id1 );
	}

}

