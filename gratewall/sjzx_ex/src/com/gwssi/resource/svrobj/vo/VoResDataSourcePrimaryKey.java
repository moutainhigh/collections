package com.gwssi.resource.svrobj.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_data_source]的数据对象类
 * @author Administrator
 *
 */
public class VoResDataSourcePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303141052490004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DATA_SOURCE_ID = "data_source_id" ;	/* 数据源ID */
	
	/**
	 * 构造函数
	 */
	public VoResDataSourcePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResDataSourcePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 数据源ID : String */
	public String getData_source_id()
	{
		return getValue( ITEM_DATA_SOURCE_ID );
	}

	public void setData_source_id( String data_source_id1 )
	{
		setValue( ITEM_DATA_SOURCE_ID, data_source_id1 );
	}

}

