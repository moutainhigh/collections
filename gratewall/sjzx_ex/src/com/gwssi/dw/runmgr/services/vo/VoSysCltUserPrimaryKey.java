package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_clt_user]的数据对象类
 * @author Administrator
 *
 */
public class VoSysCltUserPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809052041060004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_CLT_USER_ID = "sys_clt_user_id" ;	
	
	/**
	 * 构造函数
	 */
	public VoSysCltUserPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysCltUserPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	public String getSys_clt_user_id()
	{
		return getValue( ITEM_SYS_CLT_USER_ID );
	}

	public void setSys_clt_user_id( String sys_clt_user_id1 )
	{
		setValue( ITEM_SYS_CLT_USER_ID, sys_clt_user_id1 );
	}

}

