package com.gwssi.resource.share.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_business_topics]的数据对象类
 * @author Administrator
 *
 */
public class VoResBusinessTopicsPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303151434280004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_BUSINESS_TOPICS_ID = "business_topics_id" ;	/* 业务主题ID */
	
	/**
	 * 构造函数
	 */
	public VoResBusinessTopicsPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResBusinessTopicsPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 业务主题ID : String */
	public String getBusiness_topics_id()
	{
		return getValue( ITEM_BUSINESS_TOPICS_ID );
	}

	public void setBusiness_topics_id( String business_topics_id1 )
	{
		setValue( ITEM_BUSINESS_TOPICS_ID, business_topics_id1 );
	}

}

