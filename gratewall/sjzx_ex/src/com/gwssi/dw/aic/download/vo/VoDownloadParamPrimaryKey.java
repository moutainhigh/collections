package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_param]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadParamPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200812181610460004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DOWNLOAD_PARAM_ID = "download_param_id" ;	/* 下载参数ID */
	
	/**
	 * 构造函数
	 */
	public VoDownloadParamPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadParamPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 下载参数ID : String */
	public String getDownload_param_id()
	{
		return getValue( ITEM_DOWNLOAD_PARAM_ID );
	}

	public void setDownload_param_id( String download_param_id1 )
	{
		setValue( ITEM_DOWNLOAD_PARAM_ID, download_param_id1 );
	}

}

