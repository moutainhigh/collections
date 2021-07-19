package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[log_report_use]的数据对象类
 * @author Administrator
 *
 */
public class VoLogReportUse extends VoBase
{
	private static final long serialVersionUID = 201208061616070006L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_LOG_REPORT_USE_ID = "log_report_use_id" ;	/* 日志报告操作id */
	public static final String ITEM_LOG_REPORT_CREATE_ID = "log_report_create_id" ;	/* 日志报告生成id */
	public static final String ITEM_BROWSER_PERSON = "browser_person" ;	/* 浏览人 */
	public static final String ITEM_BROWSER_DATE = "browser_date" ;	/* 浏览日期 */
	public static final String ITEM_OPERATE = "operate" ;			/* 操作 */
	public static final String ITEM_FILENAME = "filename" ;			/* 文件名 */
	public static final String ITEM_PATH = "path" ;					/* 路径 */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* 时间戳 */
	
	/**
	 * 构造函数
	 */
	public VoLogReportUse()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoLogReportUse(DataBus value)
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

	/* 日志报告生成id : String */
	public String getLog_report_create_id()
	{
		return getValue( ITEM_LOG_REPORT_CREATE_ID );
	}

	public void setLog_report_create_id( String log_report_create_id1 )
	{
		setValue( ITEM_LOG_REPORT_CREATE_ID, log_report_create_id1 );
	}

	/* 浏览人 : String */
	public String getBrowser_person()
	{
		return getValue( ITEM_BROWSER_PERSON );
	}

	public void setBrowser_person( String browser_person1 )
	{
		setValue( ITEM_BROWSER_PERSON, browser_person1 );
	}

	/* 浏览日期 : String */
	public String getBrowser_date()
	{
		return getValue( ITEM_BROWSER_DATE );
	}

	public void setBrowser_date( String browser_date1 )
	{
		setValue( ITEM_BROWSER_DATE, browser_date1 );
	}

	/* 操作 : String */
	public String getOperate()
	{
		return getValue( ITEM_OPERATE );
	}

	public void setOperate( String operate1 )
	{
		setValue( ITEM_OPERATE, operate1 );
	}

	/* 文件名 : String */
	public String getFilename()
	{
		return getValue( ITEM_FILENAME );
	}

	public void setFilename( String filename1 )
	{
		setValue( ITEM_FILENAME, filename1 );
	}

	/* 路径 : String */
	public String getPath()
	{
		return getValue( ITEM_PATH );
	}

	public void setPath( String path1 )
	{
		setValue( ITEM_PATH, path1 );
	}

	/* 时间戳 : String */
	public String getTimestamp()
	{
		return getValue( ITEM_TIMESTAMP );
	}

	public void setTimestamp( String timestamp1 )
	{
		setValue( ITEM_TIMESTAMP, timestamp1 );
	}

}

