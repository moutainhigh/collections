package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_status]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadStatusPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809081126150004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DOWNLOAD_STATUS_ID = "download_status_id" ;	/* 下载状态ID */
	
	/**
	 * 构造函数
	 */
	public VoDownloadStatusPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadStatusPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 下载状态ID : String */
	public String getDownload_status_id()
	{
		return getValue( ITEM_DOWNLOAD_STATUS_ID );
	}

	public void setDownload_status_id( String download_status_id1 )
	{
		setValue( ITEM_DOWNLOAD_STATUS_ID, download_status_id1 );
	}

}

