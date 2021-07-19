package com.gwssi.resource.share.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_business_topics]的数据对象类
 * @author Administrator
 *
 */
public class VoResBusinessTopics extends VoBase
{
	private static final long serialVersionUID = 201303151434280002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_BUSINESS_TOPICS_ID = "business_topics_id" ;	/* 业务主题ID */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* 服务对象ID */
	public static final String ITEM_TOPICS_NAME = "topics_name" ;	/* 主题名称 */
	public static final String ITEM_TOPICS_NO = "topics_no" ;		/* 主题编号 */
	public static final String ITEM_TOPICS_DESC = "topics_desc" ;	/* 主题描述 */
	public static final String ITEM_SHOW_ORDER = "show_order" ;		/* 显示顺序 */
	
	/**
	 * 构造函数
	 */
	public VoResBusinessTopics()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResBusinessTopics(DataBus value)
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

	/* 服务对象ID : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* 主题名称 : String */
	public String getTopics_name()
	{
		return getValue( ITEM_TOPICS_NAME );
	}

	public void setTopics_name( String topics_name1 )
	{
		setValue( ITEM_TOPICS_NAME, topics_name1 );
	}

	/* 主题编号 : String */
	public String getTopics_no()
	{
		return getValue( ITEM_TOPICS_NO );
	}

	public void setTopics_no( String topics_no1 )
	{
		setValue( ITEM_TOPICS_NO, topics_no1 );
	}

	/* 主题描述 : String */
	public String getTopics_desc()
	{
		return getValue( ITEM_TOPICS_DESC );
	}

	public void setTopics_desc( String topics_desc1 )
	{
		setValue( ITEM_TOPICS_DESC, topics_desc1 );
	}

	/* 显示顺序 : String */
	public String getShow_order()
	{
		return getValue( ITEM_SHOW_ORDER );
	}

	public void setShow_order( String show_order1 )
	{
		setValue( ITEM_SHOW_ORDER, show_order1 );
	}

}

