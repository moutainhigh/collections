package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_log]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrLogPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200811281608000004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_LOG_ID = "sys_svr_log_id" ;	/* ���������־��� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrLogPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrLogPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ���������־��� : String */
	public String getSys_svr_log_id()
	{
		return getValue( ITEM_SYS_SVR_LOG_ID );
	}

	public void setSys_svr_log_id( String sys_svr_log_id1 )
	{
		setValue( ITEM_SYS_SVR_LOG_ID, sys_svr_log_id1 );
	}

}

