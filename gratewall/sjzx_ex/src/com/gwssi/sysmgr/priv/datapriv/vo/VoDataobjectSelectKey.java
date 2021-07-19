package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[dataobject]的数据对象类
 * @author Administrator
 *
 */
public class VoDataobjectSelectKey extends VoBase
{
	private static final long serialVersionUID = 200709101621240011L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_OBJECTSOURCE = "objectsource" ;	/* 数据权限来源 */
	
	/**
	 * 构造函数
	 */
	public VoDataobjectSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDataobjectSelectKey(DataBus value)
	{
		super(value);
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

