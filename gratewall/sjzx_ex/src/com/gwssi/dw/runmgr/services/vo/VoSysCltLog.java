package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysCltLog extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_CLT_LOG_ID = "sys_clt_log_id";			/* 主键 */
	public static final String ITEM_SYS_CLT_USER_ID = "sys_clt_user_id";			/* 采集策略外键 */
	public static final String ITEM_CLT_STARTDATE = "clt_startdate";			/* 采集数据开始日期 */
	public static final String ITEM_CLT_ENDDATE = "clt_enddate";			/* 采集数据结束日期 */
	public static final String ITEM_EXC_STARTTIME = "exc_starttime";			/* 采集执行开始时间 */
	public static final String ITEM_EXC_ENDTIME = "exc_endtime";			/* 采集执行结束时间 */
	public static final String ITEM_STATE = "state";			/* 采集状态（0表示失败1表示成功） */
	public static final String ITEM_LOGDESC = "logdesc";			/* 日志描述 */

	public VoSysCltLog(DataBus value)
	{
		super(value);
	}

	public VoSysCltLog()
	{
		super();
	}

	/* 主键 */
	public String getSys_clt_log_id()
	{
		return getValue( ITEM_SYS_CLT_LOG_ID );
	}

	public void setSys_clt_log_id( String sys_clt_log_id1 )
	{
		setValue( ITEM_SYS_CLT_LOG_ID, sys_clt_log_id1 );
	}

	/* 采集策略外键 */
	public String getSys_clt_user_id()
	{
		return getValue( ITEM_SYS_CLT_USER_ID );
	}

	public void setSys_clt_user_id( String sys_clt_user_id1 )
	{
		setValue( ITEM_SYS_CLT_USER_ID, sys_clt_user_id1 );
	}

	/* 采集数据开始日期 */
	public String getClt_startdate()
	{
		return getValue( ITEM_CLT_STARTDATE );
	}

	public void setClt_startdate( String clt_startdate1 )
	{
		setValue( ITEM_CLT_STARTDATE, clt_startdate1 );
	}

	/* 采集数据结束日期 */
	public String getClt_enddate()
	{
		return getValue( ITEM_CLT_ENDDATE );
	}

	public void setClt_enddate( String clt_enddate1 )
	{
		setValue( ITEM_CLT_ENDDATE, clt_enddate1 );
	}

	/* 采集执行开始时间 */
	public String getExc_starttime()
	{
		return getValue( ITEM_EXC_STARTTIME );
	}

	public void setExc_starttime( String exc_starttime1 )
	{
		setValue( ITEM_EXC_STARTTIME, exc_starttime1 );
	}

	/* 采集执行结束时间 */
	public String getExc_endtime()
	{
		return getValue( ITEM_EXC_ENDTIME );
	}

	public void setExc_endtime( String exc_endtime1 )
	{
		setValue( ITEM_EXC_ENDTIME, exc_endtime1 );
	}

	/* 采集状态（0表示失败1表示成功） */
	public String getState()
	{
		return getValue( ITEM_STATE );
	}

	public void setState( String state1 )
	{
		setValue( ITEM_STATE, state1 );
	}

	/* 日志描述 */
	public String getLogdesc()
	{
		return getValue( ITEM_LOGDESC );
	}

	public void setLogdesc( String logdesc1 )
	{
		setValue( ITEM_LOGDESC, logdesc1 );
	}

}

