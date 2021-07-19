package com.gwssi.sysmgr.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_purv]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadPurvPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809051722060004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DOWNLOAD_PURV_ID = "download_purv_id" ;	/* 主键 */
	
	/**
	 * 构造函数
	 */
	public VoDownloadPurvPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadPurvPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 主键 : String */
	public String getDownload_purv_id()
	{
		return getValue( ITEM_DOWNLOAD_PURV_ID );
	}

	public void setDownload_purv_id( String download_purv_id1 )
	{
		setValue( ITEM_DOWNLOAD_PURV_ID, download_purv_id1 );
	}

}

