package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_status]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadStatusSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809081126150003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOWNLOAD_STATUS_ID = "download_status_id" ;	/* ����״̬ID */
	public static final String ITEM_APPLY_USER = "apply_user" ;		/* ������ */
	public static final String ITEM_APPLY_NAME = "apply_name" ;		/* �������� */
	public static final String ITEM_APPLY_DATE = "apply_date" ;		/* �������� */
	public static final String ITEM_STATUS = "status" ;				/* ����״̬ */
	
	/**
	 * ���캯��
	 */
	public VoDownloadStatusSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadStatusSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ����״̬ID : String */
	public String getDownload_status_id()
	{
		return getValue( ITEM_DOWNLOAD_STATUS_ID );
	}

	public void setDownload_status_id( String download_status_id1 )
	{
		setValue( ITEM_DOWNLOAD_STATUS_ID, download_status_id1 );
	}

	/* ������ : String */
	public String getApply_user()
	{
		return getValue( ITEM_APPLY_USER );
	}

	public void setApply_user( String apply_user1 )
	{
		setValue( ITEM_APPLY_USER, apply_user1 );
	}

	/* �������� : String */
	public String getApply_name()
	{
		return getValue( ITEM_APPLY_NAME );
	}

	public void setApply_name( String apply_name1 )
	{
		setValue( ITEM_APPLY_NAME, apply_name1 );
	}

	/* �������� : String */
	public String getApply_date()
	{
		return getValue( ITEM_APPLY_DATE );
	}

	public void setApply_date( String apply_date1 )
	{
		setValue( ITEM_APPLY_DATE, apply_date1 );
	}

	/* ����״̬ : String */
	public String getStatus()
	{
		return getValue( ITEM_STATUS );
	}

	public void setStatus( String status1 )
	{
		setValue( ITEM_STATUS, status1 );
	}

}

