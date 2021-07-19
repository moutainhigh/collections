package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[dataobject]的数据对象类
 * @author Administrator
 *
 */
public class VoDataobjectPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200709101621250012L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_OBJECTID = "objectid" ;			/* 数据权限类型代码 */
	
	/**
	 * 构造函数
	 */
	public VoDataobjectPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDataobjectPrimaryKey(DataBus value)
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

}

