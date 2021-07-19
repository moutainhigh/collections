package com.gwssi.file.demo.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[zw_tzgl_jbxx]的数据对象类
 * @author Administrator
 *
 */
public class VoZwTzglJbxxPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303271357410004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_JBXX_PK = "jbxx_pk" ;			/* 通知编号-主键 */
	
	/**
	 * 构造函数
	 */
	public VoZwTzglJbxxPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoZwTzglJbxxPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 通知编号-主键 : String */
	public String getJbxx_pk()
	{
		return getValue( ITEM_JBXX_PK );
	}

	public void setJbxx_pk( String jbxx_pk1 )
	{
		setValue( ITEM_JBXX_PK, jbxx_pk1 );
	}

}

