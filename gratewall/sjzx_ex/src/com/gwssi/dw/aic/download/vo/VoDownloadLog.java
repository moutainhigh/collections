package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_log]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadLog extends VoBase
{
	private static final long serialVersionUID = 200812261332290002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DOWNLOAD_LOG_ID = "download_log_id" ;	/* 下载日志ID */
	public static final String ITEM_DOWNLOAD_STATUS_ID = "download_status_id" ;	/* 下载情况ID */
	public static final String ITEM_OPERNAME = "opername" ;			/* 操作名称 */
	public static final String ITEM_OPERDATE = "operdate" ;			/* 操作日期 */
	public static final String ITEM_OPERTIME = "opertime" ;			/* 操作时间 */
	public static final String ITEM_OPERTOR = "opertor" ;			/* 操作者 */
	public static final String ITEM_OPERDEPT = "operdept" ;			/* 操作者部门 */
	public static final String ITEM_DOWNLOAD_COUNT = "download_count" ;	/* 记录数量 */
	public static final String ITEM_DOWNLOAD_COND = "download_cond" ;	/* 完整查询条件 */
	public static final String ITEM_OPERTYPE = "opertype" ;             /* 操作类型 */
	
	/**
	 * 构造函数
	 */
	public VoDownloadLog()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadLog(DataBus value)
	{
		super(value);
	}
	
	public String getOpertype()
	{
		return getValue( ITEM_OPERTYPE );
	}

	public void setOpertype( String opertype )
	{
		setValue( ITEM_OPERTYPE, opertype );
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

	/* 下载情况ID : String */
	public String getDownload_status_id()
	{
		return getValue( ITEM_DOWNLOAD_STATUS_ID );
	}

	public void setDownload_status_id( String download_status_id1 )
	{
		setValue( ITEM_DOWNLOAD_STATUS_ID, download_status_id1 );
	}

	/* 操作名称 : String */
	public String getOpername()
	{
		return getValue( ITEM_OPERNAME );
	}

	public void setOpername( String opername1 )
	{
		setValue( ITEM_OPERNAME, opername1 );
	}

	/* 操作日期 : String */
	public String getOperdate()
	{
		return getValue( ITEM_OPERDATE );
	}

	public void setOperdate( String operdate1 )
	{
		setValue( ITEM_OPERDATE, operdate1 );
	}

	/* 操作时间 : String */
	public String getOpertime()
	{
		return getValue( ITEM_OPERTIME );
	}

	public void setOpertime( String opertime1 )
	{
		setValue( ITEM_OPERTIME, opertime1 );
	}

	/* 操作者 : String */
	public String getOpertor()
	{
		return getValue( ITEM_OPERTOR );
	}

	public void setOpertor( String opertor1 )
	{
		setValue( ITEM_OPERTOR, opertor1 );
	}

	/* 操作者部门 : String */
	public String getOperdept()
	{
		return getValue( ITEM_OPERDEPT );
	}

	public void setOperdept( String operdept1 )
	{
		setValue( ITEM_OPERDEPT, operdept1 );
	}

	/* 记录数量 : String */
	public String getDownload_count()
	{
		return getValue( ITEM_DOWNLOAD_COUNT );
	}

	public void setDownload_count( String download_count1 )
	{
		setValue( ITEM_DOWNLOAD_COUNT, download_count1 );
	}

	/* 完整查询条件 : String */
	public String getDownload_cond()
	{
		return getValue( ITEM_DOWNLOAD_COND );
	}

	public void setDownload_cond( String download_cond1 )
	{
		setValue( ITEM_DOWNLOAD_COND, download_cond1 );
	}

}

