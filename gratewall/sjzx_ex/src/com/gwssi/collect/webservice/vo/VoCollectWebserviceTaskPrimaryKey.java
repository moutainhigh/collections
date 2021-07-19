package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_webservice_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectWebserviceTaskPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304101334340004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_WEBSERVICE_TASK_ID = "webservice_task_id" ;	/* WEBSERVICE����ID */
	
	/**
	 * ���캯��
	 */
	public VoCollectWebserviceTaskPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectWebserviceTaskPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* WEBSERVICE����ID : String */
	public String getWebservice_task_id()
	{
		return getValue( ITEM_WEBSERVICE_TASK_ID );
	}

	public void setWebservice_task_id( String webservice_task_id1 )
	{
		setValue( ITEM_WEBSERVICE_TASK_ID, webservice_task_id1 );
	}

}

