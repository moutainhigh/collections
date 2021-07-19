package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_user]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrUserPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200805050829180004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* 服务对象编号 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrUserPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrUserPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 服务对象编号 : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

}

