package com.gwssi.share.interfaces.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_interface]的数据对象类
 * @author Administrator
 *
 */
public class VoShareInterfaceSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303121022120003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_INTERFACE_NAME = "interface_name" ;	/* 接口名称 */
	public static final String ITEM_INTERFACE_STATE = "interface_state" ;	/* 接口状态 */
	
	/**
	 * 构造函数
	 */
	public VoShareInterfaceSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareInterfaceSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 接口名称 : String */
	public String getInterface_name()
	{
		return getValue( ITEM_INTERFACE_NAME );
	}

	public void setInterface_name( String interface_name1 )
	{
		setValue( ITEM_INTERFACE_NAME, interface_name1 );
	}

	/* 接口状态 : String */
	public String getInterface_state()
	{
		return getValue( ITEM_INTERFACE_STATE );
	}

	public void setInterface_state( String interface_state1 )
	{
		setValue( ITEM_INTERFACE_STATE, interface_state1 );
	}

}

