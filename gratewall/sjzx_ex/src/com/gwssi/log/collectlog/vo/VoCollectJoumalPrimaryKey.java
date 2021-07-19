package com.gwssi.log.collectlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_joumal]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectJoumalPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304101519320004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_COLLECT_JOUMAL_ID = "collect_joumal_id" ;	/* 采集日志ID */
	
	/**
	 * 构造函数
	 */
	public VoCollectJoumalPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectJoumalPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 采集日志ID : String */
	public String getCollect_joumal_id()
	{
		return getValue( ITEM_COLLECT_JOUMAL_ID );
	}

	public void setCollect_joumal_id( String collect_joumal_id1 )
	{
		setValue( ITEM_COLLECT_JOUMAL_ID, collect_joumal_id1 );
	}

}

