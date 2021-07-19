package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[exc_que_civ]的数据对象类
 * @author Administrator
 *
 */
public class VoExcQueCivPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200808291334510012L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_EXC_QUE_CIV_ID = "exc_que_civ_id" ;	/* 民政局扩展信息ID */
	
	/**
	 * 构造函数
	 */
	public VoExcQueCivPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoExcQueCivPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 民政局扩展信息ID : String */
	public String getExc_que_civ_id()
	{
		return getValue( ITEM_EXC_QUE_CIV_ID );
	}

	public void setExc_que_civ_id( String exc_que_civ_id1 )
	{
		setValue( ITEM_EXC_QUE_CIV_ID, exc_que_civ_id1 );
	}

}

