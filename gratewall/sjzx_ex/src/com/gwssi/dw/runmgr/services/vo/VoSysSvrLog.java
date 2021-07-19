package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_log]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrLog extends VoBase
{
	private static final long serialVersionUID = 200811281607590002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_LOG_ID = "sys_svr_log_id" ;	/* ���������־��� */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* ʹ�ù�������� */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* ��������� */
	public static final String ITEM_SYS_SVR_USER_NAME = "sys_svr_user_name" ;	/* ʹ�ù��������� */
	public static final String ITEM_SYS_SVR_SERVICE_NAME = "sys_svr_service_name" ;	/* ����������� */
	public static final String ITEM_EXECUTE_START_TIME = "execute_start_time" ;	/* ִ�п�ʼʱ�� */
	public static final String ITEM_EXECUTE_END_TIME = "execute_end_time" ;	/* ִ�н���ʱ�� */
	public static final String ITEM_STATE = "state" ;				/* ִ�н�� */
	public static final String ITEM_RECORDS_MOUNT = "records_mount" ;	/* ���ؼ�¼�� */
	public static final String ITEM_ERROR_MSG = "error_msg" ;		/* �������� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrLog()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrLog(DataBus value)
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

	/* ʹ�ù��������� : String */
	public String getSys_svr_user_name()
	{
		return getValue( ITEM_SYS_SVR_USER_NAME );
	}

	public void setSys_svr_user_name( String sys_svr_user_name1 )
	{
		setValue( ITEM_SYS_SVR_USER_NAME, sys_svr_user_name1 );
	}

	/* ����������� : String */
	public String getSys_svr_service_name()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_NAME );
	}

	public void setSys_svr_service_name( String sys_svr_service_name1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_NAME, sys_svr_service_name1 );
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

	/* ���ؼ�¼�� : String */
	public String getRecords_mount()
	{
		return getValue( ITEM_RECORDS_MOUNT );
	}

	public void setRecords_mount( String records_mount1 )
	{
		setValue( ITEM_RECORDS_MOUNT, records_mount1 );
	}

	/* �������� : String */
	public String getError_msg()
	{
		return getValue( ITEM_ERROR_MSG );
	}

	public void setError_msg( String error_msg1 )
	{
		setValue( ITEM_ERROR_MSG, error_msg1 );
	}

}

