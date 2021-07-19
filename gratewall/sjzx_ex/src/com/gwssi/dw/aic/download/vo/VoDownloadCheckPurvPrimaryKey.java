package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_check_purv]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadCheckPurvPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200903171409260004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOWNLOAD_CHECK_PURV_ID = "download_check_purv_id" ;	/* ��������Ȩ��ID */
	
	/**
	 * ���캯��
	 */
	public VoDownloadCheckPurvPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadCheckPurvPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ��������Ȩ��ID : String */
	public String getDownload_check_purv_id()
	{
		return getValue( ITEM_DOWNLOAD_CHECK_PURV_ID );
	}

	public void setDownload_check_purv_id( String download_check_purv_id1 )
	{
		setValue( ITEM_DOWNLOAD_CHECK_PURV_ID, download_check_purv_id1 );
	}

}

