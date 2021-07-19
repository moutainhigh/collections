package com.gwssi.share.trs.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[trs_share_service]的数据对象类
 * @author Administrator
 *
 */
public class VoTrsShareServicePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201308051642360004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TRS_SERVICE_ID = "trs_service_id" ;	/* 服务ID */
	
	/**
	 * 构造函数
	 */
	public VoTrsShareServicePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoTrsShareServicePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 服务ID : String */
	public String getTrs_service_id()
	{
		return getValue( ITEM_TRS_SERVICE_ID );
	}

	public void setTrs_service_id( String trs_service_id1 )
	{
		setValue( ITEM_TRS_SERVICE_ID, trs_service_id1 );
	}

}

