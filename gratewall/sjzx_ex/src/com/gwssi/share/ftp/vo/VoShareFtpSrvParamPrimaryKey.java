package com.gwssi.share.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_ftp_srv_param]的数据对象类
 * @author Administrator
 *
 */
public class VoShareFtpSrvParamPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201308211700020012L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SRV_PARAM_ID = "srv_param_id" ;	/* 参数值ID */
	
	/**
	 * 构造函数
	 */
	public VoShareFtpSrvParamPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareFtpSrvParamPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 参数值ID : String */
	public String getSrv_param_id()
	{
		return getValue( ITEM_SRV_PARAM_ID );
	}

	public void setSrv_param_id( String srv_param_id1 )
	{
		setValue( ITEM_SRV_PARAM_ID, srv_param_id1 );
	}

}

