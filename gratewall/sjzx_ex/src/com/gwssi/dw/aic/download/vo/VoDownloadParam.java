package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadParam extends VoBase
{
	private static final long serialVersionUID = 200812181610450002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOWNLOAD_PARAM_ID = "download_param_id" ;	/* ���ز���ID */
	public static final String ITEM_DOWNLOAD_STATUS_ID = "download_status_id" ;	/* �������ID */
	public static final String ITEM_PARAM_TABLE = "param_table" ;	/* ������ */
	public static final String ITEM_PARAM_COLUMN = "param_column" ;	/* �����ֶ� */
	public static final String ITEM_PARAM_VALUE = "param_value" ;	/* ����Ĭ��ֵ */
	public static final String ITEM_PARAM_SEQUE = "param_seque" ;	/* ����˳�� */
	
	/**
	 * ���캯��
	 */
	public VoDownloadParam()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadParam(DataBus value)
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

	/* �������ID : String */
	public String getDownload_status_id()
	{
		return getValue( ITEM_DOWNLOAD_STATUS_ID );
	}

	public void setDownload_status_id( String download_status_id1 )
	{
		setValue( ITEM_DOWNLOAD_STATUS_ID, download_status_id1 );
	}

	/* ������ : String */
	public String getParam_table()
	{
		return getValue( ITEM_PARAM_TABLE );
	}

	public void setParam_table( String param_table1 )
	{
		setValue( ITEM_PARAM_TABLE, param_table1 );
	}

	/* �����ֶ� : String */
	public String getParam_column()
	{
		return getValue( ITEM_PARAM_COLUMN );
	}

	public void setParam_column( String param_column1 )
	{
		setValue( ITEM_PARAM_COLUMN, param_column1 );
	}

	/* ����Ĭ��ֵ : String */
	public String getParam_value()
	{
		return getValue( ITEM_PARAM_VALUE );
	}

	public void setParam_value( String param_value1 )
	{
		setValue( ITEM_PARAM_VALUE, param_value1 );
	}

	/* ����˳�� : String */
	public String getParam_seque()
	{
		return getValue( ITEM_PARAM_SEQUE );
	}

	public void setParam_seque( String param_seque1 )
	{
		setValue( ITEM_PARAM_SEQUE, param_seque1 );
	}

}

