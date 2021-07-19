package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[funcdataobject]的数据对象类
 * @author Administrator
 *
 */
public class VoFuncdataobjectSelectKey extends VoBase
{
	private static final long serialVersionUID = 200709101623210015L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_OBJECTID = "objectid" ;			/* 数据对象代码 */
	
	/**
	 * 构造函数
	 */
	public VoFuncdataobjectSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoFuncdataobjectSelectKey(DataBus value)
	{
		super(value);
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

