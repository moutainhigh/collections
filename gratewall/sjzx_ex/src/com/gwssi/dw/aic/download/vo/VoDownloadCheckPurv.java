package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_check_purv]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadCheckPurv extends VoBase
{
	private static final long serialVersionUID = 200903171409260002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DOWNLOAD_CHECK_PURV_ID = "download_check_purv_id" ;	/* 下载审批权限ID */
	public static final String ITEM_ROLEID = "roleid" ;				/* 角色ID */
	public static final String ITEM_MAX_SIZE = "max_size" ;			/* 上限阀值 */
	public static final String ITEM_MIN_SIZE = "min_size" ;			/* 下限阀值 */
	
	/**
	 * 构造函数
	 */
	public VoDownloadCheckPurv()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadCheckPurv(DataBus value)
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

	/* 角色ID : String */
	public String getRoleid()
	{
		return getValue( ITEM_ROLEID );
	}

	public void setRoleid( String roleid1 )
	{
		setValue( ITEM_ROLEID, roleid1 );
	}

	/* 上限阀值 : String */
	public String getMax_size()
	{
		return getValue( ITEM_MAX_SIZE );
	}

	public void setMax_size( String max_size1 )
	{
		setValue( ITEM_MAX_SIZE, max_size1 );
	}

	/* 下限阀值 : String */
	public String getMin_size()
	{
		return getValue( ITEM_MIN_SIZE );
	}

	public void setMin_size( String min_size1 )
	{
		setValue( ITEM_MIN_SIZE, min_size1 );
	}

}

