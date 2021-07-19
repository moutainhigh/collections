package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_log]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadLog extends VoBase
{
	private static final long serialVersionUID = 200812261332290002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOWNLOAD_LOG_ID = "download_log_id" ;	/* ������־ID */
	public static final String ITEM_DOWNLOAD_STATUS_ID = "download_status_id" ;	/* �������ID */
	public static final String ITEM_OPERNAME = "opername" ;			/* �������� */
	public static final String ITEM_OPERDATE = "operdate" ;			/* �������� */
	public static final String ITEM_OPERTIME = "opertime" ;			/* ����ʱ�� */
	public static final String ITEM_OPERTOR = "opertor" ;			/* ������ */
	public static final String ITEM_OPERDEPT = "operdept" ;			/* �����߲��� */
	public static final String ITEM_DOWNLOAD_COUNT = "download_count" ;	/* ��¼���� */
	public static final String ITEM_DOWNLOAD_COND = "download_cond" ;	/* ������ѯ���� */
	public static final String ITEM_OPERTYPE = "opertype" ;             /* �������� */
	
	/**
	 * ���캯��
	 */
	public VoDownloadLog()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadLog(DataBus value)
	{
		super(value);
	}
	
	public String getOpertype()
	{
		return getValue( ITEM_OPERTYPE );
	}

	public void setOpertype( String opertype )
	{
		setValue( ITEM_OPERTYPE, opertype );
	}
	
	/* ������־ID : String */
	public String getDownload_log_id()
	{
		return getValue( ITEM_DOWNLOAD_LOG_ID );
	}

	public void setDownload_log_id( String download_log_id1 )
	{
		setValue( ITEM_DOWNLOAD_LOG_ID, download_log_id1 );
	}

	/* �������ID : String */
	public String getDownload_status_id()
	{
		return getValue( ITEM_DOWNLOAD_STATUS_ID );
	}

	public void setDownload_status_id( String download_status_id1 )
	{
		setValue( ITEM_DOWNLOAD_STATUS_ID, download_status_id1 );
	}

	/* �������� : String */
	public String getOpername()
	{
		return getValue( ITEM_OPERNAME );
	}

	public void setOpername( String opername1 )
	{
		setValue( ITEM_OPERNAME, opername1 );
	}

	/* �������� : String */
	public String getOperdate()
	{
		return getValue( ITEM_OPERDATE );
	}

	public void setOperdate( String operdate1 )
	{
		setValue( ITEM_OPERDATE, operdate1 );
	}

	/* ����ʱ�� : String */
	public String getOpertime()
	{
		return getValue( ITEM_OPERTIME );
	}

	public void setOpertime( String opertime1 )
	{
		setValue( ITEM_OPERTIME, opertime1 );
	}

	/* ������ : String */
	public String getOpertor()
	{
		return getValue( ITEM_OPERTOR );
	}

	public void setOpertor( String opertor1 )
	{
		setValue( ITEM_OPERTOR, opertor1 );
	}

	/* �����߲��� : String */
	public String getOperdept()
	{
		return getValue( ITEM_OPERDEPT );
	}

	public void setOperdept( String operdept1 )
	{
		setValue( ITEM_OPERDEPT, operdept1 );
	}

	/* ��¼���� : String */
	public String getDownload_count()
	{
		return getValue( ITEM_DOWNLOAD_COUNT );
	}

	public void setDownload_count( String download_count1 )
	{
		setValue( ITEM_DOWNLOAD_COUNT, download_count1 );
	}

	/* ������ѯ���� : String */
	public String getDownload_cond()
	{
		return getValue( ITEM_DOWNLOAD_COND );
	}

	public void setDownload_cond( String download_cond1 )
	{
		setValue( ITEM_DOWNLOAD_COND, download_cond1 );
	}

}

