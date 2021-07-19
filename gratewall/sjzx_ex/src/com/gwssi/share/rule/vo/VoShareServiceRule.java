package com.gwssi.share.rule.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_service_rule]的数据对象类
 * @author Administrator
 *
 */
public class VoShareServiceRule extends VoBase
{
	private static final long serialVersionUID = 201304081757160002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_RULE_ID = "rule_id" ;			/* 服务访问规则ID */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* 服务ID */
	public static final String ITEM_WEEK = "week" ;					/* 是星期几 */
	public static final String ITEM_START_TIME = "start_time" ;		/* 服务当天开始时间 */
	public static final String ITEM_END_TIME = "end_time" ;			/* 服务当天结束时间 */
	public static final String ITEM_TIMES_DAY = "times_day" ;		/* 服务当天可访问次数 */
	public static final String ITEM_COUNT_DAT = "count_dat" ;		/* 服务当天一次可访问记录数目 */
	public static final String ITEM_TOTAL_COUNT_DAY = "total_count_day" ;	/* 服务当天可访问总记录数目 */
	
	/**
	 * 构造函数
	 */
	public VoShareServiceRule()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareServiceRule(DataBus value)
	{
		super(value);
	}
	
	/* 服务访问规则ID : String */
	public String getRule_id()
	{
		return getValue( ITEM_RULE_ID );
	}

	public void setRule_id( String rule_id1 )
	{
		setValue( ITEM_RULE_ID, rule_id1 );
	}

	/* 服务ID : String */
	public String getService_id()
	{
		return getValue( ITEM_SERVICE_ID );
	}

	public void setService_id( String service_id1 )
	{
		setValue( ITEM_SERVICE_ID, service_id1 );
	}

	/* 是星期几 : String */
	public String getWeek()
	{
		return getValue( ITEM_WEEK );
	}

	public void setWeek( String week1 )
	{
		setValue( ITEM_WEEK, week1 );
	}

	/* 服务当天开始时间 : String */
	public String getStart_time()
	{
		return getValue( ITEM_START_TIME );
	}

	public void setStart_time( String start_time1 )
	{
		setValue( ITEM_START_TIME, start_time1 );
	}

	/* 服务当天结束时间 : String */
	public String getEnd_time()
	{
		return getValue( ITEM_END_TIME );
	}

	public void setEnd_time( String end_time1 )
	{
		setValue( ITEM_END_TIME, end_time1 );
	}

	/* 服务当天可访问次数 : String */
	public String getTimes_day()
	{
		return getValue( ITEM_TIMES_DAY );
	}

	public void setTimes_day( String times_day1 )
	{
		setValue( ITEM_TIMES_DAY, times_day1 );
	}

	/* 服务当天一次可访问记录数目 : String */
	public String getCount_dat()
	{
		return getValue( ITEM_COUNT_DAT );
	}

	public void setCount_dat( String count_dat1 )
	{
		setValue( ITEM_COUNT_DAT, count_dat1 );
	}

	/* 服务当天可访问总记录数目 : String */
	public String getTotal_count_day()
	{
		return getValue( ITEM_TOTAL_COUNT_DAY );
	}

	public void setTotal_count_day( String total_count_day1 )
	{
		setValue( ITEM_TOTAL_COUNT_DAY, total_count_day1 );
	}

}

