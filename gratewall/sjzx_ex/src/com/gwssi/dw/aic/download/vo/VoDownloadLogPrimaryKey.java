package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_log]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadLogPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200812261332290004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DOWNLOAD_LOG_ID = "download_log_id" ;	/* 下载日志ID */
	
	/**
	 * 构造函数
	 */
	public VoDownloadLogPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadLogPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 下载日志ID : String */
	public String getDownload_log_id()
	{
		return getValue( ITEM_DOWNLOAD_LOG_ID );
	}

	public void setDownload_log_id( String download_log_id1 )
	{
		setValue( ITEM_DOWNLOAD_LOG_ID, download_log_id1 );
	}

}

