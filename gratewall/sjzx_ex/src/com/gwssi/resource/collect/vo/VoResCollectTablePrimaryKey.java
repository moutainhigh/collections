package com.gwssi.resource.collect.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_collect_table]的数据对象类
 * @author Administrator
 *
 */
public class VoResCollectTablePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303221045510004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_COLLECT_TABLE_ID = "collect_table_id" ;	/* 采集数据表ID */
	
	/**
	 * 构造函数
	 */
	public VoResCollectTablePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResCollectTablePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 采集数据表ID : String */
	public String getCollect_table_id()
	{
		return getValue( ITEM_COLLECT_TABLE_ID );
	}

	public void setCollect_table_id( String collect_table_id1 )
	{
		setValue( ITEM_COLLECT_TABLE_ID, collect_table_id1 );
	}

}

