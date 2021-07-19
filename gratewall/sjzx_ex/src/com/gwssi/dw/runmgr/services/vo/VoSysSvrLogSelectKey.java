package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_log]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrLogSelectKey extends VoBase
{
	private static final long serialVersionUID = 200811281608000003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* ʹ�ù�������� */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* ��������� */
	public static final String ITEM_EXECUTE_START_TIME = "execute_start_time" ;	/* ִ�п�ʼʱ�� */
	public static final String ITEM_EXECUTE_END_TIME = "execute_end_time" ;	/* ִ�н���ʱ�� */
	public static final String ITEM_STATE = "state" ;				/* ִ�н�� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrLogSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrLogSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ʹ�ù�������� : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

	/* ��������� : String */
	public String getSys_svr_service_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_ID );
	}

	public void setSys_svr_service_id( String sys_svr_service_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_ID, sys_svr_service_id1 );
	}

	/* ִ�п�ʼʱ�� : String */
	public String getExecute_start_time()
	{
		return getValue( ITEM_EXECUTE_START_TIME );
	}

	public void setExecute_start_time( String execute_start_time1 )
	{
		setValue( ITEM_EXECUTE_START_TIME, execute_start_time1 );
	}

	/* ִ�н���ʱ�� : String */
	public String getExecute_end_time()
	{
		return getValue( ITEM_EXECUTE_END_TIME );
	}

	public void setExecute_end_time( String execute_end_time1 )
	{
		setValue( ITEM_EXECUTE_END_TIME, execute_end_time1 );
	}

	/* ִ�н�� : String */
	public String getState()
	{
		return getValue( ITEM_STATE );
	}

	public void setState( String state1 )
	{
		setValue( ITEM_STATE, state1 );
	}

}

