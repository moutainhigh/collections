package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_etl_wf]的数据对象类
 * @author Administrator
 *
 */
public class VoSysEtlWfSelectKey extends VoBase
{
	private static final long serialVersionUID = 200805081507300003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_REP_ID = "rep_id" ;				/* 项目ID */
	
	/**
	 * 构造函数
	 */
	public VoSysEtlWfSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysEtlWfSelectKey(DataBus value)
	{
		super(value);
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

}

