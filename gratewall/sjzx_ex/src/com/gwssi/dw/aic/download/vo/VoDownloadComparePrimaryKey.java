package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_compare]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadComparePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200902201127580004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOWNLOAD_COMPARE_ID = "download_compare_id" ;	/* �ȶ�����ID */
	
	/**
	 * ���캯��
	 */
	public VoDownloadComparePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadComparePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �ȶ�����ID : String */
	public String getDownload_compare_id()
	{
		return getValue( ITEM_DOWNLOAD_COMPARE_ID );
	}

	public void setDownload_compare_id( String download_compare_id1 )
	{
		setValue( ITEM_DOWNLOAD_COMPARE_ID, download_compare_id1 );
	}

}

