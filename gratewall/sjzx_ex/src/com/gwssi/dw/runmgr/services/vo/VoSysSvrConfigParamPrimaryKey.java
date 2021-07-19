package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_config_param]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrConfigParamPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809101030500004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_CONFIG_PARAM_ID = "sys_svr_config_param_id" ;	/* 共享服务配置参数编号 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrConfigParamPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrConfigParamPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 共享服务配置参数编号 : String */
	public String getSys_svr_config_param_id()
	{
		return getValue( ITEM_SYS_SVR_CONFIG_PARAM_ID );
	}

	public void setSys_svr_config_param_id( String sys_svr_config_param_id1 )
	{
		setValue( ITEM_SYS_SVR_CONFIG_PARAM_ID, sys_svr_config_param_id1 );
	}

}

