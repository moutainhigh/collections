package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[exc_que_auth]的数据对象类
 * @author Administrator
 *
 */
public class VoExcQueAuthPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200808291334510008L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_EXC_QUE_AUTH_ID = "exc_que_auth_id" ;	/* 编办扩展信息ID */
	
	/**
	 * 构造函数
	 */
	public VoExcQueAuthPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoExcQueAuthPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 编办扩展信息ID : String */
	public String getExc_que_auth_id()
	{
		return getValue( ITEM_EXC_QUE_AUTH_ID );
	}

	public void setExc_que_auth_id( String exc_que_auth_id1 )
	{
		setValue( ITEM_EXC_QUE_AUTH_ID, exc_que_auth_id1 );
	}

}

