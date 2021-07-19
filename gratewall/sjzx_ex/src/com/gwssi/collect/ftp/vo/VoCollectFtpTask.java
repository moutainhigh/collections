package com.gwssi.collect.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_ftp_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectFtpTask extends VoBase
{
	private static final long serialVersionUID = 201304281352230002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_FTP_TASK_ID = "ftp_task_id" ;	/* FTP����ID */
	public static final String ITEM_COLLECT_TASK_ID = "collect_task_id" ;	/* �ɼ�����ID */
	public static final String ITEM_SERVICE_NO = "service_no" ;		/* ������ */
	public static final String ITEM_FILE_NAME_EN = "file_name_en" ;	/* �ļ����� */
	public static final String ITEM_FILE_NAME_CN = "file_name_cn" ;	/* �ļ��������� */
	public static final String ITEM_COLLECT_MODE = "collect_mode" ;	/* �ɼ���ʽ */
	public static final String ITEM_COLLECT_TABLE = "collect_table" ;	/* ��Ӧ�ɼ��� */
	public static final String ITEM_FILE_STATUS = "file_status" ;	/* �ļ�״̬ */
	public static final String ITEM_FILE_DESCRIPTION = "file_description" ;	/* �ļ����� */
	public static final String ITEM_FILE_SEPEATOR = "file_sepeator" ;	/* �ļ��зָ��� */
	public static final String ITEM_FILE_TITLE_TYPE = "file_title_type" ;	/* �ļ�����������*/
	
	/**
	 * ���캯��
	 */
	public VoCollectFtpTask()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectFtpTask(DataBus value)
	{
		super(value);
	}
	
	/* FTP����ID : String */
	public String getFtp_task_id()
	{
		return getValue( ITEM_FTP_TASK_ID );
	}

	public void setFtp_task_id( String ftp_task_id1 )
	{
		setValue( ITEM_FTP_TASK_ID, ftp_task_id1 );
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

	/* ������ : String */
	public String getService_no()
	{
		return getValue( ITEM_SERVICE_NO );
	}

	public void setService_no( String service_no1 )
	{
		setValue( ITEM_SERVICE_NO, service_no1 );
	}

	/* �ļ����� : String */
	public String getFile_name_en()
	{
		return getValue( ITEM_FILE_NAME_EN );
	}

	public void setFile_name_en( String file_name_en1 )
	{
		setValue( ITEM_FILE_NAME_EN, file_name_en1 );
	}

	/* �ļ��������� : String */
	public String getFile_name_cn()
	{
		return getValue( ITEM_FILE_NAME_CN );
	}

	public void setFile_name_cn( String file_name_cn1 )
	{
		setValue( ITEM_FILE_NAME_CN, file_name_cn1 );
	}

	/* �ɼ���ʽ : String */
	public String getCollect_mode()
	{
		return getValue( ITEM_COLLECT_MODE );
	}

	public void setCollect_mode( String collect_mode1 )
	{
		setValue( ITEM_COLLECT_MODE, collect_mode1 );
	}

	/* ��Ӧ�ɼ��� : String */
	public String getCollect_table()
	{
		return getValue( ITEM_COLLECT_TABLE );
	}

	public void setCollect_table( String collect_table1 )
	{
		setValue( ITEM_COLLECT_TABLE, collect_table1 );
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
	
	/* �ļ����������� : String */
	public String getFile_title_type()
	{
		return getValue( ITEM_FILE_TITLE_TYPE );
	}

	public void setFile_title_type( String file_title_type1 )
	{
		setValue( ITEM_FILE_TITLE_TYPE, file_title_type1 );
	}
	
	/* �ļ��ָ��� : String */
	public String getFile_sepeator()
	{
		return getValue( ITEM_FILE_SEPEATOR );
	}

	public void setFile_sepeator( String file_sepeator1 )
	{
		setValue( ITEM_FILE_SEPEATOR, file_sepeator1 );
	}

}

