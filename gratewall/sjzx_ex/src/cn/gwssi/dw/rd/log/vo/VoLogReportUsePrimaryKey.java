package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[log_report_use]的数据对象类
 * @author Administrator
 *
 */
public class VoLogReportUsePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201208061616070008L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_LOG_REPORT_USE_ID = "log_report_use_id" ;	/* 日志报告操作id */
	
	/**
	 * 构造函数
	 */
	public VoLogReportUsePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoLogReportUsePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 日志报告操作id : String */
	public String getLog_report_use_id()
	{
		return getValue( ITEM_LOG_REPORT_USE_ID );
	}

	public void setLog_report_use_id( String log_report_use_id1 )
	{
		setValue( ITEM_LOG_REPORT_USE_ID, log_report_use_id1 );
	}

}

