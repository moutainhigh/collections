package com.gwssi.dw.metadata.basecode.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[gz_dm_jcdm_fx]的数据对象类
 * @author Administrator
 *
 */
public class VoGzDmJcdmFxPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200708271316220003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_JCSJFX_ID = "jcsjfx_id" ;		/* 基础数据分项ID */
	
	/**
	 * 构造函数
	 */
	public VoGzDmJcdmFxPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoGzDmJcdmFxPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 基础数据分项ID : String */
	public String getJcsjfx_id()
	{
		return getValue( ITEM_JCSJFX_ID );
	}

	public void setJcsjfx_id( String jcsjfx_id1 )
	{
		setValue( ITEM_JCSJFX_ID, jcsjfx_id1 );
	}

}

