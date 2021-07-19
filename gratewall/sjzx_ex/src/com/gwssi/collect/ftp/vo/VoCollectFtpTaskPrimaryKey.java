package com.gwssi.collect.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_ftp_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectFtpTaskPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304281352240004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_FTP_TASK_ID = "ftp_task_id" ;	/* FTP����ID */
	
	/**
	 * ���캯��
	 */
	public VoCollectFtpTaskPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectFtpTaskPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* FTP����ID : String */
	public String getFtp_task_id()
	{
		return getValue( ITEM_FTP_TASK_ID );
	}

	public void setFtp_task_id( String ftp_task_id1 )
	{
		setValue( ITEM_FTP_TASK_ID, ftp_task_id1 );
	}

}

