package com.gwssi.resource.collect.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_collect_table]的数据对象类
 * @author Administrator
 *
 */
public class VoResCollectTableSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303221045510003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* 服务对象ID */
	public static final String ITEM_TABLE_TYPE = "table_type" ;		/* 表类型 */
	public static final String ITEM_TABLE_NAME_EN = "table_name_en" ;	/* 表名称 */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* 表中文名称 */
	
	/**
	 * 构造函数
	 */
	public VoResCollectTableSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResCollectTableSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 服务对象ID : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* 表类型 : String */
	public String getTable_type()
	{
		return getValue( ITEM_TABLE_TYPE );
	}

	public void setTable_type( String table_type1 )
	{
		setValue( ITEM_TABLE_TYPE, table_type1 );
	}

	/* 表名称 : String */
	public String getTable_name_en()
	{
		return getValue( ITEM_TABLE_NAME_EN );
	}

	public void setTable_name_en( String table_name_en1 )
	{
		setValue( ITEM_TABLE_NAME_EN, table_name_en1 );
	}

	/* 表中文名称 : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

}

