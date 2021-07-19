package com.gwssi.collect.fileupload.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_file_upload_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectFileUploadTaskPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304271747300004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_FILE_UPLOAD_TASK_ID = "file_upload_task_id" ;	/* �ļ��ϴ�����ID */
	
	/**
	 * ���캯��
	 */
	public VoCollectFileUploadTaskPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectFileUploadTaskPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �ļ��ϴ�����ID : String */
	public String getFile_upload_task_id()
	{
		return getValue( ITEM_FILE_UPLOAD_TASK_ID );
	}

	public void setFile_upload_task_id( String file_upload_task_id1 )
	{
		setValue( ITEM_FILE_UPLOAD_TASK_ID, file_upload_task_id1 );
	}

}

