package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_limit]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrLimitPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201207121643140004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_LIMIT_ID = "sys_svr_limit_id" ;	/* 用户服务限制主键 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrLimitPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrLimitPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 用户服务限制主键 : String */
	public String getSys_svr_limit_id()
	{
		return getValue( ITEM_SYS_SVR_LIMIT_ID );
	}

	public void setSys_svr_limit_id( String sys_svr_limit_id1 )
	{
		setValue( ITEM_SYS_SVR_LIMIT_ID, sys_svr_limit_id1 );
	}

}

