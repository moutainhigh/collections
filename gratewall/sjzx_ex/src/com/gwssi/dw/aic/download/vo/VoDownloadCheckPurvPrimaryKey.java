package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_check_purv]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadCheckPurvPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200903171409260004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DOWNLOAD_CHECK_PURV_ID = "download_check_purv_id" ;	/* 下载审批权限ID */
	
	/**
	 * 构造函数
	 */
	public VoDownloadCheckPurvPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadCheckPurvPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 下载审批权限ID : String */
	public String getDownload_check_purv_id()
	{
		return getValue( ITEM_DOWNLOAD_CHECK_PURV_ID );
	}

	public void setDownload_check_purv_id( String download_check_purv_id1 )
	{
		setValue( ITEM_DOWNLOAD_CHECK_PURV_ID, download_check_purv_id1 );
	}

}

