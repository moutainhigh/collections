package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_system_semantic]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSystemSemanticSelectKey extends VoBase
{
	private static final long serialVersionUID = 200804181523310003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_NAME = "sys_name" ;			/* 系统名称 */
	public static final String ITEM_SYS_SIMPLE = "sys_simple" ;		/* 系统简称 */
	
	/**
	 * 构造函数
	 */
	public VoSysSystemSemanticSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSystemSemanticSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 系统名称 : String */
	public String getSys_name()
	{
		return getValue( ITEM_SYS_NAME );
	}

	public void setSys_name( String sys_name1 )
	{
		setValue( ITEM_SYS_NAME, sys_name1 );
	}

	/* 系统简称 : String */
	public String getSys_simple()
	{
		return getValue( ITEM_SYS_SIMPLE );
	}

	public void setSys_simple( String sys_simple1 )
	{
		setValue( ITEM_SYS_SIMPLE, sys_simple1 );
	}

}

