package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectTaskPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304101123030004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLLECT_TASK_ID = "collect_task_id" ;	/* �ɼ�����ID */
	
	/**
	 * ���캯��
	 */
	public VoCollectTaskPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectTaskPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �ɼ�����ID : String */
	public String getCollect_task_id()
	{
		return getValue( ITEM_COLLECT_TASK_ID );
	}

	public void setCollect_task_id( String collect_task_id1 )
	{
		setValue( ITEM_COLLECT_TASK_ID, collect_task_id1 );
	}

}

