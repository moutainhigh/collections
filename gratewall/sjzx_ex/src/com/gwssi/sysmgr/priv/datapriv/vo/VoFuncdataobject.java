package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[funcdataobject]的数据对象类
 * @author Administrator
 *
 */
public class VoFuncdataobject extends VoBase
{
	private static final long serialVersionUID = 200709101623200014L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_FUNCODE = "funcode" ;			/* 功能代码 */
	public static final String ITEM_OBJECTID = "objectid" ;			/* 数据对象代码 */
	
	/**
	 * 构造函数
	 */
	public VoFuncdataobject()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoFuncdataobject(DataBus value)
	{
		super(value);
	}
	
	/* 功能代码 : String */
	public String getFuncode()
	{
		return getValue( ITEM_FUNCODE );
	}

	public void setFuncode( String funcode1 )
	{
		setValue( ITEM_FUNCODE, funcode1 );
	}

	/* 数据对象代码 : String */
	public String getObjectid()
	{
		return getValue( ITEM_OBJECTID );
	}

	public void setObjectid( String objectid1 )
	{
		setValue( ITEM_OBJECTID, objectid1 );
	}

}

