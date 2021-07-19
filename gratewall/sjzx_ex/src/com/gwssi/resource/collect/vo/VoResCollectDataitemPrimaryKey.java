package com.gwssi.resource.collect.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_collect_dataitem]的数据对象类
 * @author Administrator
 *
 */
public class VoResCollectDataitemPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303221103430008L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_COLLECT_DATAITEM_ID = "collect_dataitem_id" ;	/* 采集数据项ID */
	
	/**
	 * 构造函数
	 */
	public VoResCollectDataitemPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResCollectDataitemPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 采集数据项ID : String */
	public String getCollect_dataitem_id()
	{
		return getValue( ITEM_COLLECT_DATAITEM_ID );
	}

	public void setCollect_dataitem_id( String collect_dataitem_id1 )
	{
		setValue( ITEM_COLLECT_DATAITEM_ID, collect_dataitem_id1 );
	}

}

