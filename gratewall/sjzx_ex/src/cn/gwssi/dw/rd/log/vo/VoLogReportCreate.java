package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[log_report_create]的数据对象类
 * @author Administrator
 *
 */
public class VoLogReportCreate extends VoBase
{
	private static final long serialVersionUID = 201208061604290002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_LOG_REPORT_CREATE_ID = "log_report_create_id" ;	/* 日志报告id */
	public static final String ITEM_REPORT_NAME = "report_name" ;	/* 报告名称 */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* 建立日期 */
	public static final String ITEM_LAST_MENDER = "last_mender" ;	/* 最后修改者 */
	public static final String ITEM_PUBLISH_DATE = "publish_date" ;	/* 发布日期 */
	public static final String ITEM_PUBLISH_PERSON = "publish_person" ;	/* 发布人 */
	public static final String ITEM_STATE = "state" ;				/* 状态 */
	public static final String ITEM_OPERATE = "operate" ;			/* 操作 */
	public static final String ITEM_FILENAME = "filename" ;			/* 文件名 */
	public static final String ITEM_PATH = "path" ;					/* 路径 */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* 时间戳 */
	
	/**
	 * 构造函数
	 */
	public VoLogReportCreate()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoLogReportCreate(DataBus value)
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

	/* 报告名称 : String */
	public String getReport_name()
	{
		return getValue( ITEM_REPORT_NAME );
	}

	public void setReport_name( String report_name1 )
	{
		setValue( ITEM_REPORT_NAME, report_name1 );
	}

	/* 建立日期 : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

	/* 最后修改者 : String */
	public String getLast_mender()
	{
		return getValue( ITEM_LAST_MENDER );
	}

	public void setLast_mender( String last_mender1 )
	{
		setValue( ITEM_LAST_MENDER, last_mender1 );
	}

	/* 发布日期 : String */
	public String getPublish_date()
	{
		return getValue( ITEM_PUBLISH_DATE );
	}

	public void setPublish_date( String publish_date1 )
	{
		setValue( ITEM_PUBLISH_DATE, publish_date1 );
	}

	/* 发布人 : String */
	public String getPublish_person()
	{
		return getValue( ITEM_PUBLISH_PERSON );
	}

	public void setPublish_person( String publish_person1 )
	{
		setValue( ITEM_PUBLISH_PERSON, publish_person1 );
	}

	/* 状态 : String */
	public String getState()
	{
		return getValue( ITEM_STATE );
	}

	public void setState( String state1 )
	{
		setValue( ITEM_STATE, state1 );
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

