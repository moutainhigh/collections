package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_webservice_task]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectWebserviceTaskPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304101334340004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_WEBSERVICE_TASK_ID = "webservice_task_id" ;	/* WEBSERVICE任务ID */
	
	/**
	 * 构造函数
	 */
	public VoCollectWebserviceTaskPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectWebserviceTaskPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* WEBSERVICE任务ID : String */
	public String getWebservice_task_id()
	{
		return getValue( ITEM_WEBSERVICE_TASK_ID );
	}

	public void setWebservice_task_id( String webservice_task_id1 )
	{
		setValue( ITEM_WEBSERVICE_TASK_ID, webservice_task_id1 );
	}

}

