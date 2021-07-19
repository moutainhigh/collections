package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_advquery_step2_param]的数据对象类
 * @author Administrator
 *
 */
public class VoSysAdvqueryStep2ParamSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809261020110007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_ADVANCED_QUERY_ID = "sys_advanced_query_id" ;	/* 高级查询编号 */
	
	/**
	 * 构造函数
	 */
	public VoSysAdvqueryStep2ParamSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysAdvqueryStep2ParamSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 高级查询编号 : String */
	public String getSys_advanced_query_id()
	{
		return getValue( ITEM_SYS_ADVANCED_QUERY_ID );
	}

	public void setSys_advanced_query_id( String sys_advanced_query_id1 )
	{
		setValue( ITEM_SYS_ADVANCED_QUERY_ID, sys_advanced_query_id1 );
	}

}

