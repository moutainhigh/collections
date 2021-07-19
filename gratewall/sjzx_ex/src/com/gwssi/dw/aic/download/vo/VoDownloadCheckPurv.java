package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_check_purv]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadCheckPurv extends VoBase
{
	private static final long serialVersionUID = 200903171409260002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOWNLOAD_CHECK_PURV_ID = "download_check_purv_id" ;	/* ��������Ȩ��ID */
	public static final String ITEM_ROLEID = "roleid" ;				/* ��ɫID */
	public static final String ITEM_MAX_SIZE = "max_size" ;			/* ���޷�ֵ */
	public static final String ITEM_MIN_SIZE = "min_size" ;			/* ���޷�ֵ */
	
	/**
	 * ���캯��
	 */
	public VoDownloadCheckPurv()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadCheckPurv(DataBus value)
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

	/* ��ɫID : String */
	public String getRoleid()
	{
		return getValue( ITEM_ROLEID );
	}

	public void setRoleid( String roleid1 )
	{
		setValue( ITEM_ROLEID, roleid1 );
	}

	/* ���޷�ֵ : String */
	public String getMax_size()
	{
		return getValue( ITEM_MAX_SIZE );
	}

	public void setMax_size( String max_size1 )
	{
		setValue( ITEM_MAX_SIZE, max_size1 );
	}

	/* ���޷�ֵ : String */
	public String getMin_size()
	{
		return getValue( ITEM_MIN_SIZE );
	}

	public void setMin_size( String min_size1 )
	{
		setValue( ITEM_MIN_SIZE, min_size1 );
	}

}

