package com.gwssi.collect.fileupload.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_file_upload_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectFileUploadTaskSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304271747300003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLLECT_TABLE = "collect_table" ;	/* �ɼ��� */
	public static final String ITEM_COLLECT_MODE = "collect_mode" ;	/* �ɼ�ģʽ */
	public static final String ITEM_FILE_STATUS = "file_status" ;	/* �ļ�״̬ */
	public static final String ITEM_FILE_DESCRIPTION = "file_description" ;	/* �ļ����� */
	public static final String ITEM_COLLECT_FILE_NAME = "collect_file_name" ;	/* �ɼ��ļ����� */
	public static final String ITEM_COLLECT_FILE_ID = "collect_file_id" ;	/* �ɼ��ļ�ID */
	
	/**
	 * ���캯��
	 */
	public VoCollectFileUploadTaskSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectFileUploadTaskSelectKey(DataBus value)
	{
		super(value);
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

}

