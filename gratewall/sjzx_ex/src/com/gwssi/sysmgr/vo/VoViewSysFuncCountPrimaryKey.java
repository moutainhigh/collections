package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[view_sys_func_count]的数据对象类
 * @author Administrator
 *
 */
public class VoViewSysFuncCountPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200907301527550008L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_FUNC_NAME = "func_name" ;		/* 功能模块 */
	public static final String ITEM_SJJGID_FK = "sjjgid_fk" ;		/* 机构ID */
	
	/**
	 * 构造函数
	 */
	public VoViewSysFuncCountPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoViewSysFuncCountPrimaryKey(DataBus value)
	{
		super(value);
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

}

