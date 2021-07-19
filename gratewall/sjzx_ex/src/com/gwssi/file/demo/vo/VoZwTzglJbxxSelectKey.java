package com.gwssi.file.demo.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[zw_tzgl_jbxx]的数据对象类
 * @author Administrator
 *
 */
public class VoZwTzglJbxxSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303271357410003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TZMC = "tzmc" ;					/* 通知名称 */
	public static final String ITEM_FBSJ = "fbsj" ;					/* 发布时间 */
	public static final String ITEM_TZZT = "tzzt" ;					/* 通知状态 */
	
	/**
	 * 构造函数
	 */
	public VoZwTzglJbxxSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoZwTzglJbxxSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 通知名称 : String */
	public String getTzmc()
	{
		return getValue( ITEM_TZMC );
	}

	public void setTzmc( String tzmc1 )
	{
		setValue( ITEM_TZMC, tzmc1 );
	}

	/* 发布时间 : String */
	public String getFbsj()
	{
		return getValue( ITEM_FBSJ );
	}

	public void setFbsj( String fbsj1 )
	{
		setValue( ITEM_FBSJ, fbsj1 );
	}

	/* 通知状态 : String */
	public String getTzzt()
	{
		return getValue( ITEM_TZZT );
	}

	public void setTzzt( String tzzt1 )
	{
		setValue( ITEM_TZZT, tzzt1 );
	}

}

