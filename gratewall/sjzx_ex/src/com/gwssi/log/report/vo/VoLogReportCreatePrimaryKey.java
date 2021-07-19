package com.gwssi.log.report.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[log_report_create]的数据对象类
 * @author Administrator
 *
 */
public class VoLogReportCreatePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201208061604290004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_LOG_REPORT_CREATE_ID = "log_report_create_id" ;	/* 日志报告id */
	
	/**
	 * 构造函数
	 */
	public VoLogReportCreatePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoLogReportCreatePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 日志报告id : String */
	public String getLog_report_create_id()
	{
		return getValue( ITEM_LOG_REPORT_CREATE_ID );
	}

	public void setLog_report_create_id( String log_report_create_id1 )
	{
		setValue( ITEM_LOG_REPORT_CREATE_ID, log_report_create_id1 );
	}

}

