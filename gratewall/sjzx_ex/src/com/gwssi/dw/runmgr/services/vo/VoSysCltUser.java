package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysCltUser extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_CLT_USER_ID = "sys_clt_user_id";			/* �ɼ����Ա����� */
	public static final String ITEM_JOBNAME = "jobname";			/* �ɼ��������� */
	public static final String ITEM_GROUPNAME = "groupname";			/* �ɼ����������� */
	public static final String ITEM_CLASSNAME = "classname";			/* �ɼ�������·�� */
	public static final String ITEM_NAME = "name";			/* �ɼ��������� */
	public static final String ITEM_STATE = "state";			/* �ɼ�״̬(���û�ͣ��) */
	public static final String ITEM_OLDSTATE = "oldstate";			/* �ɼ�״̬(���û�ͣ��) */
	public static final String ITEM_STRATEGY = "strategy";			/* �ɼ�����(ÿ���ÿ��) */
	public static final String ITEM_HOURS = "hours";			/* ִ��ʱ��Сʱ */
	public static final String ITEM_MINUTES = "minutes";			/* ִ��ʱ����� */
	public static final String ITEM_SECONDS = "seconds";			/* ִ��ʱ���� */
	public static final String ITEM_STRATEGYDESC = "strategydesc";			/* �ɼ�������ϸ */
	public static final String ITEM_STRATEGYTIME = "strategytime";			/* �ɼ�������ϸ�б���ʾ */
	public static final String ITEM_STARTDATE = "startdate";			/* ��ʼ���� */
	public static final String ITEM_ENDDATE = "enddate";			/* �������� */
	public static final String ITEM_USERTYPE = "user_type";			/* �û����� */

	public VoSysCltUser(DataBus value)
	{
		super(value);
	}

	public VoSysCltUser()
	{
		super();
	}

	/* �ɼ����Ա����� */
	public String getSys_clt_user_id()
	{
		return getValue( ITEM_SYS_CLT_USER_ID );
	}

	public void setSys_clt_user_id( String sys_clt_user_id1 )
	{
		setValue( ITEM_SYS_CLT_USER_ID, sys_clt_user_id1 );
	}

	/* �ɼ��������� */
	public String getJobname()
	{
		return getValue( ITEM_JOBNAME );
	}

	public void setJobname( String jobname1 )
	{
		setValue( ITEM_JOBNAME, jobname1 );
	}

	/* �ɼ����������� */
	public String getGroupname()
	{
		return getValue( ITEM_GROUPNAME );
	}

	public void setGroupname( String groupname1 )
	{
		setValue( ITEM_GROUPNAME, groupname1 );
	}

	/* �ɼ�������·�� */
	public String getClassname()
	{
		return getValue( ITEM_CLASSNAME );
	}

	public void setClassname( String classname1 )
	{
		setValue( ITEM_CLASSNAME, classname1 );
	}

	/* �ɼ��������� */
	public String getName()
	{
		return getValue( ITEM_NAME );
	}

	public void setName( String name1 )
	{
		setValue( ITEM_NAME, name1 );
	}

	/* �ɼ�״̬(���û�ͣ��) */
	public String getState()
	{
		return getValue( ITEM_STATE );
	}

	public void setState( String state1 )
	{
		setValue( ITEM_STATE, state1 );
	}

	/* �ɼ�����(ÿ���ÿ��) */
	public String getStrategy()
	{
		return getValue( ITEM_STRATEGY );
	}

	public void setStrategy( String strategy1 )
	{
		setValue( ITEM_STRATEGY, strategy1 );
	}

	/* ִ��ʱ��Сʱ */
	public String getHours()
	{
		return getValue( ITEM_HOURS );
	}

	public void setHours( String hours1 )
	{
		setValue( ITEM_HOURS, hours1 );
	}

	/* ִ��ʱ����� */
	public String getMinutes()
	{
		return getValue( ITEM_MINUTES );
	}

	public void setMinutes( String minutes1 )
	{
		setValue( ITEM_MINUTES, minutes1 );
	}

	/* ִ��ʱ���� */
	public String getSeconds()
	{
		return getValue( ITEM_SECONDS );
	}

	public void setSeconds( String seconds1 )
	{
		setValue( ITEM_SECONDS, seconds1 );
	}

	/* �ɼ�������ϸ */
	public String getStrategydesc()
	{
		return getValue( ITEM_STRATEGYDESC );
	}

	public void setStrategydesc( String strategydesc1 )
	{
		setValue( ITEM_STRATEGYDESC, strategydesc1 );
	}

	/* �ɼ�������ϸ */
	public String getStrategytime()
	{
		return getValue( ITEM_STRATEGYTIME );
	}

	public void setStrategytime( String strategytime1 )
	{
		setValue( ITEM_STRATEGYTIME, strategytime1 );
	}
	
	/* ��ʼ���� */
	public String getStartdate()
	{
		return getValue( ITEM_STARTDATE );
	}

	public void setStartdate( String startdate1 )
	{
		setValue( ITEM_STARTDATE, startdate1 );
	}

	/* �������� */
	public String getEnddate()
	{
		return getValue( ITEM_ENDDATE );
	}

	public void setEnddate( String enddate1 )
	{
		setValue( ITEM_ENDDATE, enddate1 );
	}

	/* �û����� */
	public String getUserType()
	{
		return getValue( ITEM_USERTYPE );
	}

	public void setUserType( String user_type )
	{
		setValue( ITEM_USERTYPE, user_type );
	}
}

