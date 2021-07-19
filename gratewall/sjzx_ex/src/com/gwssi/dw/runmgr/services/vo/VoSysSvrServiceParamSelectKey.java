package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_service_param]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrServiceParamSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809051535250007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* 共享服务编号 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrServiceParamSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrServiceParamSelectKey(DataBus value)
	{
		super(value);
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

}

