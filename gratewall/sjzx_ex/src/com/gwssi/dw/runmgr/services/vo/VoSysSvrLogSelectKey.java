package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_log]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrLogSelectKey extends VoBase
{
	private static final long serialVersionUID = 200811281608000003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* 使用共享服务编号 */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* 共享服务编号 */
	public static final String ITEM_EXECUTE_START_TIME = "execute_start_time" ;	/* 执行开始时间 */
	public static final String ITEM_EXECUTE_END_TIME = "execute_end_time" ;	/* 执行结束时间 */
	public static final String ITEM_STATE = "state" ;				/* 执行结果 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrLogSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrLogSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 使用共享服务编号 : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

	/* 共享服务编号 : String */
	public String getSys_svr_service_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_ID );
	}

	public void setSys_svr_service_id( String sys_svr_service_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_ID, sys_svr_service_id1 );
	}

	/* 执行开始时间 : String */
	public String getExecute_start_time()
	{
		return getValue( ITEM_EXECUTE_START_TIME );
	}

	public void setExecute_start_time( String execute_start_time1 )
	{
		setValue( ITEM_EXECUTE_START_TIME, execute_start_time1 );
	}

	/* 执行结束时间 : String */
	public String getExecute_end_time()
	{
		return getValue( ITEM_EXECUTE_END_TIME );
	}

	public void setExecute_end_time( String execute_end_time1 )
	{
		setValue( ITEM_EXECUTE_END_TIME, execute_end_time1 );
	}

	/* 执行结果 : String */
	public String getState()
	{
		return getValue( ITEM_STATE );
	}

	public void setState( String state1 )
	{
		setValue( ITEM_STATE, state1 );
	}

}

