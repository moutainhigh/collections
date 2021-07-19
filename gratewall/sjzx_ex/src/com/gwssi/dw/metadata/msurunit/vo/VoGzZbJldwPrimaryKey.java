package com.gwssi.dw.metadata.msurunit.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[gz_zb_jldw]的数据对象类
 * @author Administrator
 *
 */
public class VoGzZbJldwPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200707251356150016L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_JLDW_DM = "jldw_dm" ;			/* 计量单位代码 */
	
	/**
	 * 构造函数
	 */
	public VoGzZbJldwPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoGzZbJldwPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 计量单位代码 : String */
	public String getJldw_dm()
	{
		return getValue( ITEM_JLDW_DM );
	}

	public void setJldw_dm( String jldw_dm1 )
	{
		setValue( ITEM_JLDW_DM, jldw_dm1 );
	}

}

