package com.gwssi.dw.aic.bj.homepage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[work_status]的数据对象类
 * @author Administrator
 *
 */
public class VoWorkStatusSelectKey extends VoBase
{
	private static final long serialVersionUID = 200812041106360003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SCOUR_TABLE_NAME = "scour_table_name" ;	/* 源表名 */
	public static final String ITEM_SCOUR_TABLE_KEY_COL = "scour_table_key_col" ;	/* 源表主键ID */
	public static final String ITEM_WORK_STATUS_NAME = "work_status_name" ;	/* 待办任务名称 */
	
	/**
	 * 构造函数
	 */
	public VoWorkStatusSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoWorkStatusSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 源表名 : String */
	public String getScour_table_name()
	{
		return getValue( ITEM_SCOUR_TABLE_NAME );
	}

	public void setScour_table_name( String scour_table_name1 )
	{
		setValue( ITEM_SCOUR_TABLE_NAME, scour_table_name1 );
	}

	/* 源表主键ID : String */
	public String getScour_table_key_col()
	{
		return getValue( ITEM_SCOUR_TABLE_KEY_COL );
	}

	public void setScour_table_key_col( String scour_table_key_col1 )
	{
		setValue( ITEM_SCOUR_TABLE_KEY_COL, scour_table_key_col1 );
	}

	/* 待办任务名称 : String */
	public String getWork_status_name()
	{
		return getValue( ITEM_WORK_STATUS_NAME );
	}

	public void setWork_status_name( String work_status_name1 )
	{
		setValue( ITEM_WORK_STATUS_NAME, work_status_name1 );
	}

}

