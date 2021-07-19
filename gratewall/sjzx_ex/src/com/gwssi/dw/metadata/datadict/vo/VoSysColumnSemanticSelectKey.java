package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_column_semantic]的数据对象类
 * @author Administrator
 *
 */
public class VoSysColumnSemanticSelectKey extends VoBase
{
	private static final long serialVersionUID = 200804181524160011L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* 业务表编码 */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* 业务字段名 */
	public static final String ITEM_COLUMN_NAME_CN = "column_name_cn" ;	/* 业务字段中文名 */
	
	/**
	 * 构造函数
	 */
	public VoSysColumnSemanticSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysColumnSemanticSelectKey(DataBus value)
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

	/* 业务字段名 : String */
	public String getColumn_name()
	{
		return getValue( ITEM_COLUMN_NAME );
	}

	public void setColumn_name( String column_name1 )
	{
		setValue( ITEM_COLUMN_NAME, column_name1 );
	}

	/* 业务字段中文名 : String */
	public String getColumn_name_cn()
	{
		return getValue( ITEM_COLUMN_NAME_CN );
	}

	public void setColumn_name_cn( String column_name_cn1 )
	{
		setValue( ITEM_COLUMN_NAME_CN, column_name_cn1 );
	}

}

