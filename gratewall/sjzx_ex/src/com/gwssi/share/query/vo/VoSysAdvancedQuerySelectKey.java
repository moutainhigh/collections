package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_advanced_query]的数据对象类
 * @author Administrator
 *
 */
public class VoSysAdvancedQuerySelectKey extends VoBase
{
	private static final long serialVersionUID = 200806261658150003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_NAME = "name" ;					/* 高级查询主题名称 */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* 创建人 */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* 创建日期 */
	
	/**
	 * 构造函数
	 */
	public VoSysAdvancedQuerySelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysAdvancedQuerySelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 高级查询主题名称 : String */
	public String getName()
	{
		return getValue( ITEM_NAME );
	}

	public void setName( String name1 )
	{
		setValue( ITEM_NAME, name1 );
	}

	/* 创建人 : String */
	public String getCreate_by()
	{
		return getValue( ITEM_CREATE_BY );
	}

	public void setCreate_by( String create_by1 )
	{
		setValue( ITEM_CREATE_BY, create_by1 );
	}

	/* 创建日期 : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

}

