package com.gwssi.collect.fileupload.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_file_upload_task]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectFileUploadTaskPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304271747300004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_FILE_UPLOAD_TASK_ID = "file_upload_task_id" ;	/* 文件上传任务ID */
	
	/**
	 * 构造函数
	 */
	public VoCollectFileUploadTaskPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectFileUploadTaskPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 文件上传任务ID : String */
	public String getFile_upload_task_id()
	{
		return getValue( ITEM_FILE_UPLOAD_TASK_ID );
	}

	public void setFile_upload_task_id( String file_upload_task_id1 )
	{
		setValue( ITEM_FILE_UPLOAD_TASK_ID, file_upload_task_id1 );
	}

}

