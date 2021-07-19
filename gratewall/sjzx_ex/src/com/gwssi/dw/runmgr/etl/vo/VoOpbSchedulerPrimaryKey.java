package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[opb_scheduler]�����ݶ�����
 * @author Administrator
 *
 */
public class VoOpbSchedulerPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200806031123550004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SCHEDULER_ID = "scheduler_id" ;	/* ���� */
	
	/**
	 * ���캯��
	 */
	public VoOpbSchedulerPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoOpbSchedulerPrimaryKey(DataBus value)
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

}

