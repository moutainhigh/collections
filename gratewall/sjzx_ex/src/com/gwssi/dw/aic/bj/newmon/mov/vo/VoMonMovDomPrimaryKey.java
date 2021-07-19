package com.gwssi.dw.aic.bj.newmon.mov.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[mon_mov_dom]的数据对象类
 * @author Administrator
 *
 */
public class VoMonMovDomPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200811201508540004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_MON_MOV_DOM_ID = "mon_mov_dom_id" ;	/* 擅迁住所表ID */
	
	/**
	 * 构造函数
	 */
	public VoMonMovDomPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoMonMovDomPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 擅迁住所表ID : String */
	public String getMon_mov_dom_id()
	{
		return getValue( ITEM_MON_MOV_DOM_ID );
	}

	public void setMon_mov_dom_id( String mon_mov_dom_id1 )
	{
		setValue( ITEM_MON_MOV_DOM_ID, mon_mov_dom_id1 );
	}

}

