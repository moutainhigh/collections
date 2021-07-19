package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_table_semantic]的数据对象类
 * @author Administrator
 *
 */
public class VoSysTableSemanticSelectKey extends VoBase
{
	private static final long serialVersionUID = 200804181523530007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_ID = "sys_id" ;				/* 业务系统编码 */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* 业务表名 */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* 业务表中文名 */
	
	/**
	 * 构造函数
	 */
	public VoSysTableSemanticSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysTableSemanticSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 业务系统编码 : String */
	public String getSys_id()
	{
		return getValue( ITEM_SYS_ID );
	}

	public void setSys_id( String sys_id1 )
	{
		setValue( ITEM_SYS_ID, sys_id1 );
	}

	/* 业务表名 : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* 业务表中文名 : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

}

