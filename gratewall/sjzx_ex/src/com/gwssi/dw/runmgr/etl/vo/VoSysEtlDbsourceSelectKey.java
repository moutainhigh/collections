package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_etl_dbsource]的数据对象类
 * @author Administrator
 *
 */
public class VoSysEtlDbsourceSelectKey extends VoBase
{
	private static final long serialVersionUID = 200805091051540003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DBSOURCE_LB = "dbsource_lb" ;	/* 数据来源类别 */
	public static final String ITEM_DB_NAME = "db_name" ;			/* 数据库名称 */
	
	/**
	 * 构造函数
	 */
	public VoSysEtlDbsourceSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysEtlDbsourceSelectKey(DataBus value)
	{
		super(value);
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

}

