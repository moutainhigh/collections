package com.gwssi.log.collectlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_joumal]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectJoumalSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304101519320003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SERVICE_TARGETS_NAME = "service_targets_name" ;	/* 服务对象名称 */
	public static final String ITEM_COLLECT_TYPE = "collect_type" ;	/* 代码表 ftp */
	public static final String ITEM_TASK_STATUS = "task_status" ;	/* 代码表 启用停用归档 */
	
	/**
	 * 构造函数
	 */
	public VoCollectJoumalSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectJoumalSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 服务对象名称 : String */
	public String getService_targets_name()
	{
		return getValue( ITEM_SERVICE_TARGETS_NAME );
	}

	public void setService_targets_name( String service_targets_name1 )
	{
		setValue( ITEM_SERVICE_TARGETS_NAME, service_targets_name1 );
	}

	/* 代码表 ftp : String */
	public String getCollect_type()
	{
		return getValue( ITEM_COLLECT_TYPE );
	}

	public void setCollect_type( String collect_type1 )
	{
		setValue( ITEM_COLLECT_TYPE, collect_type1 );
	}

	/* 代码表 启用停用归档 : String */
	public String getTask_status()
	{
		return getValue( ITEM_TASK_STATUS );
	}

	public void setTask_status( String task_status1 )
	{
		setValue( ITEM_TASK_STATUS, task_status1 );
	}

}

