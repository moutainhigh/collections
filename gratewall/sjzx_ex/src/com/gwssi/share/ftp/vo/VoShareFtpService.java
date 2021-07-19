package com.gwssi.share.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_ftp_service]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareFtpService extends VoBase
{
	private static final long serialVersionUID = 201308211659250006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_FTP_SERVICE_ID = "ftp_service_id" ;	/* FTP����ID */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* ����ID */
	public static final String ITEM_DATASOURCE_ID = "datasource_id" ;	/* ����ԴID */
	public static final String ITEM_SRV_SCHEDULING_ID = "srv_scheduling_id" ;	/* �������ID */
	public static final String ITEM_FILE_NAME = "file_name" ;		/* �ļ����� */
	public static final String ITEM_FILE_TYPE = "file_type" ;		/* �ļ����� */
	
	/**
	 * ���캯��
	 */
	public VoShareFtpService()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareFtpService(DataBus value)
	{
		super(value);
	}
	
	/* FTP����ID : String */
	public String getFtp_service_id()
	{
		return getValue( ITEM_FTP_SERVICE_ID );
	}

	public void setFtp_service_id( String ftp_service_id1 )
	{
		setValue( ITEM_FTP_SERVICE_ID, ftp_service_id1 );
	}

	/* ����ID : String */
	public String getService_id()
	{
		return getValue( ITEM_SERVICE_ID );
	}

	public void setService_id( String service_id1 )
	{
		setValue( ITEM_SERVICE_ID, service_id1 );
	}

	/* ����ԴID : String */
	public String getDatasource_id()
	{
		return getValue( ITEM_DATASOURCE_ID );
	}

	public void setDatasource_id( String datasource_id1 )
	{
		setValue( ITEM_DATASOURCE_ID, datasource_id1 );
	}

	/* �������ID : String */
	public String getSrv_scheduling_id()
	{
		return getValue( ITEM_SRV_SCHEDULING_ID );
	}

	public void setSrv_scheduling_id( String srv_scheduling_id1 )
	{
		setValue( ITEM_SRV_SCHEDULING_ID, srv_scheduling_id1 );
	}

	/* �ļ����� : String */
	public String getFile_name()
	{
		return getValue( ITEM_FILE_NAME );
	}

	public void setFile_name( String file_name1 )
	{
		setValue( ITEM_FILE_NAME, file_name1 );
	}

	/* �ļ����� : String */
	public String getFile_type()
	{
		return getValue( ITEM_FILE_TYPE );
	}

	public void setFile_type( String file_type1 )
	{
		setValue( ITEM_FILE_TYPE, file_type1 );
	}

}

