package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[opb_scheduler]�����ݶ�����
 * @author Administrator
 *
 */
public class VoOpbScheduler extends VoBase
{
	private static final long serialVersionUID = 200806031123540002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SCHEDULER_ID = "scheduler_id" ;	/* ���� */
	public static final String ITEM_START_TIME = "start_time" ;		/* ��ʼʱ�� */
	public static final String ITEM_END_TIME = "end_time" ;			/* ����ʱ�� */
	public static final String ITEM_RUN_OPTIONS = "run_options" ;	/* ����ѡ�� */
	public static final String ITEM_END_OPTIONS = "end_options" ;	/* ����ѡ�� */
	public static final String ITEM_SCHEDULER_NAME = "scheduler_name" ;	/* ������������ */
	public static final String ITEM_RUN_COUNT = "run_count" ;		/* ���д��� */
	
	/**
	 * ���캯��
	 */
	public VoOpbScheduler()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoOpbScheduler(DataBus value)
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

	/* ��ʼʱ�� : String */
	public String getStart_time()
	{
		return getValue( ITEM_START_TIME );
	}

	public void setStart_time( String start_time1 )
	{
		setValue( ITEM_START_TIME, start_time1 );
	}

	/* ����ʱ�� : String */
	public String getEnd_time()
	{
		return getValue( ITEM_END_TIME );
	}

	public void setEnd_time( String end_time1 )
	{
		setValue( ITEM_END_TIME, end_time1 );
	}

	/* ����ѡ�� : String */
	public String getRun_options()
	{
		return getValue( ITEM_RUN_OPTIONS );
	}

	public void setRun_options( String run_options1 )
	{
		setValue( ITEM_RUN_OPTIONS, run_options1 );
	}

	/* ����ѡ�� : String */
	public String getEnd_options()
	{
		return getValue( ITEM_END_OPTIONS );
	}

	public void setEnd_options( String end_options1 )
	{
		setValue( ITEM_END_OPTIONS, end_options1 );
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

	/* ���д��� : String */
	public String getRun_count()
	{
		return getValue( ITEM_RUN_COUNT );
	}

	public void setRun_count( String run_count1 )
	{
		setValue( ITEM_RUN_COUNT, run_count1 );
	}

}

