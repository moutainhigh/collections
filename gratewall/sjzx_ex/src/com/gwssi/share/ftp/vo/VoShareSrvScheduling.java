package com.gwssi.share.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_srv_scheduling]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareSrvScheduling extends VoBase
{
	private static final long serialVersionUID = 201308211658410002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SRV_SCHEDULING_ID = "srv_scheduling_id" ;	/* �������ID */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* ����ID */
	public static final String ITEM_SERVICE_NO = "service_no" ;		/* ������ */
	public static final String ITEM_SERVICE_NAME = "service_name" ;	/* �������� */
	public static final String ITEM_JOB_CLASS_NAME = "job_class_name" ;	/* �������õ����� */
	public static final String ITEM_SCHEDULING_TYPE = "scheduling_type" ;	/* �ƻ��������� */
	public static final String ITEM_SCHEDULING_DAY = "scheduling_day" ;	/* �ƻ��������� */
	public static final String ITEM_START_TIME = "start_time" ;		/* �ƻ�����ʼʱ�� */
	public static final String ITEM_END_TIME = "end_time" ;			/* �ƻ��������ʱ�� */
	public static final String ITEM_SCHEDULING_COUNT = "scheduling_count" ;	/* �ƻ�����ִ�д��� */
	public static final String ITEM_INTERVAL_TIME = "interval_time" ;	/* ÿ�μ��ʱ�� */
	public static final String ITEM_SCHEDULING_WEEK = "scheduling_week" ;	/* �ƻ��������� */
	public static final String ITEM_SCHEDULING_DAY1 = "scheduling_day1" ;	/* �ƻ������������� */
	public static final String ITEM_TASK_EXPRESSION = "task_expression" ;	/* ���ʽ */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* ����� ��Ч ��Ч */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* ������ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* ����޸���ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* ����޸�ʱ�� */
	
	/**
	 * ���캯��
	 */
	public VoShareSrvScheduling()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareSrvScheduling(DataBus value)
	{
		super(value);
	}
	
	/* �������ID : String */
	public String getSrv_scheduling_id()
	{
		return getValue( ITEM_SRV_SCHEDULING_ID );
	}

	public void setSrv_scheduling_id( String srv_scheduling_id1 )
	{
		setValue( ITEM_SRV_SCHEDULING_ID, srv_scheduling_id1 );
	}

	/* ����ID : String */
	public String getService_id()
	{
		return getValue( ITEM_SERVICE_ID );
	}

	public void setService_id( String service_id1 )
	{
		setValue( ITEM_SERVICE_ID, service_id1 );
	}

	/* ������ : String */
	public String getService_no()
	{
		return getValue( ITEM_SERVICE_NO );
	}

	public void setService_no( String service_no1 )
	{
		setValue( ITEM_SERVICE_NO, service_no1 );
	}

	/* �������� : String */
	public String getService_name()
	{
		return getValue( ITEM_SERVICE_NAME );
	}

	public void setService_name( String service_name1 )
	{
		setValue( ITEM_SERVICE_NAME, service_name1 );
	}

	/* �������õ����� : String */
	public String getJob_class_name()
	{
		return getValue( ITEM_JOB_CLASS_NAME );
	}

	public void setJob_class_name( String job_class_name1 )
	{
		setValue( ITEM_JOB_CLASS_NAME, job_class_name1 );
	}

	/* �ƻ��������� : String */
	public String getScheduling_type()
	{
		return getValue( ITEM_SCHEDULING_TYPE );
	}

	public void setScheduling_type( String scheduling_type1 )
	{
		setValue( ITEM_SCHEDULING_TYPE, scheduling_type1 );
	}

	/* �ƻ��������� : String */
	public String getScheduling_day()
	{
		return getValue( ITEM_SCHEDULING_DAY );
	}

	public void setScheduling_day( String scheduling_day1 )
	{
		setValue( ITEM_SCHEDULING_DAY, scheduling_day1 );
	}

	/* �ƻ�����ʼʱ�� : String */
	public String getStart_time()
	{
		return getValue( ITEM_START_TIME );
	}

	public void setStart_time( String start_time1 )
	{
		setValue( ITEM_START_TIME, start_time1 );
	}

	/* �ƻ��������ʱ�� : String */
	public String getEnd_time()
	{
		return getValue( ITEM_END_TIME );
	}

	public void setEnd_time( String end_time1 )
	{
		setValue( ITEM_END_TIME, end_time1 );
	}

	/* �ƻ�����ִ�д��� : String */
	public String getScheduling_count()
	{
		return getValue( ITEM_SCHEDULING_COUNT );
	}

	public void setScheduling_count( String scheduling_count1 )
	{
		setValue( ITEM_SCHEDULING_COUNT, scheduling_count1 );
	}

	/* ÿ�μ��ʱ�� : String */
	public String getInterval_time()
	{
		return getValue( ITEM_INTERVAL_TIME );
	}

	public void setInterval_time( String interval_time1 )
	{
		setValue( ITEM_INTERVAL_TIME, interval_time1 );
	}

	/* �ƻ��������� : String */
	public String getScheduling_week()
	{
		return getValue( ITEM_SCHEDULING_WEEK );
	}

	public void setScheduling_week( String scheduling_week1 )
	{
		setValue( ITEM_SCHEDULING_WEEK, scheduling_week1 );
	}

	/* �ƻ������������� : String */
	public String getScheduling_day1()
	{
		return getValue( ITEM_SCHEDULING_DAY1 );
	}

	public void setScheduling_day1( String scheduling_day11 )
	{
		setValue( ITEM_SCHEDULING_DAY1, scheduling_day11 );
	}

	/* ���ʽ : String */
	public String getTask_expression()
	{
		return getValue( ITEM_TASK_EXPRESSION );
	}

	public void setTask_expression( String task_expression1 )
	{
		setValue( ITEM_TASK_EXPRESSION, task_expression1 );
	}

	/* ����� ��Ч ��Ч : String */
	public String getIs_markup()
	{
		return getValue( ITEM_IS_MARKUP );
	}

	public void setIs_markup( String is_markup1 )
	{
		setValue( ITEM_IS_MARKUP, is_markup1 );
	}

	/* ������ID : String */
	public String getCreator_id()
	{
		return getValue( ITEM_CREATOR_ID );
	}

	public void setCreator_id( String creator_id1 )
	{
		setValue( ITEM_CREATOR_ID, creator_id1 );
	}

	/* ����ʱ�� : String */
	public String getCreated_time()
	{
		return getValue( ITEM_CREATED_TIME );
	}

	public void setCreated_time( String created_time1 )
	{
		setValue( ITEM_CREATED_TIME, created_time1 );
	}

	/* ����޸���ID : String */
	public String getLast_modify_id()
	{
		return getValue( ITEM_LAST_MODIFY_ID );
	}

	public void setLast_modify_id( String last_modify_id1 )
	{
		setValue( ITEM_LAST_MODIFY_ID, last_modify_id1 );
	}

	/* ����޸�ʱ�� : String */
	public String getLast_modify_time()
	{
		return getValue( ITEM_LAST_MODIFY_TIME );
	}

	public void setLast_modify_time( String last_modify_time1 )
	{
		setValue( ITEM_LAST_MODIFY_TIME, last_modify_time1 );
	}

}

