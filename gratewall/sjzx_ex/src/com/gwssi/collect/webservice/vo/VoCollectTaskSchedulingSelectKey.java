package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_task_scheduling]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectTaskSchedulingSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304181056000003L;
	
	/**
	 * �����б�
	 */
	//public static final String ITEM_task_name = "task_name" ;			/* �ƻ��������� */
	public static final String ITEM_JHRW_LX = "scheduling_type" ;			/* �ƻ��������� */
	
	/**
	 * ���캯��
	 */
	public VoCollectTaskSchedulingSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectTaskSchedulingSelectKey(DataBus value)
	{
		super(value);
	}
	
//	/* �ƻ��������� : String */
//	public String gettask_name()
//	{
//		return getValue( ITEM_task_name );
//	}
//
//	public void settask_name( String task_name1 )
//	{
//		setValue( ITEM_task_name, task_name1 );
//	}

	/* �ƻ��������� : String */
	public String getJhrw_lx()
	{
		return getValue( ITEM_JHRW_LX );
	}

	public void setJhrw_lx( String jhrw_lx1 )
	{
		setValue( ITEM_JHRW_LX, jhrw_lx1 );
	}

}

