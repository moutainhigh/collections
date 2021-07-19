package com.gwssi.sysmgr.role.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[operrolefun]的数据对象类
 * @author Administrator
 *
 */
public class VoOperrolefunSelectKey extends VoBase
{
	private static final long serialVersionUID = 200709111111510003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_ROLEID = "roleid" ;				/* 角色编号 */
	
	/**
	 * 构造函数
	 */
	public VoOperrolefunSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoOperrolefunSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 角色编号 : String */
	public String getRoleid()
	{
		return getValue( ITEM_ROLEID );
	}

	public void setRoleid( String roleid1 )
	{
		setValue( ITEM_ROLEID, roleid1 );
	}

}

