package com.gwssi.share.trs.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[trs_share_service]的数据对象类
 * @author Administrator
 *
 */
public class VoTrsShareServiceSelectKey extends VoBase
{
	private static final long serialVersionUID = 201308051642360003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TRS_SERVICE_NAME = "trs_service_name" ;	/* 服务名称 */
	
	/**
	 * 构造函数
	 */
	public VoTrsShareServiceSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoTrsShareServiceSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 服务名称 : String */
	public String getTrs_service_name()
	{
		return getValue( ITEM_TRS_SERVICE_NAME );
	}

	public void setTrs_service_name( String trs_service_name1 )
	{
		setValue( ITEM_TRS_SERVICE_NAME, trs_service_name1 );
	}

}

