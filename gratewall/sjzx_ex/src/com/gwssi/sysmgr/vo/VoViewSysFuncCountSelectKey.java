package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[view_sys_func_count]的数据对象类
 * @author Administrator
 *
 */
public class VoViewSysFuncCountSelectKey extends VoBase
{
	private static final long serialVersionUID = 200907301527540007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_QUERY_DATE = "query_date" ;		/* 执行日期 */
	
	/**
	 * 构造函数
	 */
	public VoViewSysFuncCountSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoViewSysFuncCountSelectKey(DataBus value)
	{
		super(value);
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

}

