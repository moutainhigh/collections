package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[view_sys_count_semantic]的数据对象类
 * @author Administrator
 *
 */
public class VoViewSysCountSemanticPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200902271135510004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TABLE_CLASS_ID = "table_class_id" ;	/* 关联ID */
	
	/**
	 * 构造函数
	 */
	public VoViewSysCountSemanticPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoViewSysCountSemanticPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 关联ID : String */
	public String getTable_class_id()
	{
		return getValue( ITEM_TABLE_CLASS_ID );
	}

	public void setTable_class_id( String table_class_id1 )
	{
		setValue( ITEM_TABLE_CLASS_ID, table_class_id1 );
	}

}

