package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[etl_table_count]的数据对象类
 * @author Administrator
 *
 */
public class VoEtlTableCountPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200810301721050004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_NAME = "sys_name" ;			/* 主题名称 */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* 表名 */
	public static final String ITEM_ETL_DATE = "etl_date" ;			/* 统计日期 */
	
	/**
	 * 构造函数
	 */
	public VoEtlTableCountPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEtlTableCountPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 主题名称 : String */
	public String getSys_name()
	{
		return getValue( ITEM_SYS_NAME );
	}

	public void setSys_name( String sys_name1 )
	{
		setValue( ITEM_SYS_NAME, sys_name1 );
	}

	/* 表名 : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* 统计日期 : String */
	public String getEtl_date()
	{
		return getValue( ITEM_ETL_DATE );
	}

	public void setEtl_date( String etl_date1 )
	{
		setValue( ITEM_ETL_DATE, etl_date1 );
	}

}

