package com.gwssi.collect.fileupload.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_file_upload_task]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectFileUploadTaskSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304271747300003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_COLLECT_TABLE = "collect_table" ;	/* 采集表 */
	public static final String ITEM_COLLECT_MODE = "collect_mode" ;	/* 采集模式 */
	public static final String ITEM_FILE_STATUS = "file_status" ;	/* 文件状态 */
	public static final String ITEM_FILE_DESCRIPTION = "file_description" ;	/* 文件描述 */
	public static final String ITEM_COLLECT_FILE_NAME = "collect_file_name" ;	/* 采集文件名称 */
	public static final String ITEM_COLLECT_FILE_ID = "collect_file_id" ;	/* 采集文件ID */
	
	/**
	 * 构造函数
	 */
	public VoCollectFileUploadTaskSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectFileUploadTaskSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 采集表 : String */
	public String getCollect_table()
	{
		return getValue( ITEM_COLLECT_TABLE );
	}

	public void setCollect_table( String collect_table1 )
	{
		setValue( ITEM_COLLECT_TABLE, collect_table1 );
	}

	/* 采集模式 : String */
	public String getCollect_mode()
	{
		return getValue( ITEM_COLLECT_MODE );
	}

	public void setCollect_mode( String collect_mode1 )
	{
		setValue( ITEM_COLLECT_MODE, collect_mode1 );
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

	/* 采集文件名称 : String */
	public String getCollect_file_name()
	{
		return getValue( ITEM_COLLECT_FILE_NAME );
	}

	public void setCollect_file_name( String collect_file_name1 )
	{
		setValue( ITEM_COLLECT_FILE_NAME, collect_file_name1 );
	}

	/* 采集文件ID : String */
	public String getCollect_file_id()
	{
		return getValue( ITEM_COLLECT_FILE_ID );
	}

	public void setCollect_file_id( String collect_file_id1 )
	{
		setValue( ITEM_COLLECT_FILE_ID, collect_file_id1 );
	}

}

