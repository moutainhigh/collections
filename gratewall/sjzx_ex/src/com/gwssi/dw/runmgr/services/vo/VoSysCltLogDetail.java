package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysCltLogDetail extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_CLT_LOG_DETAIL_ID = "sys_clt_log_detail_id";			/* ���� */
	public static final String ITEM_SYS_CLT_LOG_ID = "sys_clt_log_id";			/* �ɼ���־��� */
	public static final String ITEM_INF_NAME = "inf_name";			/* �ӿ����� */
	public static final String ITEM_INF_DESC = "inf_desc";			/* �ӿ����� */
	public static final String ITEM_CLT_NUM = "clt_num";			/* �ɼ������� */

	public VoSysCltLogDetail(DataBus value)
	{
		super(value);
	}

	public VoSysCltLogDetail()
	{
		super();
	}

	/* ���� */
	public String getSys_clt_log_detail_id()
	{
		return getValue( ITEM_SYS_CLT_LOG_DETAIL_ID );
	}

	public void setSys_clt_log_detail_id( String sys_clt_log_detail_id1 )
	{
		setValue( ITEM_SYS_CLT_LOG_DETAIL_ID, sys_clt_log_detail_id1 );
	}

	/* �ɼ���־��� */
	public String getSys_clt_log_id()
	{
		return getValue( ITEM_SYS_CLT_LOG_ID );
	}

	public void setSys_clt_log_id( String sys_clt_log_id1 )
	{
		setValue( ITEM_SYS_CLT_LOG_ID, sys_clt_log_id1 );
	}

	/* �ӿ����� */
	public String getInf_name()
	{
		return getValue( ITEM_INF_NAME );
	}

	public void setInf_name( String inf_name1 )
	{
		setValue( ITEM_INF_NAME, inf_name1 );
	}

	/* �ӿ����� */
	public String getInf_desc()
	{
		return getValue( ITEM_INF_DESC );
	}

	public void setInf_desc( String inf_desc1 )
	{
		setValue( ITEM_INF_DESC, inf_desc1 );
	}

	/* �ɼ������� */
	public String getClt_num()
	{
		return getValue( ITEM_CLT_NUM );
	}

	public void setClt_num( String clt_num1 )
	{
		setValue( ITEM_CLT_NUM, clt_num1 );
	}

}

