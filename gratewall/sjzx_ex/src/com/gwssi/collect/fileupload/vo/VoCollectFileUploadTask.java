package com.gwssi.collect.fileupload.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_file_upload_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectFileUploadTask extends VoBase
{
	private static final long serialVersionUID = 201304271747300002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_FILE_UPLOAD_TASK_ID = "file_upload_task_id" ;	/* �ļ��ϴ�����ID */
	public static final String ITEM_COLLECT_TASK_ID = "collect_task_id" ;	/* �ɼ�����ID */
	public static final String ITEM_COLLECT_TABLE = "collect_table" ;	/* �ɼ��� */
	public static final String ITEM_COLLECT_MODE = "collect_mode" ;	/* �ɼ�ģʽ */
	public static final String ITEM_FILE_STATUS = "file_status" ;	/* �ļ�״̬ */
	public static final String ITEM_FILE_DESCRIPTION = "file_description" ;	/* �ļ����� */
	public static final String ITEM_COLLECT_FILE_NAME = "collect_file_name" ;	/* �ɼ��ļ����� */
	public static final String ITEM_COLLECT_FILE_ID = "collect_file_id" ;	/* �ɼ��ļ�ID */
	public static final String ITEM_CHECK_RESULT_FILE_NAME = "check_result_file_name" ;	/* У�����ļ����� */
	public static final String ITEM_CHECK_RESULT_FILE_ID = "check_result_file_id" ;	/* У�����ļ�ID */
	
	/**
	 * ���캯��
	 */
	public VoCollectFileUploadTask()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectFileUploadTask(DataBus value)
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

	/* �ɼ�����ID : String */
	public String getCollect_task_id()
	{
		return getValue( ITEM_COLLECT_TASK_ID );
	}

	public void setCollect_task_id( String collect_task_id1 )
	{
		setValue( ITEM_COLLECT_TASK_ID, collect_task_id1 );
	}

	/* �ɼ��� : String */
	public String getCollect_table()
	{
		return getValue( ITEM_COLLECT_TABLE );
	}

	public void setCollect_table( String collect_table1 )
	{
		setValue( ITEM_COLLECT_TABLE, collect_table1 );
	}

	/* �ɼ�ģʽ : String */
	public String getCollect_mode()
	{
		return getValue( ITEM_COLLECT_MODE );
	}

	public void setCollect_mode( String collect_mode1 )
	{
		setValue( ITEM_COLLECT_MODE, collect_mode1 );
	}

	/* �ļ�״̬ : String */
	public String getFile_status()
	{
		return getValue( ITEM_FILE_STATUS );
	}

	public void setFile_status( String file_status1 )
	{
		setValue( ITEM_FILE_STATUS, file_status1 );
	}

	/* �ļ����� : String */
	public String getFile_description()
	{
		return getValue( ITEM_FILE_DESCRIPTION );
	}

	public void setFile_description( String file_description1 )
	{
		setValue( ITEM_FILE_DESCRIPTION, file_description1 );
	}

	/* �ɼ��ļ����� : String */
	public String getCollect_file_name()
	{
		return getValue( ITEM_COLLECT_FILE_NAME );
	}

	public void setCollect_file_name( String collect_file_name1 )
	{
		setValue( ITEM_COLLECT_FILE_NAME, collect_file_name1 );
	}

	/* �ɼ��ļ�ID : String */
	public String getCollect_file_id()
	{
		return getValue( ITEM_COLLECT_FILE_ID );
	}

	public void setCollect_file_id( String collect_file_id1 )
	{
		setValue( ITEM_COLLECT_FILE_ID, collect_file_id1 );
	}

	/* У�����ļ����� : String */
	public String getCheck_result_file_name()
	{
		return getValue( ITEM_CHECK_RESULT_FILE_NAME );
	}

	public void setCheck_result_file_name( String check_result_file_name1 )
	{
		setValue( ITEM_CHECK_RESULT_FILE_NAME, check_result_file_name1 );
	}

	/* У�����ļ�ID : String */
	public String getCheck_result_file_id()
	{
		return getValue( ITEM_CHECK_RESULT_FILE_ID );
	}

	public void setCheck_result_file_id( String check_result_file_id1 )
	{
		setValue( ITEM_CHECK_RESULT_FILE_ID, check_result_file_id1 );
	}

}

