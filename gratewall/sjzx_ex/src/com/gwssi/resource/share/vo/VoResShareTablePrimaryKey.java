package com.gwssi.resource.share.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_share_table]的数据对象类
 * @author Administrator
 *
 */
public class VoResShareTablePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303191807570004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SHARE_TABLE_ID = "share_table_id" ;	/* 共享表ID */
	
	/**
	 * 构造函数
	 */
	public VoResShareTablePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResShareTablePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 共享表ID : String */
	public String getShare_table_id()
	{
		return getValue( ITEM_SHARE_TABLE_ID );
	}

	public void setShare_table_id( String share_table_id1 )
	{
		setValue( ITEM_SHARE_TABLE_ID, share_table_id1 );
	}

}

