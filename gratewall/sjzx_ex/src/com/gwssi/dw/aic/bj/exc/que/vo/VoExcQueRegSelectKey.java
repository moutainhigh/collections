package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[exc_que_reg]的数据对象类
 * @author Administrator
 *
 */
public class VoExcQueRegSelectKey extends VoBase
{
	private static final long serialVersionUID = 200808291334510003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_EXC_QUE_REG_ID = "exc_que_reg_id" ;	/* 质检法人库ID */
	
	/**
	 * 构造函数
	 */
	public VoExcQueRegSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoExcQueRegSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 质检法人库ID : String */
	public String getExc_que_reg_id()
	{
		return getValue( ITEM_EXC_QUE_REG_ID );
	}

	public void setExc_que_reg_id( String exc_que_reg_id1 )
	{
		setValue( ITEM_EXC_QUE_REG_ID, exc_que_reg_id1 );
	}

}

