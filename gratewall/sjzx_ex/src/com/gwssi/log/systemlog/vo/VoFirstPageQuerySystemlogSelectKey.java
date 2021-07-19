package com.gwssi.log.systemlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[first_page_query]的数据对象类
 * @author Administrator
 *
 */
public class VoFirstPageQuerySystemlogSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304251426190003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CREAT_START_TIME = "creat_start_time" ;	/* 操作时间 */
	public static final String ITEM_CREAT_END_TIME = "creat_end_time" ;	/* 操作时间 */
	public static final String ITEM_USERNAME = "username" ;			/* 用户名 */
	
	/**
	 * 构造函数
	 */
	public VoFirstPageQuerySystemlogSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoFirstPageQuerySystemlogSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 操作时间 : String */
	public String getCreat_start_time()
	{
		return getValue( ITEM_CREAT_START_TIME );
	}

	public void setCreat_start_time( String creat_start_time1 )
	{
		setValue( ITEM_CREAT_START_TIME, creat_start_time1 );
	}

	/* 操作时间 : String */
	public String getCreat_end_time()
	{
		return getValue( ITEM_CREAT_END_TIME );
	}

	public void setCreat_end_time( String creat_end_time1 )
	{
		setValue( ITEM_CREAT_END_TIME, creat_end_time1 );
	}

	/* 用户名 : String */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

}

