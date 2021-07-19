package com.gwssi.dw.metadata.basecode.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[gz_dm_jcdm]的数据对象类
 * @author Administrator
 *
 */
public class VoGzDmJcdmSelectKey extends VoBase
{
	private static final long serialVersionUID = 200708261321550002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_JC_DM_ID = "jc_dm_id" ;			/* 基础代码ID */
	
	/**
	 * 构造函数
	 */
	public VoGzDmJcdmSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoGzDmJcdmSelectKey(DataBus value)
	{
		super(value);
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

