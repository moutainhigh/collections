package com.gwssi.log.sharelog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_log]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareLogPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304031121530004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_LOG_ID = "log_id" ;				/* ���� */
	
	/**
	 * ���캯��
	 */
	public VoShareLogPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareLogPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ���� : String */
	public String getLog_id()
	{
		return getValue( ITEM_LOG_ID );
	}

	public void setLog_id( String log_id1 )
	{
		setValue( ITEM_LOG_ID, log_id1 );
	}

}

