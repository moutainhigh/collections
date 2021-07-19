package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_system_semantic]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSystemSemanticPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200804181523310004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_ID = "sys_id" ;				/* 系统编码 */
	
	/**
	 * 构造函数
	 */
	public VoSysSystemSemanticPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSystemSemanticPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 系统编码 : String */
	public String getSys_id()
	{
		return getValue( ITEM_SYS_ID );
	}

	public void setSys_id( String sys_id1 )
	{
		setValue( ITEM_SYS_ID, sys_id1 );
	}

}

