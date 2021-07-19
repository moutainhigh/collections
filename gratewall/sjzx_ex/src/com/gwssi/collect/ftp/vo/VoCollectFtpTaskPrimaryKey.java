package com.gwssi.collect.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_ftp_task]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectFtpTaskPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304281352240004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_FTP_TASK_ID = "ftp_task_id" ;	/* FTP任务ID */
	
	/**
	 * 构造函数
	 */
	public VoCollectFtpTaskPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectFtpTaskPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* FTP任务ID : String */
	public String getFtp_task_id()
	{
		return getValue( ITEM_FTP_TASK_ID );
	}

	public void setFtp_task_id( String ftp_task_id1 )
	{
		setValue( ITEM_FTP_TASK_ID, ftp_task_id1 );
	}

}

