package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_compare]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadComparePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200902201127580004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DOWNLOAD_COMPARE_ID = "download_compare_id" ;	/* 比对下载ID */
	
	/**
	 * 构造函数
	 */
	public VoDownloadComparePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadComparePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 比对下载ID : String */
	public String getDownload_compare_id()
	{
		return getValue( ITEM_DOWNLOAD_COMPARE_ID );
	}

	public void setDownload_compare_id( String download_compare_id1 )
	{
		setValue( ITEM_DOWNLOAD_COMPARE_ID, download_compare_id1 );
	}

}

