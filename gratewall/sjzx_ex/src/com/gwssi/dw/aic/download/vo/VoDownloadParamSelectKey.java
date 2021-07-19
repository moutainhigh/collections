package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_param]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadParamSelectKey extends VoBase
{
	private static final long serialVersionUID = 200812181610460003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DOWNLOAD_PARAM_ID = "download_param_id" ;	/* 下载参数ID */
	public static final String ITEM_DOWNLOAD_STATUS_ID = "download_status_id" ;	/* 下载情况ID */
	public static final String ITEM_PARAM_TABLE = "param_table" ;	/* 参数表 */
	public static final String ITEM_PARAM_COLUMN = "param_column" ;	/* 参数字段 */
	
	/**
	 * 构造函数
	 */
	public VoDownloadParamSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadParamSelectKey(DataBus value)
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

	/* 下载情况ID : String */
	public String getDownload_status_id()
	{
		return getValue( ITEM_DOWNLOAD_STATUS_ID );
	}

	public void setDownload_status_id( String download_status_id1 )
	{
		setValue( ITEM_DOWNLOAD_STATUS_ID, download_status_id1 );
	}

	/* 参数表 : String */
	public String getParam_table()
	{
		return getValue( ITEM_PARAM_TABLE );
	}

	public void setParam_table( String param_table1 )
	{
		setValue( ITEM_PARAM_TABLE, param_table1 );
	}

	/* 参数字段 : String */
	public String getParam_column()
	{
		return getValue( ITEM_PARAM_COLUMN );
	}

	public void setParam_column( String param_column1 )
	{
		setValue( ITEM_PARAM_COLUMN, param_column1 );
	}

}

