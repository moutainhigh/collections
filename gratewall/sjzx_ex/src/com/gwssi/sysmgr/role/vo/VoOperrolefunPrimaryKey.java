package com.gwssi.sysmgr.role.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[operrolefun]的数据对象类
 * @author Administrator
 *
 */
public class VoOperrolefunPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200709111111520004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_ROLEACCID = "roleaccid" ;		/* 角色权限代码 */
	
	/**
	 * 构造函数
	 */
	public VoOperrolefunPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoOperrolefunPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 角色权限代码 : String */
	public String getRoleaccid()
	{
		return getValue( ITEM_ROLEACCID );
	}

	public void setRoleaccid( String roleaccid1 )
	{
		setValue( ITEM_ROLEACCID, roleaccid1 );
	}

}

