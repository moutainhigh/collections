package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[exc_que_auth]的数据对象类
 * @author Administrator
 *
 */
public class VoExcQueRegPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200808291334510018L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_EXC_QUE_AUTH_ID = "exc_que_reg_id" ;	/* 信息ID */
	
	/**
	 * 构造函数
	 */
	public VoExcQueRegPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoExcQueRegPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 编办扩展信息ID : String */
	public String getExc_que_reg_id()
	{
		return getValue( ITEM_EXC_QUE_AUTH_ID );
	}

	public void setExc_que_reg_id( String exc_que_reg_id1 )
	{
		setValue( ITEM_EXC_QUE_AUTH_ID, exc_que_reg_id1 );
	}

}

