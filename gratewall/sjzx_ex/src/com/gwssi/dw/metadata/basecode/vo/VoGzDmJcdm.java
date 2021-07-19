package com.gwssi.dw.metadata.basecode.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[gz_dm_jcdm]的数据对象类
 * @author Administrator
 *
 */
public class VoGzDmJcdm extends VoBase
{
	private static final long serialVersionUID = 200708261321540001L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_JC_DM_ID = "jc_dm_id" ;			/* 基础代码ID */
	public static final String ITEM_JC_DM_DM = "jc_dm_dm" ;			/* 基础代码 */
	public static final String ITEM_JC_DM_MC = "jc_dm_mc" ;			/* 基础代码名称 */
	public static final String ITEM_JC_DM_BZLY = "jc_dm_bzly" ;		/* 标准来源 */
	public static final String ITEM_JC_DM_MS = "jc_dm_ms" ;			/* 基础代码描述 */
	
	/**
	 * 构造函数
	 */
	public VoGzDmJcdm()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoGzDmJcdm(DataBus value)
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

	/* 基础代码 : String */
	public String getJc_dm_dm()
	{
		return getValue( ITEM_JC_DM_DM );
	}

	public void setJc_dm_dm( String jc_dm_dm1 )
	{
		setValue( ITEM_JC_DM_DM, jc_dm_dm1 );
	}

	/* 基础代码名称 : String */
	public String getJc_dm_mc()
	{
		return getValue( ITEM_JC_DM_MC );
	}

	public void setJc_dm_mc( String jc_dm_mc1 )
	{
		setValue( ITEM_JC_DM_MC, jc_dm_mc1 );
	}

	/* 标准来源 : String */
	public String getJc_dm_bzly()
	{
		return getValue( ITEM_JC_DM_BZLY );
	}

	public void setJc_dm_bzly( String jc_dm_bzly1 )
	{
		setValue( ITEM_JC_DM_BZLY, jc_dm_bzly1 );
	}

	/* 基础代码描述 : String */
	public String getJc_dm_ms()
	{
		return getValue( ITEM_JC_DM_MS );
	}

	public void setJc_dm_ms( String jc_dm_ms1 )
	{
		setValue( ITEM_JC_DM_MS, jc_dm_ms1 );
	}

}

