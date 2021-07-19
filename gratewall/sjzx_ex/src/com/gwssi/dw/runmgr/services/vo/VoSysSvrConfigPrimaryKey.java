package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_config]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrConfigPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809081645150004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_CONFIG_ID = "sys_svr_config_id" ;	/* 共享服务配置编码 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrConfigPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrConfigPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 共享服务配置编码 : String */
	public String getSys_svr_config_id()
	{
		return getValue( ITEM_SYS_SVR_CONFIG_ID );
	}

	public void setSys_svr_config_id( String sys_svr_config_id1 )
	{
		setValue( ITEM_SYS_SVR_CONFIG_ID, sys_svr_config_id1 );
	}

}

