package com.gwssi.share.interfaces.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_interface]的数据对象类
 * @author Administrator
 *
 */
public class VoShareInterfacePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303121022120004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_INTERFACE_ID = "interface_id" ;	/* 接口ID */
	
	/**
	 * 构造函数
	 */
	public VoShareInterfacePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareInterfacePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 接口ID : String */
	public String getInterface_id()
	{
		return getValue( ITEM_INTERFACE_ID );
	}

	public void setInterface_id( String interface_id1 )
	{
		setValue( ITEM_INTERFACE_ID, interface_id1 );
	}

}

