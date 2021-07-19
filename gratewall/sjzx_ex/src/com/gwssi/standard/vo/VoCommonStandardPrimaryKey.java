package com.gwssi.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[common_standard]的数据对象类
 * @author Administrator
 *
 */
public class VoCommonStandardPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304121530000004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_STANDARD_ID = "standard_id" ;	/* 标准ID */
	
	/**
	 * 构造函数
	 */
	public VoCommonStandardPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCommonStandardPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 标准ID : String */
	public String getStandard_id()
	{
		return getValue( ITEM_STANDARD_ID );
	}

	public void setStandard_id( String standard_id1 )
	{
		setValue( ITEM_STANDARD_ID, standard_id1 );
	}

}

