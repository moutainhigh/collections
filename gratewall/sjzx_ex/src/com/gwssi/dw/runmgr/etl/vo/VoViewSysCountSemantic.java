package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[view_sys_count_semantic]的数据对象类
 * @author Administrator
 *
 */
public class VoViewSysCountSemantic extends VoBase
{
	private static final long serialVersionUID = 200902271135510002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TABLE_CLASS_ID = "table_class_id" ;	/* 关联ID */
	public static final String ITEM_SYS_NAME = "sys_name" ;			/* 主题名称 */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* 表英文名 */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* 表中文名 */
	public static final String ITEM_CLASS_SORT = "class_sort" ;		/* 分类标志 */
	public static final String ITEM_CLASS_STATE = "class_state" ;	/* 状态标志 */
	public static final String ITEM_COUNT_DATE = "count_date" ;		/* 统计日期 */
	public static final String ITEM_COUNT_FULL = "count_full" ;		/* 表全量 */
	public static final String ITEM_COUNT_INCRE = "count_incre" ;	/* 表增量 */
	public static final String ITEM_SYS_ORDER = "sys_order" ;		/* 主题排序 */
	public static final String ITEM_TABLE_ORDER = "table_order" ;	/* 表排序 */
	public static final String ITEM_SORT_ORDER = "sort_order" ;		/* 分类排序 */
	public static final String ITEM_STATE_ORDER = "state_order" ;	/* 状态排序 */
	
	/**
	 * 构造函数
	 */
	public VoViewSysCountSemantic()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoViewSysCountSemantic(DataBus value)
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

	/* 主题名称 : String */
	public String getSys_name()
	{
		return getValue( ITEM_SYS_NAME );
	}

	public void setSys_name( String sys_name1 )
	{
		setValue( ITEM_SYS_NAME, sys_name1 );
	}

	/* 表英文名 : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* 表中文名 : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

	/* 分类标志 : String */
	public String getClass_sort()
	{
		return getValue( ITEM_CLASS_SORT );
	}

	public void setClass_sort( String class_sort1 )
	{
		setValue( ITEM_CLASS_SORT, class_sort1 );
	}

	/* 状态标志 : String */
	public String getClass_state()
	{
		return getValue( ITEM_CLASS_STATE );
	}

	public void setClass_state( String class_state1 )
	{
		setValue( ITEM_CLASS_STATE, class_state1 );
	}

	/* 统计日期 : String */
	public String getCount_date()
	{
		return getValue( ITEM_COUNT_DATE );
	}

	public void setCount_date( String count_date1 )
	{
		setValue( ITEM_COUNT_DATE, count_date1 );
	}

	/* 表全量 : String */
	public String getCount_full()
	{
		return getValue( ITEM_COUNT_FULL );
	}

	public void setCount_full( String count_full1 )
	{
		setValue( ITEM_COUNT_FULL, count_full1 );
	}

	/* 表增量 : String */
	public String getCount_incre()
	{
		return getValue( ITEM_COUNT_INCRE );
	}

	public void setCount_incre( String count_incre1 )
	{
		setValue( ITEM_COUNT_INCRE, count_incre1 );
	}

	/* 主题排序 : String */
	public String getSys_order()
	{
		return getValue( ITEM_SYS_ORDER );
	}

	public void setSys_order( String sys_order1 )
	{
		setValue( ITEM_SYS_ORDER, sys_order1 );
	}

	/* 表排序 : String */
	public String getTable_order()
	{
		return getValue( ITEM_TABLE_ORDER );
	}

	public void setTable_order( String table_order1 )
	{
		setValue( ITEM_TABLE_ORDER, table_order1 );
	}

	/* 分类排序 : String */
	public String getSort_order()
	{
		return getValue( ITEM_SORT_ORDER );
	}

	public void setSort_order( String sort_order1 )
	{
		setValue( ITEM_SORT_ORDER, sort_order1 );
	}

	/* 状态排序 : String */
	public String getState_order()
	{
		return getValue( ITEM_STATE_ORDER );
	}

	public void setState_order( String state_order1 )
	{
		setValue( ITEM_STATE_ORDER, state_order1 );
	}

}

