package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_webservice_patameter]的数据对象类
 * 
 * @author Administrator
 * 
 */
public class VoCollectWebservicePatameter extends VoBase
{
	private static final long	serialVersionUID				= 201304101416370002L;

	/**
	 * 变量列表
	 */
	public static final String	ITEM_WEBSERVICE_PATAMETER_ID	= "webservice_patameter_id";	/* 参数ID */

	public static final String	ITEM_WEBSERVICE_TASK_ID			= "webservice_task_id";		/* webservice任务ID */

	public static final String	ITEM_PATAMETER_TYPE				= "patameter_type";			/* 参数类型 */

	public static final String	ITEM_PATAMETER_NAME				= "patameter_name";			/* 参数名 */

	public static final String	ITEM_PATAMETER_VALUE			= "patameter_value";			/* 参数值 */

	public static final String	ITEM_PATAMETER_STYLE			= "patameter_style";			/* 参数格式 */

	/**
	 * 构造函数
	 */
	public VoCollectWebservicePatameter()
	{
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param value
	 *            数据节点
	 */
	public VoCollectWebservicePatameter(DataBus value)
	{
		super(value);
	}

	/* 参数ID : String */
	public String getWebservice_patameter_id()
	{
		return getValue(ITEM_WEBSERVICE_PATAMETER_ID);
	}

	public void setWebservice_patameter_id(String webservice_patameter_id1)
	{
		setValue(ITEM_WEBSERVICE_PATAMETER_ID, webservice_patameter_id1);
	}

	/* webservice任务ID : String */
	public String getWebservice_task_id()
	{
		return getValue(ITEM_WEBSERVICE_TASK_ID);
	}

	public void setWebservice_task_id(String webservice_task_id1)
	{
		setValue(ITEM_WEBSERVICE_TASK_ID, webservice_task_id1);
	}

	/* 参数类型 : String */
	public String getPatameter_type()
	{
		return getValue(ITEM_PATAMETER_TYPE);
	}

	public void setPatameter_type(String patameter_type1)
	{
		setValue(ITEM_PATAMETER_TYPE, patameter_type1);
	}

	/* 参数名 : String */
	public String getPatameter_name()
	{
		return getValue(ITEM_PATAMETER_NAME);
	}

	public void setPatameter_name(String patameter_name1)
	{
		setValue(ITEM_PATAMETER_NAME, patameter_name1);
	}

	/* 参数值 : String */
	public String getPatameter_value()
	{
		return getValue(ITEM_PATAMETER_VALUE);
	}

	public void setPatameter_value(String patameter_value1)
	{
		setValue(ITEM_PATAMETER_VALUE, patameter_value1);
	}

	/* 参数类型 : String */
	public String getPatameter_style()
	{
		return getValue(ITEM_PATAMETER_STYLE);
	}

	public void setPatameter_style(String patameter_style1)
	{
		setValue(ITEM_PATAMETER_STYLE, patameter_style1);
	}

}
