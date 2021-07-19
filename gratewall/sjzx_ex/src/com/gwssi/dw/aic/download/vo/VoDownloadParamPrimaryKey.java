package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadParamPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200812181610460004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOWNLOAD_PARAM_ID = "download_param_id" ;	/* ���ز���ID */
	
	/**
	 * ���캯��
	 */
	public VoDownloadParamPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadParamPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ���ز���ID : String */
	public String getDownload_param_id()
	{
		return getValue( ITEM_DOWNLOAD_PARAM_ID );
	}

	public void setDownload_param_id( String download_param_id1 )
	{
		setValue( ITEM_DOWNLOAD_PARAM_ID, download_param_id1 );
	}

}

