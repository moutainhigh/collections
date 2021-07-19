package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[etl_table_count]的数据对象类
 * @author Administrator
 *
 */
public class VoEtlTableCount extends VoBase
{
	private static final long serialVersionUID = 200810301721040002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_NAME = "sys_name" ;			/* 主题名称 */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* 表中文名 */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* 表名 */
	public static final String ITEM_ENT_TYPE = "ent_type" ;			/* 主体类型 */
	public static final String ITEM_ENT_STATS = "ent_stats" ;		/* 主体状态 */
	public static final String ITEM_C_COUNT = "c_count" ;			/* 表数据量 */
	public static final String ITEM_I_COUNT = "i_count" ;			/* 较上次统计增量 */
	public static final String ITEM_ETL_DATE = "etl_date" ;			/* 统计日期 */
	public static final String ITEM_LAST_ETL_DATE = "last_etl_date" ;	/* 上次统计日期 */
	public static final String ITEM_SYS_ORDER = "sys_order" ;		/* 主题序号 */
	public static final String ITEM_TABLE_ORDER = "table_order" ;	/* 表序号 */
	
	/**
	 * 构造函数
	 */
	public VoEtlTableCount()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEtlTableCount(DataBus value)
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

	/* 表中文名 : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
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

	/* 主体类型 : String */
	public String getEnt_type()
	{
		return getValue( ITEM_ENT_TYPE );
	}

	public void setEnt_type( String ent_type1 )
	{
		setValue( ITEM_ENT_TYPE, ent_type1 );
	}

	/* 主体状态 : String */
	public String getEnt_stats()
	{
		return getValue( ITEM_ENT_STATS );
	}

	public void setEnt_stats( String ent_stats1 )
	{
		setValue( ITEM_ENT_STATS, ent_stats1 );
	}

	/* 表数据量 : String */
	public String getC_count()
	{
		return getValue( ITEM_C_COUNT );
	}

	public void setC_count( String c_count1 )
	{
		setValue( ITEM_C_COUNT, c_count1 );
	}

	/* 较上次统计增量 : String */
	public String getI_count()
	{
		return getValue( ITEM_I_COUNT );
	}

	public void setI_count( String i_count1 )
	{
		setValue( ITEM_I_COUNT, i_count1 );
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

	/* 上次统计日期 : String */
	public String getLast_etl_date()
	{
		return getValue( ITEM_LAST_ETL_DATE );
	}

	public void setLast_etl_date( String last_etl_date1 )
	{
		setValue( ITEM_LAST_ETL_DATE, last_etl_date1 );
	}

	/* 主题序号 : String */
	public String getSys_order()
	{
		return getValue( ITEM_SYS_ORDER );
	}

	public void setSys_order( String sys_order1 )
	{
		setValue( ITEM_SYS_ORDER, sys_order1 );
	}

	/* 表序号 : String */
	public String getTable_order()
	{
		return getValue( ITEM_TABLE_ORDER );
	}

	public void setTable_order( String table_order1 )
	{
		setValue( ITEM_TABLE_ORDER, table_order1 );
	}

}

