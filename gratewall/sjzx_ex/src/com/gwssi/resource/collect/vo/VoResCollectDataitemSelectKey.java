package com.gwssi.resource.collect.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_collect_dataitem]的数据对象类
 * @author Administrator
 *
 */
public class VoResCollectDataitemSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303221103430007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_COLLECT_TABLE_ID = "collect_table_id" ;	/* 采集数据表ID */
	public static final String ITEM_DATAITEM_NAME_EN = "dataitem_name_en" ;	/* 数据项名称 */
	public static final String ITEM_DATAITEM_NAME_CN = "dataitem_name_cn" ;	/* 数据项中文名称 */
	public static final String ITEM_DATAITEM_TYPE = "dataitem_type" ;	/* 数据项类型 */
	public static final String ITEM_CODE_TABLE = "code_table" ;		/* 对应代码表 */
	
	/**
	 * 构造函数
	 */
	public VoResCollectDataitemSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResCollectDataitemSelectKey(DataBus value)
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

	/* 数据项名称 : String */
	public String getDataitem_name_en()
	{
		return getValue( ITEM_DATAITEM_NAME_EN );
	}

	public void setDataitem_name_en( String dataitem_name_en1 )
	{
		setValue( ITEM_DATAITEM_NAME_EN, dataitem_name_en1 );
	}

	/* 数据项中文名称 : String */
	public String getDataitem_name_cn()
	{
		return getValue( ITEM_DATAITEM_NAME_CN );
	}

	public void setDataitem_name_cn( String dataitem_name_cn1 )
	{
		setValue( ITEM_DATAITEM_NAME_CN, dataitem_name_cn1 );
	}

	/* 数据项类型 : String */
	public String getDataitem_type()
	{
		return getValue( ITEM_DATAITEM_TYPE );
	}

	public void setDataitem_type( String dataitem_type1 )
	{
		setValue( ITEM_DATAITEM_TYPE, dataitem_type1 );
	}

	/* 对应代码表 : String */
	public String getCode_table()
	{
		return getValue( ITEM_CODE_TABLE );
	}

	public void setCode_table( String code_table1 )
	{
		setValue( ITEM_CODE_TABLE, code_table1 );
	}

}

