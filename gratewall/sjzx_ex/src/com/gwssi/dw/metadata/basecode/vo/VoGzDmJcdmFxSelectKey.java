package com.gwssi.dw.metadata.basecode.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[gz_dm_jcdm_fx]的数据对象类
 * @author Administrator
 *
 */
public class VoGzDmJcdmFxSelectKey extends VoBase
{
	private static final long serialVersionUID = 200708271316220002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_JCSJFX_ID = "jcsjfx_id" ;		/* 基础数据分项ID */
	public static final String ITEM_JC_DM_ID = "jc_dm_id" ;			/* 基础代码ID */
	
	/**
	 * 构造函数
	 */
	public VoGzDmJcdmFxSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoGzDmJcdmFxSelectKey(DataBus value)
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

	/* 基础代码ID : String */
	public String getJc_dm_id()
	{
		return getValue( ITEM_JC_DM_ID );
	}

	public void setJc_dm_id( String jc_dm_id1 )
	{
		setValue( ITEM_JC_DM_ID, jc_dm_id1 );
	}

}

