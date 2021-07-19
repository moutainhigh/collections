package com.gwssi.sysmgr.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_purv]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadPurv extends VoBase
{
	private static final long serialVersionUID = 200809051722060002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DOWNLOAD_PURV_ID = "download_purv_id" ;	/* ���� */
	public static final String ITEM_AGENCY_ID = "agency_id" ;		/* ������� */
	public static final String ITEM_HAS_PURV = "has_purv" ;			/* �Ƿ��������� */
	public static final String ITEM_MAX_RESULT = "max_result" ;		/* ������������ */
	public static final String ITEM_LAST_MODI_USER = "last_modi_user" ;	/* ����޸��� */
	public static final String ITEM_LAST_MODI_DATE = "last_modi_date" ;	/* ����޸����� */
	
	/**
	 * ���캯��
	 */
	public VoDownloadPurv()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadPurv(DataBus value)
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

	/* ������� : String */
	public String getAgency_id()
	{
		return getValue( ITEM_AGENCY_ID );
	}

	public void setAgency_id( String agency_id1 )
	{
		setValue( ITEM_AGENCY_ID, agency_id1 );
	}

	/* �Ƿ��������� : String */
	public String getHas_purv()
	{
		return getValue( ITEM_HAS_PURV );
	}

	public void setHas_purv( String has_purv1 )
	{
		setValue( ITEM_HAS_PURV, has_purv1 );
	}

	/* ������������ : String */
	public String getMax_result()
	{
		return getValue( ITEM_MAX_RESULT );
	}

	public void setMax_result( String max_result1 )
	{
		setValue( ITEM_MAX_RESULT, max_result1 );
	}

	/* ����޸��� : String */
	public String getLast_modi_user()
	{
		return getValue( ITEM_LAST_MODI_USER );
	}

	public void setLast_modi_user( String last_modi_user1 )
	{
		setValue( ITEM_LAST_MODI_USER, last_modi_user1 );
	}

	/* ����޸����� : String */
	public String getLast_modi_date()
	{
		return getValue( ITEM_LAST_MODI_DATE );
	}

	public void setLast_modi_date( String last_modi_date1 )
	{
		setValue( ITEM_LAST_MODI_DATE, last_modi_date1 );
	}

}

