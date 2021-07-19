package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_status]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadStatusPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809081126150004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOWNLOAD_STATUS_ID = "download_status_id" ;	/* ����״̬ID */
	
	/**
	 * ���캯��
	 */
	public VoDownloadStatusPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadStatusPrimaryKey(DataBus value)
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

}

