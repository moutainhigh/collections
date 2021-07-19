package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_etl_dbsource]的数据对象类
 * @author Administrator
 *
 */
public class VoSysEtlDbsource extends VoBase
{
	private static final long serialVersionUID = 200805091051540002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_ETL_DBSOURCE_ID = "sys_etl_dbsource_id" ;	/* ID */
	public static final String ITEM_DBSOURCE_NAME = "dbsource_name" ;	/* 数据来源名称 */
	public static final String ITEM_DBSOURCE_LB = "dbsource_lb" ;	/* 数据来源类别 */
	public static final String ITEM_DB_NAME = "db_name" ;			/* 数据库名称 */
	public static final String ITEM_DB_MS = "db_ms" ;				/* 数据库描述 */
	public static final String ITEM_DB_USER = "db_user" ;			/* 数据库用户 */
	public static final String ITEM_DB_PASSWORD = "db_password" ;	/* 数据库密码 */
	public static final String ITEM_DB_CONSTR = "db_constr" ;		/* 数据库连接串 */
	public static final String ITEM_DB_LB = "db_lb" ;				/* db_lb */
	
	/**
	 * 构造函数
	 */
	public VoSysEtlDbsource()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysEtlDbsource(DataBus value)
	{
		super(value);
	}
	
	/* ID : String */
	public String getSys_etl_dbsource_id()
	{
		return getValue( ITEM_SYS_ETL_DBSOURCE_ID );
	}

	public void setSys_etl_dbsource_id( String sys_etl_dbsource_id1 )
	{
		setValue( ITEM_SYS_ETL_DBSOURCE_ID, sys_etl_dbsource_id1 );
	}

	/* 数据来源名称 : String */
	public String getDbsource_name()
	{
		return getValue( ITEM_DBSOURCE_NAME );
	}

	public void setDbsource_name( String dbsource_name1 )
	{
		setValue( ITEM_DBSOURCE_NAME, dbsource_name1 );
	}

	/* 数据来源类别 : String */
	public String getDbsource_lb()
	{
		return getValue( ITEM_DBSOURCE_LB );
	}

	public void setDbsource_lb( String dbsource_lb1 )
	{
		setValue( ITEM_DBSOURCE_LB, dbsource_lb1 );
	}

	/* 数据库名称 : String */
	public String getDb_name()
	{
		return getValue( ITEM_DB_NAME );
	}

	public void setDb_name( String db_name1 )
	{
		setValue( ITEM_DB_NAME, db_name1 );
	}

	/* 数据库描述 : String */
	public String getDb_ms()
	{
		return getValue( ITEM_DB_MS );
	}

	public void setDb_ms( String db_ms1 )
	{
		setValue( ITEM_DB_MS, db_ms1 );
	}

	/* 数据库用户 : String */
	public String getDb_user()
	{
		return getValue( ITEM_DB_USER );
	}

	public void setDb_user( String db_user1 )
	{
		setValue( ITEM_DB_USER, db_user1 );
	}

	/* 数据库密码 : String */
	public String getDb_password()
	{
		return getValue( ITEM_DB_PASSWORD );
	}

	public void setDb_password( String db_password1 )
	{
		setValue( ITEM_DB_PASSWORD, db_password1 );
	}

	/* 数据库连接串 : String */
	public String getDb_constr()
	{
		return getValue( ITEM_DB_CONSTR );
	}

	public void setDb_constr( String db_constr1 )
	{
		setValue( ITEM_DB_CONSTR, db_constr1 );
	}

	/* db_lb : String */
	public String getDb_lb()
	{
		return getValue( ITEM_DB_LB );
	}

	public void setDb_lb( String db_lb1 )
	{
		setValue( ITEM_DB_LB, db_lb1 );
	}

}

