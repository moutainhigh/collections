package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_advanced_query]的数据对象类
 * @author Administrator
 *
 */
public class VoSysAdvancedQuery extends VoBase
{
	private static final long serialVersionUID = 200806261658150002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_ADVANCED_QUERY_ID = "sys_advanced_query_id" ;	/* 高级查询编号 */
	public static final String ITEM_NAME = "name" ;					/* 高级查询主题名称 */
	public static final String ITEM_SYS_ADVANCED_QUERY_TABLE_ID = "sys_advanced_query_table_id" ;	/* 高级查询关联表编号 */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* 数据表连接条件 */
	public static final String ITEM_QUERY_SQL = "query_sql" ;		/* 高级查询SQL */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* 创建人 */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* 创建日期 */
	public static final String ITEM_LAST_EXEC_DATE = "last_exec_date" ;	/* 最后执行日期 */
	public static final String ITEM_EXEC_TOTAL = "exec_total" ;		/* 执行次数 */
	public static final String ITEM_SQL_STEP_1 = "sql_step_1" ;		/* 第一步提交的SQL */
	public static final String ITEM_SQL_STEP_2 = "sql_step_2" ;		/* 第二步提交的SQL */
	public static final String ITEM_SQL_STEP_3 = "sql_step_3" ;		/* 第三步提交的SQL */
	public static final String ITEM_DCZD_DM = "dczd_dm" ;			/* 主题编号 */
	
	/**
	 * 构造函数
	 */
	public VoSysAdvancedQuery()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysAdvancedQuery(DataBus value)
	{
		super(value);
	}
	
	/* 高级查询编号 : String */
	public String getSys_advanced_query_id()
	{
		return getValue( ITEM_SYS_ADVANCED_QUERY_ID );
	}

	public void setSys_advanced_query_id( String sys_advanced_query_id1 )
	{
		setValue( ITEM_SYS_ADVANCED_QUERY_ID, sys_advanced_query_id1 );
	}

	/* 高级查询主题名称 : String */
	public String getName()
	{
		return getValue( ITEM_NAME );
	}

	public void setName( String name1 )
	{
		setValue( ITEM_NAME, name1 );
	}

	/* 高级查询关联表编号 : String */
	public String getSys_advanced_query_table_id()
	{
		return getValue( ITEM_SYS_ADVANCED_QUERY_TABLE_ID );
	}

	public void setSys_advanced_query_table_id( String sys_advanced_query_table_id1 )
	{
		setValue( ITEM_SYS_ADVANCED_QUERY_TABLE_ID, sys_advanced_query_table_id1 );
	}

	/* 数据表连接条件 : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

	/* 高级查询SQL : String */
	public String getQuery_sql()
	{
		return getValue( ITEM_QUERY_SQL );
	}

	public void setQuery_sql( String query_sql1 )
	{
		setValue( ITEM_QUERY_SQL, query_sql1 );
	}

	/* 创建人 : String */
	public String getCreate_by()
	{
		return getValue( ITEM_CREATE_BY );
	}

	public void setCreate_by( String create_by1 )
	{
		setValue( ITEM_CREATE_BY, create_by1 );
	}

	/* 创建日期 : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

	/* 最后执行日期 : String */
	public String getLast_exec_date()
	{
		return getValue( ITEM_LAST_EXEC_DATE );
	}

	public void setLast_exec_date( String last_exec_date1 )
	{
		setValue( ITEM_LAST_EXEC_DATE, last_exec_date1 );
	}

	/* 执行次数 : String */
	public String getExec_total()
	{
		return getValue( ITEM_EXEC_TOTAL );
	}

	public void setExec_total( String exec_total1 )
	{
		setValue( ITEM_EXEC_TOTAL, exec_total1 );
	}

	/* 第一步提交的SQL : String */
	public String getSql_step_1()
	{
		return getValue( ITEM_SQL_STEP_1 );
	}

	public void setSql_step_1( String sql_step_11 )
	{
		setValue( ITEM_SQL_STEP_1, sql_step_11 );
	}

	/* 第二步提交的SQL : String */
	public String getSql_step_2()
	{
		return getValue( ITEM_SQL_STEP_2 );
	}

	public void setSql_step_2( String sql_step_21 )
	{
		setValue( ITEM_SQL_STEP_2, sql_step_21 );
	}

	/* 第三步提交的SQL : String */
	public String getSql_step_3()
	{
		return getValue( ITEM_SQL_STEP_3 );
	}

	public void setSql_step_3( String sql_step_31 )
	{
		setValue( ITEM_SQL_STEP_3, sql_step_31 );
	}

	/* 主题编号 : String */
	public String getDczd_dm()
	{
		return getValue( ITEM_DCZD_DM );
	}

	public void setDczd_dm( String dczd_dm1 )
	{
		setValue( ITEM_DCZD_DM, dczd_dm1 );
	}

}

