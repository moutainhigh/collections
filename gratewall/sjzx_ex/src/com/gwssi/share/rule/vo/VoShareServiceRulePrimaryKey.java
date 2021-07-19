package com.gwssi.share.rule.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_service_rule]的数据对象类
 * @author Administrator
 *
 */
public class VoShareServiceRulePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304081757160004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_RULE_ID = "rule_id" ;			/* 服务访问规则ID */
	
	/**
	 * 构造函数
	 */
	public VoShareServiceRulePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareServiceRulePrimaryKey(DataBus value)
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

}

