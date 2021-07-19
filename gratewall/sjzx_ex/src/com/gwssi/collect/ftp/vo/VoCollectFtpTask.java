package com.gwssi.collect.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_ftp_task]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectFtpTask extends VoBase
{
	private static final long serialVersionUID = 201304281352230002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_FTP_TASK_ID = "ftp_task_id" ;	/* FTP任务ID */
	public static final String ITEM_COLLECT_TASK_ID = "collect_task_id" ;	/* 采集任务ID */
	public static final String ITEM_SERVICE_NO = "service_no" ;		/* 任务编号 */
	public static final String ITEM_FILE_NAME_EN = "file_name_en" ;	/* 文件名称 */
	public static final String ITEM_FILE_NAME_CN = "file_name_cn" ;	/* 文件中文名称 */
	public static final String ITEM_COLLECT_MODE = "collect_mode" ;	/* 采集方式 */
	public static final String ITEM_COLLECT_TABLE = "collect_table" ;	/* 对应采集表 */
	public static final String ITEM_FILE_STATUS = "file_status" ;	/* 文件状态 */
	public static final String ITEM_FILE_DESCRIPTION = "file_description" ;	/* 文件描述 */
	public static final String ITEM_FILE_SEPEATOR = "file_sepeator" ;	/* 文件列分隔符 */
	public static final String ITEM_FILE_TITLE_TYPE = "file_title_type" ;	/* 文件标题行类型*/
	
	/**
	 * 构造函数
	 */
	public VoCollectFtpTask()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectFtpTask(DataBus value)
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

	/* 采集任务ID : String */
	public String getCollect_task_id()
	{
		return getValue( ITEM_COLLECT_TASK_ID );
	}

	public void setCollect_task_id( String collect_task_id1 )
	{
		setValue( ITEM_COLLECT_TASK_ID, collect_task_id1 );
	}

	/* 任务编号 : String */
	public String getService_no()
	{
		return getValue( ITEM_SERVICE_NO );
	}

	public void setService_no( String service_no1 )
	{
		setValue( ITEM_SERVICE_NO, service_no1 );
	}

	/* 文件名称 : String */
	public String getFile_name_en()
	{
		return getValue( ITEM_FILE_NAME_EN );
	}

	public void setFile_name_en( String file_name_en1 )
	{
		setValue( ITEM_FILE_NAME_EN, file_name_en1 );
	}

	/* 文件中文名称 : String */
	public String getFile_name_cn()
	{
		return getValue( ITEM_FILE_NAME_CN );
	}

	public void setFile_name_cn( String file_name_cn1 )
	{
		setValue( ITEM_FILE_NAME_CN, file_name_cn1 );
	}

	/* 采集方式 : String */
	public String getCollect_mode()
	{
		return getValue( ITEM_COLLECT_MODE );
	}

	public void setCollect_mode( String collect_mode1 )
	{
		setValue( ITEM_COLLECT_MODE, collect_mode1 );
	}

	/* 对应采集表 : String */
	public String getCollect_table()
	{
		return getValue( ITEM_COLLECT_TABLE );
	}

	public void setCollect_table( String collect_table1 )
	{
		setValue( ITEM_COLLECT_TABLE, collect_table1 );
	}

	/* 文件状态 : String */
	public String getFile_status()
	{
		return getValue( ITEM_FILE_STATUS );
	}

	public void setFile_status( String file_status1 )
	{
		setValue( ITEM_FILE_STATUS, file_status1 );
	}

	/* 文件描述 : String */
	public String getFile_description()
	{
		return getValue( ITEM_FILE_DESCRIPTION );
	}

	public void setFile_description( String file_description1 )
	{
		setValue( ITEM_FILE_DESCRIPTION, file_description1 );
	}
	
	/* 文件标题行类型 : String */
	public String getFile_title_type()
	{
		return getValue( ITEM_FILE_TITLE_TYPE );
	}

	public void setFile_title_type( String file_title_type1 )
	{
		setValue( ITEM_FILE_TITLE_TYPE, file_title_type1 );
	}
	
	/* 文件分隔符 : String */
	public String getFile_sepeator()
	{
		return getValue( ITEM_FILE_SEPEATOR );
	}

	public void setFile_sepeator( String file_sepeator1 )
	{
		setValue( ITEM_FILE_SEPEATOR, file_sepeator1 );
	}

}

