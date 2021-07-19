package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_etl_dbsource]的数据对象类
 * @author Administrator
 *
 */
public class VoSysEtlDbsourcePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200805091051550004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_ETL_DBSOURCE_ID = "sys_etl_dbsource_id" ;	/* ID */
	
	/**
	 * 构造函数
	 */
	public VoSysEtlDbsourcePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysEtlDbsourcePrimaryKey(DataBus value)
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

}

