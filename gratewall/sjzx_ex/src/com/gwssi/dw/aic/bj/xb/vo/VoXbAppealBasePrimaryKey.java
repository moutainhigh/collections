package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[xb_appeal_base]的数据对象类
 * @author Administrator
 *
 */
public class VoXbAppealBasePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809111008440004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_XB_APPEAL_BASE_ID = "xb_appeal_base_id" ;	/* 申诉基本信息ID */
	
	/**
	 * 构造函数
	 */
	public VoXbAppealBasePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoXbAppealBasePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 申诉基本信息ID : String */
	public String getXb_appeal_base_id()
	{
		return getValue( ITEM_XB_APPEAL_BASE_ID );
	}

	public void setXb_appeal_base_id( String xb_appeal_base_id1 )
	{
		setValue( ITEM_XB_APPEAL_BASE_ID, xb_appeal_base_id1 );
	}

}

