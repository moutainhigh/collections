package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysCltLog extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_CLT_LOG_ID = "sys_clt_log_id";			/* ���� */
	public static final String ITEM_SYS_CLT_USER_ID = "sys_clt_user_id";			/* �ɼ�������� */
	public static final String ITEM_CLT_STARTDATE = "clt_startdate";			/* �ɼ����ݿ�ʼ���� */
	public static final String ITEM_CLT_ENDDATE = "clt_enddate";			/* �ɼ����ݽ������� */
	public static final String ITEM_EXC_STARTTIME = "exc_starttime";			/* �ɼ�ִ�п�ʼʱ�� */
	public static final String ITEM_EXC_ENDTIME = "exc_endtime";			/* �ɼ�ִ�н���ʱ�� */
	public static final String ITEM_STATE = "state";			/* �ɼ�״̬��0��ʾʧ��1��ʾ�ɹ��� */
	public static final String ITEM_LOGDESC = "logdesc";			/* ��־���� */

	public VoSysCltLog(DataBus value)
	{
		super(value);
	}

	public VoSysCltLog()
	{
		super();
	}

	/* ���� */
	public String getSys_clt_log_id()
	{
		return getValue( ITEM_SYS_CLT_LOG_ID );
	}

	public void setSys_clt_log_id( String sys_clt_log_id1 )
	{
		setValue( ITEM_SYS_CLT_LOG_ID, sys_clt_log_id1 );
	}

	/* �ɼ�������� */
	public String getSys_clt_user_id()
	{
		return getValue( ITEM_SYS_CLT_USER_ID );
	}

	public void setSys_clt_user_id( String sys_clt_user_id1 )
	{
		setValue( ITEM_SYS_CLT_USER_ID, sys_clt_user_id1 );
	}

	/* �ɼ����ݿ�ʼ���� */
	public String getClt_startdate()
	{
		return getValue( ITEM_CLT_STARTDATE );
	}

	public void setClt_startdate( String clt_startdate1 )
	{
		setValue( ITEM_CLT_STARTDATE, clt_startdate1 );
	}

	/* �ɼ����ݽ������� */
	public String getClt_enddate()
	{
		return getValue( ITEM_CLT_ENDDATE );
	}

	public void setClt_enddate( String clt_enddate1 )
	{
		setValue( ITEM_CLT_ENDDATE, clt_enddate1 );
	}

	/* �ɼ�ִ�п�ʼʱ�� */
	public String getExc_starttime()
	{
		return getValue( ITEM_EXC_STARTTIME );
	}

	public void setExc_starttime( String exc_starttime1 )
	{
		setValue( ITEM_EXC_STARTTIME, exc_starttime1 );
	}

	/* �ɼ�ִ�н���ʱ�� */
	public String getExc_endtime()
	{
		return getValue( ITEM_EXC_ENDTIME );
	}

	public void setExc_endtime( String exc_endtime1 )
	{
		setValue( ITEM_EXC_ENDTIME, exc_endtime1 );
	}

	/* �ɼ�״̬��0��ʾʧ��1��ʾ�ɹ��� */
	public String getState()
	{
		return getValue( ITEM_STATE );
	}

	public void setState( String state1 )
	{
		setValue( ITEM_STATE, state1 );
	}

	/* ��־���� */
	public String getLogdesc()
	{
		return getValue( ITEM_LOGDESC );
	}

	public void setLogdesc( String logdesc1 )
	{
		setValue( ITEM_LOGDESC, logdesc1 );
	}

}

