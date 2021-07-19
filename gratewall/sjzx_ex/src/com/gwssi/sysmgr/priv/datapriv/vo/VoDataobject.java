package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[dataobject]的数据对象类
 * @author Administrator
 *
 */
public class VoDataobject extends VoBase
{
	private static final long serialVersionUID = 200709101621230010L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_OBJECTID = "objectid" ;			/* 数据权限类型代码 */
	public static final String ITEM_OBJECTSOURCE = "objectsource" ;	/* 数据权限来源 */
	
	/**
	 * 构造函数
	 */
	public VoDataobject()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDataobject(DataBus value)
	{
		super(value);
	}
	
	/* 数据权限类型代码 : String */
	public String getObjectid()
	{
		return getValue( ITEM_OBJECTID );
	}

	public void setObjectid( String objectid1 )
	{
		setValue( ITEM_OBJECTID, objectid1 );
	}

	/* 数据权限来源 : String */
	public String getObjectsource()
	{
		return getValue( ITEM_OBJECTSOURCE );
	}

	public void setObjectsource( String objectsource1 )
	{
		setValue( ITEM_OBJECTSOURCE, objectsource1 );
	}

}

