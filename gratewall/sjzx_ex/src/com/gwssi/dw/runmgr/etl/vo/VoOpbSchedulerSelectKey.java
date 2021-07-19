package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[opb_scheduler]�����ݶ�����
 * @author Administrator
 *
 */
public class VoOpbSchedulerSelectKey extends VoBase
{
	private static final long serialVersionUID = 200806031123550003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SCHEDULER_ID = "scheduler_id" ;	/* ���� */
	public static final String ITEM_SCHEDULER_NAME = "scheduler_name" ;	/* ������������ */
	
	/**
	 * ���캯��
	 */
	public VoOpbSchedulerSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoOpbSchedulerSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ���� : String */
	public String getScheduler_id()
	{
		return getValue( ITEM_SCHEDULER_ID );
	}

	public void setScheduler_id( String scheduler_id1 )
	{
		setValue( ITEM_SCHEDULER_ID, scheduler_id1 );
	}

	/* ������������ : String */
	public String getScheduler_name()
	{
		return getValue( ITEM_SCHEDULER_NAME );
	}

	public void setScheduler_name( String scheduler_name1 )
	{
		setValue( ITEM_SCHEDULER_NAME, scheduler_name1 );
	}

}

