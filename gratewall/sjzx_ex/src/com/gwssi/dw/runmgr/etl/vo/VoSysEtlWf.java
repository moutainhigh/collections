package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_etl_wf]的数据对象类
 * @author Administrator
 *
 */
public class VoSysEtlWf extends VoBase
{
	private static final long serialVersionUID = 200805081507300002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_ETL_WF_ID = "sys_etl_wf_id" ;	/* ID */
	public static final String ITEM_REP_ID = "rep_id" ;				/* 项目ID */
	public static final String ITEM_REP_FOLDERNAME = "rep_foldername" ;	/* 文件夹名称 */
	public static final String ITEM_WF_NAME = "wf_name" ;			/* 抽取服务名称 */
	public static final String ITEM_WF_MS = "wf_ms" ;				/* 抽取服务描述 */
	/**
	 * 构造函数
	 */
	public VoSysEtlWf()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysEtlWf(DataBus value)
	{
		super(value);
	}
	
	/* ID : String */
	public String getSys_etl_wf_id()
	{
		return getValue( ITEM_SYS_ETL_WF_ID );
	}

	public void setSys_etl_wf_id( String sys_etl_wf_id1 )
	{
		setValue( ITEM_SYS_ETL_WF_ID, sys_etl_wf_id1 );
	}

	/* 项目ID : String */
	public String getRep_id()
	{
		return getValue( ITEM_REP_ID );
	}

	public void setRep_id( String rep_id1 )
	{
		setValue( ITEM_REP_ID, rep_id1 );
	}

	/* 文件夹名称 : String */
	public String getRep_foldername()
	{
		return getValue( ITEM_REP_FOLDERNAME );
	}

	public void setRep_foldername( String rep_foldername1 )
	{
		setValue( ITEM_REP_FOLDERNAME, rep_foldername1 );
	}

	/* 抽取服务名称 : String */
	public String getWf_name()
	{
		return getValue( ITEM_WF_NAME );
	}

	public void setWf_name( String wf_name1 )
	{
		setValue( ITEM_WF_NAME, wf_name1 );
	}

	/* 抽取服务描述 : String */
	public String getWf_ms()
	{
		return getValue( ITEM_WF_MS );
	}

	public void setWf_ms( String wf_ms1 )
	{
		setValue( ITEM_WF_MS, wf_ms1 );
	}
}

