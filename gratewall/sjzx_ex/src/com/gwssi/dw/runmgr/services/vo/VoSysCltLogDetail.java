package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysCltLogDetail extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_CLT_LOG_DETAIL_ID = "sys_clt_log_detail_id";			/* 主键 */
	public static final String ITEM_SYS_CLT_LOG_ID = "sys_clt_log_id";			/* 采集日志外键 */
	public static final String ITEM_INF_NAME = "inf_name";			/* 接口名称 */
	public static final String ITEM_INF_DESC = "inf_desc";			/* 接口描述 */
	public static final String ITEM_CLT_NUM = "clt_num";			/* 采集数据量 */

	public VoSysCltLogDetail(DataBus value)
	{
		super(value);
	}

	public VoSysCltLogDetail()
	{
		super();
	}

	/* 主键 */
	public String getSys_clt_log_detail_id()
	{
		return getValue( ITEM_SYS_CLT_LOG_DETAIL_ID );
	}

	public void setSys_clt_log_detail_id( String sys_clt_log_detail_id1 )
	{
		setValue( ITEM_SYS_CLT_LOG_DETAIL_ID, sys_clt_log_detail_id1 );
	}

	/* 采集日志外键 */
	public String getSys_clt_log_id()
	{
		return getValue( ITEM_SYS_CLT_LOG_ID );
	}

	public void setSys_clt_log_id( String sys_clt_log_id1 )
	{
		setValue( ITEM_SYS_CLT_LOG_ID, sys_clt_log_id1 );
	}

	/* 接口名称 */
	public String getInf_name()
	{
		return getValue( ITEM_INF_NAME );
	}

	public void setInf_name( String inf_name1 )
	{
		setValue( ITEM_INF_NAME, inf_name1 );
	}

	/* 接口描述 */
	public String getInf_desc()
	{
		return getValue( ITEM_INF_DESC );
	}

	public void setInf_desc( String inf_desc1 )
	{
		setValue( ITEM_INF_DESC, inf_desc1 );
	}

	/* 采集数据量 */
	public String getClt_num()
	{
		return getValue( ITEM_CLT_NUM );
	}

	public void setClt_num( String clt_num1 )
	{
		setValue( ITEM_CLT_NUM, clt_num1 );
	}

}

