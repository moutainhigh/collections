package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_log]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadLogPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200812261332290004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOWNLOAD_LOG_ID = "download_log_id" ;	/* ������־ID */
	
	/**
	 * ���캯��
	 */
	public VoDownloadLogPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadLogPrimaryKey(DataBus value)
	{
		super(value);
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

}

