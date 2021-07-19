package com.gwssi.share.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_srv_scheduling]的数据对象类
 * @author Administrator
 *
 */
public class VoShareSrvSchedulingPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201308211658410004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SRV_SCHEDULING_ID = "srv_scheduling_id" ;	/* 任务调度ID */
	
	/**
	 * 构造函数
	 */
	public VoShareSrvSchedulingPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareSrvSchedulingPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 任务调度ID : String */
	public String getSrv_scheduling_id()
	{
		return getValue( ITEM_SRV_SCHEDULING_ID );
	}

	public void setSrv_scheduling_id( String srv_scheduling_id1 )
	{
		setValue( ITEM_SRV_SCHEDULING_ID, srv_scheduling_id1 );
	}

}

