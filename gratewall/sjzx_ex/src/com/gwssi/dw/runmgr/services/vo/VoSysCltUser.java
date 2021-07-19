package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysCltUser extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_CLT_USER_ID = "sys_clt_user_id";			/* 采集策略表主键 */
	public static final String ITEM_JOBNAME = "jobname";			/* 采集调度名称 */
	public static final String ITEM_GROUPNAME = "groupname";			/* 采集调度组名称 */
	public static final String ITEM_CLASSNAME = "classname";			/* 采集调度类路径 */
	public static final String ITEM_NAME = "name";			/* 采集对象名称 */
	public static final String ITEM_STATE = "state";			/* 采集状态(启用或停用) */
	public static final String ITEM_OLDSTATE = "oldstate";			/* 采集状态(启用或停用) */
	public static final String ITEM_STRATEGY = "strategy";			/* 采集策略(每天或每周) */
	public static final String ITEM_HOURS = "hours";			/* 执行时间小时 */
	public static final String ITEM_MINUTES = "minutes";			/* 执行时间分钟 */
	public static final String ITEM_SECONDS = "seconds";			/* 执行时间秒 */
	public static final String ITEM_STRATEGYDESC = "strategydesc";			/* 采集策略详细 */
	public static final String ITEM_STRATEGYTIME = "strategytime";			/* 采集策略详细列表显示 */
	public static final String ITEM_STARTDATE = "startdate";			/* 开始日期 */
	public static final String ITEM_ENDDATE = "enddate";			/* 结束日期 */
	public static final String ITEM_USERTYPE = "user_type";			/* 用户类型 */

	public VoSysCltUser(DataBus value)
	{
		super(value);
	}

	public VoSysCltUser()
	{
		super();
	}

	/* 采集策略表主键 */
	public String getSys_clt_user_id()
	{
		return getValue( ITEM_SYS_CLT_USER_ID );
	}

	public void setSys_clt_user_id( String sys_clt_user_id1 )
	{
		setValue( ITEM_SYS_CLT_USER_ID, sys_clt_user_id1 );
	}

	/* 采集调度名称 */
	public String getJobname()
	{
		return getValue( ITEM_JOBNAME );
	}

	public void setJobname( String jobname1 )
	{
		setValue( ITEM_JOBNAME, jobname1 );
	}

	/* 采集调度组名称 */
	public String getGroupname()
	{
		return getValue( ITEM_GROUPNAME );
	}

	public void setGroupname( String groupname1 )
	{
		setValue( ITEM_GROUPNAME, groupname1 );
	}

	/* 采集调度类路径 */
	public String getClassname()
	{
		return getValue( ITEM_CLASSNAME );
	}

	public void setClassname( String classname1 )
	{
		setValue( ITEM_CLASSNAME, classname1 );
	}

	/* 采集对象名称 */
	public String getName()
	{
		return getValue( ITEM_NAME );
	}

	public void setName( String name1 )
	{
		setValue( ITEM_NAME, name1 );
	}

	/* 采集状态(启用或停用) */
	public String getState()
	{
		return getValue( ITEM_STATE );
	}

	public void setState( String state1 )
	{
		setValue( ITEM_STATE, state1 );
	}

	/* 采集策略(每天或每周) */
	public String getStrategy()
	{
		return getValue( ITEM_STRATEGY );
	}

	public void setStrategy( String strategy1 )
	{
		setValue( ITEM_STRATEGY, strategy1 );
	}

	/* 执行时间小时 */
	public String getHours()
	{
		return getValue( ITEM_HOURS );
	}

	public void setHours( String hours1 )
	{
		setValue( ITEM_HOURS, hours1 );
	}

	/* 执行时间分钟 */
	public String getMinutes()
	{
		return getValue( ITEM_MINUTES );
	}

	public void setMinutes( String minutes1 )
	{
		setValue( ITEM_MINUTES, minutes1 );
	}

	/* 执行时间秒 */
	public String getSeconds()
	{
		return getValue( ITEM_SECONDS );
	}

	public void setSeconds( String seconds1 )
	{
		setValue( ITEM_SECONDS, seconds1 );
	}

	/* 采集策略详细 */
	public String getStrategydesc()
	{
		return getValue( ITEM_STRATEGYDESC );
	}

	public void setStrategydesc( String strategydesc1 )
	{
		setValue( ITEM_STRATEGYDESC, strategydesc1 );
	}

	/* 采集策略详细 */
	public String getStrategytime()
	{
		return getValue( ITEM_STRATEGYTIME );
	}

	public void setStrategytime( String strategytime1 )
	{
		setValue( ITEM_STRATEGYTIME, strategytime1 );
	}
	
	/* 开始日期 */
	public String getStartdate()
	{
		return getValue( ITEM_STARTDATE );
	}

	public void setStartdate( String startdate1 )
	{
		setValue( ITEM_STARTDATE, startdate1 );
	}

	/* 结束日期 */
	public String getEnddate()
	{
		return getValue( ITEM_ENDDATE );
	}

	public void setEnddate( String enddate1 )
	{
		setValue( ITEM_ENDDATE, enddate1 );
	}

	/* 用户类型 */
	public String getUserType()
	{
		return getValue( ITEM_USERTYPE );
	}

	public void setUserType( String user_type )
	{
		setValue( ITEM_USERTYPE, user_type );
	}
}

