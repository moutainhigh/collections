package com.gwssi.log.systemlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[first_page_query]的数据对象类
 * @author Administrator
 *
 */
public class VoFirstPageQuerySystemlogPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304251426190004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_FIRST_PAGE_QUERY_ID = "first_page_query_id" ;	/* 主键 */
	
	/**
	 * 构造函数
	 */
	public VoFirstPageQuerySystemlogPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoFirstPageQuerySystemlogPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 主键 : String */
	public String getFirst_page_query_id()
	{
		return getValue( ITEM_FIRST_PAGE_QUERY_ID );
	}

	public void setFirst_page_query_id( String first_page_query_id1 )
	{
		setValue( ITEM_FIRST_PAGE_QUERY_ID, first_page_query_id1 );
	}

}

