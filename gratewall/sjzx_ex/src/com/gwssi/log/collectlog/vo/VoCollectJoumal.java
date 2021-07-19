package com.gwssi.log.collectlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_joumal]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectJoumal extends VoBase
{
	private static final long serialVersionUID = 201304101519320002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLLECT_JOUMAL_ID = "collect_joumal_id" ;	/* �ɼ���־ID */
	public static final String ITEM_COLLECT_TASK_ID = "collect_task_id" ;	/* �ɼ�����ID */
	public static final String ITEM_TASK_NAME = "task_name" ;		/* �������� */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* �������ID */
	public static final String ITEM_SERVICE_TARGETS_NAME = "service_targets_name" ;	/* ����������� */
	public static final String ITEM_COLLECT_TYPE = "collect_type" ;	/* ����� ftp */
	public static final String ITEM_TASK_ID = "task_id" ;		/* ����ID */
	public static final String ITEM_SERVICE_NO = "service_no" ;		/* ����ID */
	public static final String ITEM_TASK_START_TIME = "task_start_time" ;	/* ����ʼʱ�� */
	public static final String ITEM_TASK_END_TIME = "task_end_time" ;	/* �������ʱ�� */
	public static final String ITEM_TASK_CONSUME_TIME = "task_consume_time" ;	/* ��λ���� */
	public static final String ITEM_COLLECT_DATA_AMOUNT = "collect_data_amount" ;	/* ���βɼ������� */
	public static final String ITEM_TASK_STATUS = "task_status" ;	/* ����� ����ͣ�ù鵵 */
	public static final String ITEM_PATAMETER = "patameter" ;		/* ��ڲ��� */
	public static final String ITEM_RETURN_CODES = "return_codes" ;	/* ����� ���ݽ��������������ݱ�� */
	
	/**
	 * ���캯��
	 */
	public VoCollectJoumal()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectJoumal(DataBus value)
	{
		super(value);
	}
	
	/* �ɼ���־ID : String */
	public String getCollect_joumal_id()
	{
		return getValue( ITEM_COLLECT_JOUMAL_ID );
	}

	public void setCollect_joumal_id( String collect_joumal_id1 )
	{
		setValue( ITEM_COLLECT_JOUMAL_ID, collect_joumal_id1 );
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

	/* �������� : String */
	public String getTask_name()
	{
		return getValue( ITEM_TASK_NAME );
	}

	public void setTask_name( String task_name1 )
	{
		setValue( ITEM_TASK_NAME, task_name1 );
	}

	/* �������ID : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* ����������� : String */
	public String getService_targets_name()
	{
		return getValue( ITEM_SERVICE_TARGETS_NAME );
	}

	public void setService_targets_name( String service_targets_name1 )
	{
		setValue( ITEM_SERVICE_TARGETS_NAME, service_targets_name1 );
	}

	/* ����� ftp : String */
	public String getCollect_type()
	{
		return getValue( ITEM_COLLECT_TYPE );
	}

	public void setCollect_type( String collect_type1 )
	{
		setValue( ITEM_COLLECT_TYPE, collect_type1 );
	}

	/* ����ID : String */
	public String getTask_id()
	{
		return getValue( ITEM_TASK_ID );
	}

	public void setTask_id( String task_id1 )
	{
		setValue( ITEM_TASK_ID, task_id1 );
	}

	/* ����ID : String */
	public String getService_no()
	{
		return getValue( ITEM_SERVICE_NO );
	}

	public void setService_no( String service_no1 )
	{
		setValue( ITEM_SERVICE_NO, service_no1 );
	}
	
	/* ����ʼʱ�� : String */
	public String getTask_start_time()
	{
		return getValue( ITEM_TASK_START_TIME );
	}

	public void setTask_start_time( String task_start_time1 )
	{
		setValue( ITEM_TASK_START_TIME, task_start_time1 );
	}

	/* �������ʱ�� : String */
	public String getTask_end_time()
	{
		return getValue( ITEM_TASK_END_TIME );
	}

	public void setTask_end_time( String task_end_time1 )
	{
		setValue( ITEM_TASK_END_TIME, task_end_time1 );
	}

	/* ��λ���� : String */
	public String getTask_consume_time()
	{
		return getValue( ITEM_TASK_CONSUME_TIME );
	}

	public void setTask_consume_time( String task_consume_time1 )
	{
		setValue( ITEM_TASK_CONSUME_TIME, task_consume_time1 );
	}

	/* ���βɼ������� : String */
	public String getCollect_data_amount()
	{
		return getValue( ITEM_COLLECT_DATA_AMOUNT );
	}

	public void setCollect_data_amount( String collect_data_amount1 )
	{
		setValue( ITEM_COLLECT_DATA_AMOUNT, collect_data_amount1 );
	}

	/* ����� ����ͣ�ù鵵 : String */
	public String getTask_status()
	{
		return getValue( ITEM_TASK_STATUS );
	}

	public void setTask_status( String task_status1 )
	{
		setValue( ITEM_TASK_STATUS, task_status1 );
	}

	/* ��ڲ��� : String */
	public String getPatameter()
	{
		return getValue( ITEM_PATAMETER );
	}

	public void setPatameter( String patameter1 )
	{
		setValue( ITEM_PATAMETER, patameter1 );
	}

	/* ����� ���ݽ��������������ݱ�� : String */
	public String getReturn_codes()
	{
		return getValue( ITEM_RETURN_CODES );
	}

	public void setReturn_codes( String return_codes1 )
	{
		setValue( ITEM_RETURN_CODES, return_codes1 );
	}

}

