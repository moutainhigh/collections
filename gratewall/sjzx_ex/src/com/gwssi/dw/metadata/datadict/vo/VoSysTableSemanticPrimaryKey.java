package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_table_semantic]的数据对象类
 * @author Administrator
 *
 */
public class VoSysTableSemanticPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200804181523530008L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* 业务表编码 */
	
	/**
	 * 构造函数
	 */
	public VoSysTableSemanticPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysTableSemanticPrimaryKey(DataBus value)
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

}

