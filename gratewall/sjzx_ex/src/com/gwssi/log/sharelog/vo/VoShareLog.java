package com.gwssi.log.sharelog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoShareLog extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id";			/* 服务对象id */
	public static final String ITEM_LOG_ID = "log_id";			/* 主键 */
	public static final String ITEM_SERVICE_TYPE = "service_type";			/* service_type */
	public static final String ITEM_SERVICE_START_TIME = "service_start_time";			/* service_start_time */
	public static final String ITEM_SERVICE_END_TIME = "service_end_time";			/* service_end_time */
	public static final String ITEM_SERVICE_NAME = "service_name";			/* 服务名称 */
	public static final String ITEM_SERVICE_ID = "service_id";			/* 服务id */
	public static final String ITEM_ACCESS_IP = "access_ip";			/* 访问ip */
	public static final String ITEM_CONSUME_TIME = "consume_time";			/* 本次访问消耗时间 */
	public static final String ITEM_RECORD_START = "record_start";			/* 开始记录数 */
	public static final String ITEM_RECORD_END = "record_end";			/* 结束记录数 */
	public static final String ITEM_RECORD_AMOUNT = "record_amount";			/* 本次访问数据量 */
	public static final String ITEM_PATAMETER = "patameter";			/* 入口参数值 */
	public static final String ITEM_SERVICE_STATE = "service_state";			/* 代码表成功失败 */
	public static final String ITEM_RETURN_CODES = "return_codes";			/* 服务返回码 */
	public static final String ITEM_TARGETS_TYPE = "targets_type";			/* 对象类型 */
	public static final String ITEM_SERVICE_NO = "service_no";			/* 服务编号 */
	public static final String ITEM_LOG_TYPE = "log_type";			/* 日志类型 */
	public static final String ITEM_SERVICE_TARGETS_NAME = "service_targets_name";			/* 服务对象名称 */

	public VoShareLog(DataBus value)
	{
		super(value);
	}

	public VoShareLog()
	{
		super();
	}

	/* 服务对象id */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* 主键 */
	public String getLog_id()
	{
		return getValue( ITEM_LOG_ID );
	}

	public void setLog_id( String log_id1 )
	{
		setValue( ITEM_LOG_ID, log_id1 );
	}

	/* service_type */
	public String getService_type()
	{
		return getValue( ITEM_SERVICE_TYPE );
	}

	public void setService_type( String service_type1 )
	{
		setValue( ITEM_SERVICE_TYPE, service_type1 );
	}

	/* service_start_time */
	public String getService_start_time()
	{
		return getValue( ITEM_SERVICE_START_TIME );
	}

	public void setService_start_time( String service_start_time1 )
	{
		setValue( ITEM_SERVICE_START_TIME, service_start_time1 );
	}

	/* service_end_time */
	public String getService_end_time()
	{
		return getValue( ITEM_SERVICE_END_TIME );
	}

	public void setService_end_time( String service_end_time1 )
	{
		setValue( ITEM_SERVICE_END_TIME, service_end_time1 );
	}

	/* 服务名称 */
	public String getService_name()
	{
		return getValue( ITEM_SERVICE_NAME );
	}

	public void setService_name( String service_name1 )
	{
		setValue( ITEM_SERVICE_NAME, service_name1 );
	}

	/* 服务id */
	public String getService_id()
	{
		return getValue( ITEM_SERVICE_ID );
	}

	public void setService_id( String service_id1 )
	{
		setValue( ITEM_SERVICE_ID, service_id1 );
	}

	/* 访问ip */
	public String getAccess_ip()
	{
		return getValue( ITEM_ACCESS_IP );
	}

	public void setAccess_ip( String access_ip1 )
	{
		setValue( ITEM_ACCESS_IP, access_ip1 );
	}

	/* 本次访问消耗时间 */
	public String getConsume_time()
	{
		return getValue( ITEM_CONSUME_TIME );
	}

	public void setConsume_time( String consume_time1 )
	{
		setValue( ITEM_CONSUME_TIME, consume_time1 );
	}

	/* 开始记录数 */
	public String getRecord_start()
	{
		return getValue( ITEM_RECORD_START );
	}

	public void setRecord_start( String record_start1 )
	{
		setValue( ITEM_RECORD_START, record_start1 );
	}

	/* 结束记录数 */
	public String getRecord_end()
	{
		return getValue( ITEM_RECORD_END );
	}

	public void setRecord_end( String record_end1 )
	{
		setValue( ITEM_RECORD_END, record_end1 );
	}

	/* 本次访问数据量 */
	public String getRecord_amount()
	{
		return getValue( ITEM_RECORD_AMOUNT );
	}

	public void setRecord_amount( String record_amount1 )
	{
		setValue( ITEM_RECORD_AMOUNT, record_amount1 );
	}

	/* 入口参数值 */
	public String getPatameter()
	{
		return getValue( ITEM_PATAMETER );
	}

	public void setPatameter( String patameter1 )
	{
		setValue( ITEM_PATAMETER, patameter1 );
	}

	/* 代码表成功失败 */
	public String getService_state()
	{
		return getValue( ITEM_SERVICE_STATE );
	}

	public void setService_state( String service_state1 )
	{
		setValue( ITEM_SERVICE_STATE, service_state1 );
	}

	/* 服务返回码 */
	public String getReturn_codes()
	{
		return getValue( ITEM_RETURN_CODES );
	}

	public void setReturn_codes( String return_codes1 )
	{
		setValue( ITEM_RETURN_CODES, return_codes1 );
	}
	/* 对象类型 */
	public String getTargets_type()
	{
		return getValue( ITEM_TARGETS_TYPE );
	}

	public void setTargets_type( String targets_type1 )
	{
		setValue( ITEM_TARGETS_TYPE, targets_type1 );
	}
	/* 日志类型 */
	public String getLog_type()
	{
		return getValue( ITEM_LOG_TYPE );
	}

	public void setLog_type( String log_type1 )
	{
		setValue( ITEM_LOG_TYPE, log_type1 );
	}
	/* 服务对象名称 */
	public String getService_targets_name()
	{
		return getValue( ITEM_SERVICE_TARGETS_NAME );
	}

	public void setService_targets_name( String service_targets_name1 )
	{
		setValue( ITEM_SERVICE_TARGETS_NAME, service_targets_name1 );
	}
	
	/* 服务编号 */
	public String getService_no()
	{
		return getValue( ITEM_SERVICE_NO );
	}

	public void setService_no( String service_no1 )
	{
		setValue( ITEM_SERVICE_NO, service_no1 );
	}
	
	
	
	
	
	
	
	
}

