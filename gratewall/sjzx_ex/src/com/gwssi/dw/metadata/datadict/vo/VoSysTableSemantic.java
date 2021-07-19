package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_table_semantic]的数据对象类
 * @author Administrator
 *
 */
public class VoSysTableSemantic extends VoBase
{
	private static final long serialVersionUID = 200804181523520006L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* 业务表编码 */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* 业务表名 */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* 业务表中文名 */
	public static final String ITEM_TABLE_ORDER = "table_order" ;	/* 检索顺序 */
	public static final String ITEM_DEMO = "demo" ;					/* 备注 */
	public static final String ITEM_SYS_ID = "sys_id" ;				/* 业务系统编码 */
	
	/**
	 * 构造函数
	 */
	public VoSysTableSemantic()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysTableSemantic(DataBus value)
	{
		super(value);
	}
	
	/* 业务表编码 : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
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

	/* 检索顺序 : String */
	public String getTable_order()
	{
		return getValue( ITEM_TABLE_ORDER );
	}

	public void setTable_order( String table_order1 )
	{
		setValue( ITEM_TABLE_ORDER, table_order1 );
	}

	/* 备注 : String */
	public String getDemo()
	{
		return getValue( ITEM_DEMO );
	}

	public void setDemo( String demo1 )
	{
		setValue( ITEM_DEMO, demo1 );
	}

	/* 业务系统编码 : String */
	public String getSys_id()
	{
		return getValue( ITEM_SYS_ID );
	}

	public void setSys_no( String sys_id1 )
	{
		setValue( ITEM_SYS_ID, sys_id1 );
	}

}

