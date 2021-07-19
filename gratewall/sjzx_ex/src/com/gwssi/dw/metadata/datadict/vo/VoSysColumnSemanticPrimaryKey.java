package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_column_semantic]的数据对象类
 * @author Administrator
 *
 */
public class VoSysColumnSemanticPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200804181524160012L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_COLUMN_NO = "column_no" ;		/* 业务字段编码 */
	
	/**
	 * 构造函数
	 */
	public VoSysColumnSemanticPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysColumnSemanticPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 业务字段编码 : String */
	public String getColumn_no()
	{
		return getValue( ITEM_COLUMN_NO );
	}

	public void setColumn_no( String column_no1 )
	{
		setValue( ITEM_COLUMN_NO, column_no1 );
	}

}

