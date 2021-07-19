package com.gwssi.sysmgr.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_purv]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadPurvPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809051722060004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOWNLOAD_PURV_ID = "download_purv_id" ;	/* ���� */
	
	/**
	 * ���캯��
	 */
	public VoDownloadPurvPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadPurvPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ���� : String */
	public String getDownload_purv_id()
	{
		return getValue( ITEM_DOWNLOAD_PURV_ID );
	}

	public void setDownload_purv_id( String download_purv_id1 )
	{
		setValue( ITEM_DOWNLOAD_PURV_ID, download_purv_id1 );
	}

}

