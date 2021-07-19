package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[view_sys_count_log]的数据对象类
 * @author Administrator
 *
 */
public class VoViewSysCountLogSelectKey extends VoBase
{
	private static final long serialVersionUID = 201208031540190003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_NAME = "sys_name" ;			/* 主题名称 */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* 表中文名 */
	public static final String ITEM_COUNT_DATE = "count_date" ;		/* 统计日期 */
	public static final String ITEM_CLASS_STATE = "class_state" ;	/* 分类状态 */
	
	/**
	 * 构造函数
	 */
	public VoViewSysCountLogSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoViewSysCountLogSelectKey(DataBus value)
	{
		super(value);
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

	/* 表中文名 : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
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

	/* 分类状态 : String */
	public String getClass_state()
	{
		return getValue( ITEM_CLASS_STATE );
	}

	public void setClass_state( String class_state1 )
	{
		setValue( ITEM_CLASS_STATE, class_state1 );
	}

}

