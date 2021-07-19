package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_webservice_patameter]�����ݶ�����
 * 
 * @author Administrator
 * 
 */
public class VoCollectWebservicePatameter extends VoBase
{
	private static final long	serialVersionUID				= 201304101416370002L;

	/**
	 * �����б�
	 */
	public static final String	ITEM_WEBSERVICE_PATAMETER_ID	= "webservice_patameter_id";	/* ����ID */

	public static final String	ITEM_WEBSERVICE_TASK_ID			= "webservice_task_id";		/* webservice����ID */

	public static final String	ITEM_PATAMETER_TYPE				= "patameter_type";			/* �������� */

	public static final String	ITEM_PATAMETER_NAME				= "patameter_name";			/* ������ */

	public static final String	ITEM_PATAMETER_VALUE			= "patameter_value";			/* ����ֵ */

	public static final String	ITEM_PATAMETER_STYLE			= "patameter_style";			/* ������ʽ */

	/**
	 * ���캯��
	 */
	public VoCollectWebservicePatameter()
	{
		super();
	}

	/**
	 * ���캯��
	 * 
	 * @param value
	 *            ���ݽڵ�
	 */
	public VoCollectWebservicePatameter(DataBus value)
	{
		super(value);
	}

	/* ����ID : String */
	public String getWebservice_patameter_id()
	{
		return getValue(ITEM_WEBSERVICE_PATAMETER_ID);
	}

	public void setWebservice_patameter_id(String webservice_patameter_id1)
	{
		setValue(ITEM_WEBSERVICE_PATAMETER_ID, webservice_patameter_id1);
	}

	/* webservice����ID : String */
	public String getWebservice_task_id()
	{
		return getValue(ITEM_WEBSERVICE_TASK_ID);
	}

	public void setWebservice_task_id(String webservice_task_id1)
	{
		setValue(ITEM_WEBSERVICE_TASK_ID, webservice_task_id1);
	}

	/* �������� : String */
	public String getPatameter_type()
	{
		return getValue(ITEM_PATAMETER_TYPE);
	}

	public void setPatameter_type(String patameter_type1)
	{
		setValue(ITEM_PATAMETER_TYPE, patameter_type1);
	}

	/* ������ : String */
	public String getPatameter_name()
	{
		return getValue(ITEM_PATAMETER_NAME);
	}

	public void setPatameter_name(String patameter_name1)
	{
		setValue(ITEM_PATAMETER_NAME, patameter_name1);
	}

	/* ����ֵ : String */
	public String getPatameter_value()
	{
		return getValue(ITEM_PATAMETER_VALUE);
	}

	public void setPatameter_value(String patameter_value1)
	{
		setValue(ITEM_PATAMETER_VALUE, patameter_value1);
	}

	/* �������� : String */
	public String getPatameter_style()
	{
		return getValue(ITEM_PATAMETER_STYLE);
	}

	public void setPatameter_style(String patameter_style1)
	{
		setValue(ITEM_PATAMETER_STYLE, patameter_style1);
	}

}
