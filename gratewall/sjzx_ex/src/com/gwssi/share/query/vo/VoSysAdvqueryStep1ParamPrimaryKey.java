package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_advquery_step1_param]的数据对象类
 * @author Administrator
 *
 */
public class VoSysAdvqueryStep1ParamPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809261021030012L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_ADVQUERY_STEP1_PARAM_ID = "sys_advquery_step1_param_id" ;	/* 高级查询步骤一参数编号 */
	
	/**
	 * 构造函数
	 */
	public VoSysAdvqueryStep1ParamPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysAdvqueryStep1ParamPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 高级查询步骤一参数编号 : String */
	public String getSys_advquery_step1_param_id()
	{
		return getValue( ITEM_SYS_ADVQUERY_STEP1_PARAM_ID );
	}

	public void setSys_advquery_step1_param_id( String sys_advquery_step1_param_id1 )
	{
		setValue( ITEM_SYS_ADVQUERY_STEP1_PARAM_ID, sys_advquery_step1_param_id1 );
	}

}

