package com.gwssi.dw.aic.bj.newmon.ent.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[mon_ent_tsk]的数据对象类
 * @author Administrator
 *
 */
public class VoMonEntTskSelectKey extends VoBase
{
	private static final long serialVersionUID = 200811191653120003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_MAIN_ID = "main_id" ;			/* 主体ID */
	
	/**
	 * 构造函数
	 */
	public VoMonEntTskSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoMonEntTskSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 主体ID : String */
	public String getMain_id()
	{
		return getValue( ITEM_MAIN_ID );
	}

	public void setMain_id( String main_id1 )
	{
		setValue( ITEM_MAIN_ID, main_id1 );
	}

}

