package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[view_sys_func_count]的数据对象类
 * @author Administrator
 *
 */
public class VoViewSysFuncCount extends VoBase
{
	private static final long serialVersionUID = 200907301527540006L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_QUERYTIMES = "querytimes" ;		/* 次数 */
	public static final String ITEM_QUERY_DATE = "query_date" ;		/* 执行日期 */
	public static final String ITEM_FUNC_NAME = "func_name" ;		/* 功能模块 */
	public static final String ITEM_SJJGID_FK = "sjjgid_fk" ;		/* 机构ID */
	public static final String ITEM_FUNC_INDEX = "func_index" ;		/* 序号 */
	
	/**
	 * 构造函数
	 */
	public VoViewSysFuncCount()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoViewSysFuncCount(DataBus value)
	{
		super(value);
	}
	
	/* 次数 : String */
	public String getQuerytimes()
	{
		return getValue( ITEM_QUERYTIMES );
	}

	public void setQuerytimes( String querytimes1 )
	{
		setValue( ITEM_QUERYTIMES, querytimes1 );
	}

	/* 执行日期 : String */
	public String getQuery_date()
	{
		return getValue( ITEM_QUERY_DATE );
	}

	public void setQuery_date( String query_date1 )
	{
		setValue( ITEM_QUERY_DATE, query_date1 );
	}

	/* 功能模块 : String */
	public String getFunc_name()
	{
		return getValue( ITEM_FUNC_NAME );
	}

	public void setFunc_name( String func_name1 )
	{
		setValue( ITEM_FUNC_NAME, func_name1 );
	}

	/* 机构ID : String */
	public String getSjjgid_fk()
	{
		return getValue( ITEM_SJJGID_FK );
	}

	public void setSjjgid_fk( String sjjgid_fk1 )
	{
		setValue( ITEM_SJJGID_FK, sjjgid_fk1 );
	}

	/* 序号 : String */
	public String getFunc_index()
	{
		return getValue( ITEM_FUNC_INDEX );
	}

	public void setFunc_index( String func_index1 )
	{
		setValue( ITEM_FUNC_INDEX, func_index1 );
	}

}

