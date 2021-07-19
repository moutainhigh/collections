package com.gwssi.share.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_ftp_service]的数据对象类
 * @author Administrator
 *
 */
public class VoShareFtpServicePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201308211659250008L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_FTP_SERVICE_ID = "ftp_service_id" ;	/* FTP服务ID */
	
	/**
	 * 构造函数
	 */
	public VoShareFtpServicePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareFtpServicePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* FTP服务ID : String */
	public String getFtp_service_id()
	{
		return getValue( ITEM_FTP_SERVICE_ID );
	}

	public void setFtp_service_id( String ftp_service_id1 )
	{
		setValue( ITEM_FTP_SERVICE_ID, ftp_service_id1 );
	}

}

