package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_limit]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrLimit extends VoBase
{
	private static final long serialVersionUID = 201207121643140002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_LIMIT_ID = "sys_svr_limit_id" ;	/* 用户服务限制主键 */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* 用户ID */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* 服务ID */
	public static final String ITEM_IS_LIMIT_WEEK = "is_limit_week" ;	/* 是否限制本日期 */
	public static final String ITEM_IS_LIMIT_TIME = "is_limit_time" ;	/* 是否限制时间 */
	public static final String ITEM_IS_LIMIT_NUMBER = "is_limit_number" ;	/* 是否限制次数 */
	public static final String ITEM_IS_LIMIT_TOTAL = "is_limit_total" ;	/* 是否限制总条数 */
	public static final String ITEM_LIMIT_WEEK = "limit_week" ;		/* 限制星期 */
	public static final String ITEM_LIMIT_TIME = "limit_time" ;		/* 限制时间 */
	public static final String ITEM_LIMIT_START_TIME = "limit_start_time" ;	/* 起止限制时间 */
	public static final String ITEM_LIMIT_END_TIME = "limit_end_time" ;	/* 结束限制时间 */
	public static final String ITEM_LIMIT_NUMBER = "limit_number" ;	/* 限制次数 */
	public static final String ITEM_LIMIT_TOTAL = "limit_total" ;	/* 限制总条数 */
	public static final String ITEM_LIMIT_DESP = "limit_desp" ;		/* 限制描述 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrLimit()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrLimit(DataBus value)
	{
		super(value);
	}
	
	/* 用户服务限制主键 : String */
	public String getSys_svr_limit_id()
	{
		return getValue( ITEM_SYS_SVR_LIMIT_ID );
	}

	public void setSys_svr_limit_id( String sys_svr_limit_id1 )
	{
		setValue( ITEM_SYS_SVR_LIMIT_ID, sys_svr_limit_id1 );
	}

	/* 用户ID : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

	/* 服务ID : String */
	public String getSys_svr_service_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_ID );
	}

	public void setSys_svr_service_id( String sys_svr_service_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_ID, sys_svr_service_id1 );
	}

	/* 是否限制本日期 : String */
	public String getIs_limit_week()
	{
		return getValue( ITEM_IS_LIMIT_WEEK );
	}

	public void setIs_limit_week( String is_limit_week1 )
	{
		setValue( ITEM_IS_LIMIT_WEEK, is_limit_week1 );
	}

	/* 是否限制时间 : String */
	public String getIs_limit_time()
	{
		return getValue( ITEM_IS_LIMIT_TIME );
	}

	public void setIs_limit_time( String is_limit_time1 )
	{
		setValue( ITEM_IS_LIMIT_TIME, is_limit_time1 );
	}

	/* 是否限制次数 : String */
	public String getIs_limit_number()
	{
		return getValue( ITEM_IS_LIMIT_NUMBER );
	}

	public void setIs_limit_number( String is_limit_number1 )
	{
		setValue( ITEM_IS_LIMIT_NUMBER, is_limit_number1 );
	}

	/* 是否限制总条数 : String */
	public String getIs_limit_total()
	{
		return getValue( ITEM_IS_LIMIT_TOTAL );
	}

	public void setIs_limit_total( String is_limit_total1 )
	{
		setValue( ITEM_IS_LIMIT_TOTAL, is_limit_total1 );
	}

	/* 限制星期 : String */
	public String getLimit_week()
	{
		return getValue( ITEM_LIMIT_WEEK );
	}

	public void setLimit_week( String limit_week1 )
	{
		setValue( ITEM_LIMIT_WEEK, limit_week1 );
	}

	/* 限制时间 : String */
	public String getLimit_time()
	{
		return getValue( ITEM_LIMIT_TIME );
	}

	public void setLimit_time( String limit_time1 )
	{
		setValue( ITEM_LIMIT_TIME, limit_time1 );
	}

	/* 起止限制时间 : String */
	public String getLimit_start_time()
	{
		return getValue( ITEM_LIMIT_START_TIME );
	}

	public void setLimit_start_time( String limit_start_time1 )
	{
		setValue( ITEM_LIMIT_START_TIME, limit_start_time1 );
	}

	/* 结束限制时间 : String */
	public String getLimit_end_time()
	{
		return getValue( ITEM_LIMIT_END_TIME );
	}

	public void setLimit_end_time( String limit_end_time1 )
	{
		setValue( ITEM_LIMIT_END_TIME, limit_end_time1 );
	}

	/* 限制次数 : String */
	public String getLimit_number()
	{
		return getValue( ITEM_LIMIT_NUMBER );
	}

	public void setLimit_number( String limit_number1 )
	{
		setValue( ITEM_LIMIT_NUMBER, limit_number1 );
	}

	/* 限制总条数 : String */
	public String getLimit_total()
	{
		return getValue( ITEM_LIMIT_TOTAL );
	}

	public void setLimit_total( String limit_total1 )
	{
		setValue( ITEM_LIMIT_TOTAL, limit_total1 );
	}

	/* 限制描述 : String */
	public String getLimit_desp()
	{
		return getValue( ITEM_LIMIT_DESP );
	}

	public void setLimit_desp( String limit_desp1 )
	{
		setValue( ITEM_LIMIT_DESP, limit_desp1 );
	}

}

