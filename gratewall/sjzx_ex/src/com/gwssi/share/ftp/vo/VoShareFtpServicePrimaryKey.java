package com.gwssi.share.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_ftp_service]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareFtpServicePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201308211659250008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_FTP_SERVICE_ID = "ftp_service_id" ;	/* FTP����ID */
	
	/**
	 * ���캯��
	 */
	public VoShareFtpServicePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareFtpServicePrimaryKey(DataBus value)
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

}

