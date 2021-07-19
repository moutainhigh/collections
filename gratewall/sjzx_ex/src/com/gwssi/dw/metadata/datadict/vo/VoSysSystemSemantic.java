package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_system_semantic]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSystemSemantic extends VoBase
{
	private static final long serialVersionUID = 200804181523310002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_ID = "sys_id" ;				/* 系统编码 */
	public static final String ITEM_SYS_NO = "sys_no" ;				/* 系统编码 */
	public static final String ITEM_SYS_NAME = "sys_name" ;			/* 系统名称 */
	public static final String ITEM_SYS_SIMPLE = "sys_simple" ;		/* 系统简称 */
	public static final String ITEM_SYS_ORDER = "sys_order" ;		/* 检索顺序 */
	
	/**
	 * 构造函数
	 */
	public VoSysSystemSemantic()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSystemSemantic(DataBus value)
	{
		super(value);
	}
	
	/* 系统ID : String */
	public String getSys_id()
	{
		return getValue( ITEM_SYS_ID );
	}

	public void setSys_id( String sys_id1 )
	{
		setValue( ITEM_SYS_NO, sys_id1 );
	}	
	
	/* 系统编码 : String */
	public String getSys_no()
	{
		return getValue( ITEM_SYS_NO );
	}

	public void setSys_no( String sys_no1 )
	{
		setValue( ITEM_SYS_NO, sys_no1 );
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

	/* 检索顺序 : String */
	public String getSys_order()
	{
		return getValue( ITEM_SYS_ORDER );
	}

	public void setSys_order( String sys_order1 )
	{
		setValue( ITEM_SYS_ORDER, sys_order1 );
	}

}

