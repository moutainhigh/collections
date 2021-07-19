package com.gwssi.dw.aic.bj.newmon.ent.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[mon_ent_tsk]的数据对象类
 * @author Administrator
 *
 */
public class VoMonEntTskPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200811191653120004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_MON_ENT_TSK_ID = "mon_ent_tsk_id" ;	/* 主体检查任务表id */
	
	/**
	 * 构造函数
	 */
	public VoMonEntTskPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoMonEntTskPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 主体检查任务表id : String */
	public String getMon_ent_tsk_id()
	{
		return getValue( ITEM_MON_ENT_TSK_ID );
	}

	public void setMon_ent_tsk_id( String mon_ent_tsk_id1 )
	{
		setValue( ITEM_MON_ENT_TSK_ID, mon_ent_tsk_id1 );
	}

}

