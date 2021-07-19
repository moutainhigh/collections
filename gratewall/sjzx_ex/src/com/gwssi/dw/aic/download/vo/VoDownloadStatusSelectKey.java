package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_status]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadStatusSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809081126150003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DOWNLOAD_STATUS_ID = "download_status_id" ;	/* 下载状态ID */
	public static final String ITEM_APPLY_USER = "apply_user" ;		/* 申请人 */
	public static final String ITEM_APPLY_NAME = "apply_name" ;		/* 申请名称 */
	public static final String ITEM_APPLY_DATE = "apply_date" ;		/* 申请日期 */
	public static final String ITEM_STATUS = "status" ;				/* 申请状态 */
	
	/**
	 * 构造函数
	 */
	public VoDownloadStatusSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadStatusSelectKey(DataBus value)
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

	/* 申请人 : String */
	public String getApply_user()
	{
		return getValue( ITEM_APPLY_USER );
	}

	public void setApply_user( String apply_user1 )
	{
		setValue( ITEM_APPLY_USER, apply_user1 );
	}

	/* 申请名称 : String */
	public String getApply_name()
	{
		return getValue( ITEM_APPLY_NAME );
	}

	public void setApply_name( String apply_name1 )
	{
		setValue( ITEM_APPLY_NAME, apply_name1 );
	}

	/* 申请日期 : String */
	public String getApply_date()
	{
		return getValue( ITEM_APPLY_DATE );
	}

	public void setApply_date( String apply_date1 )
	{
		setValue( ITEM_APPLY_DATE, apply_date1 );
	}

	/* 申请状态 : String */
	public String getStatus()
	{
		return getValue( ITEM_STATUS );
	}

	public void setStatus( String status1 )
	{
		setValue( ITEM_STATUS, status1 );
	}

}

