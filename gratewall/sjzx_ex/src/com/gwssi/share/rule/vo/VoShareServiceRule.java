package com.gwssi.share.rule.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_service_rule]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareServiceRule extends VoBase
{
	private static final long serialVersionUID = 201304081757160002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_RULE_ID = "rule_id" ;			/* ������ʹ���ID */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* ����ID */
	public static final String ITEM_WEEK = "week" ;					/* �����ڼ� */
	public static final String ITEM_START_TIME = "start_time" ;		/* �����쿪ʼʱ�� */
	public static final String ITEM_END_TIME = "end_time" ;			/* ���������ʱ�� */
	public static final String ITEM_TIMES_DAY = "times_day" ;		/* ������ɷ��ʴ��� */
	public static final String ITEM_COUNT_DAT = "count_dat" ;		/* ������һ�οɷ��ʼ�¼��Ŀ */
	public static final String ITEM_TOTAL_COUNT_DAY = "total_count_day" ;	/* ������ɷ����ܼ�¼��Ŀ */
	
	/**
	 * ���캯��
	 */
	public VoShareServiceRule()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareServiceRule(DataBus value)
	{
		super(value);
	}
	
	/* ������ʹ���ID : String */
	public String getRule_id()
	{
		return getValue( ITEM_RULE_ID );
	}

	public void setRule_id( String rule_id1 )
	{
		setValue( ITEM_RULE_ID, rule_id1 );
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

	/* �����ڼ� : String */
	public String getWeek()
	{
		return getValue( ITEM_WEEK );
	}

	public void setWeek( String week1 )
	{
		setValue( ITEM_WEEK, week1 );
	}

	/* �����쿪ʼʱ�� : String */
	public String getStart_time()
	{
		return getValue( ITEM_START_TIME );
	}

	public void setStart_time( String start_time1 )
	{
		setValue( ITEM_START_TIME, start_time1 );
	}

	/* ���������ʱ�� : String */
	public String getEnd_time()
	{
		return getValue( ITEM_END_TIME );
	}

	public void setEnd_time( String end_time1 )
	{
		setValue( ITEM_END_TIME, end_time1 );
	}

	/* ������ɷ��ʴ��� : String */
	public String getTimes_day()
	{
		return getValue( ITEM_TIMES_DAY );
	}

	public void setTimes_day( String times_day1 )
	{
		setValue( ITEM_TIMES_DAY, times_day1 );
	}

	/* ������һ�οɷ��ʼ�¼��Ŀ : String */
	public String getCount_dat()
	{
		return getValue( ITEM_COUNT_DAT );
	}

	public void setCount_dat( String count_dat1 )
	{
		setValue( ITEM_COUNT_DAT, count_dat1 );
	}

	/* ������ɷ����ܼ�¼��Ŀ : String */
	public String getTotal_count_day()
	{
		return getValue( ITEM_TOTAL_COUNT_DAY );
	}

	public void setTotal_count_day( String total_count_day1 )
	{
		setValue( ITEM_TOTAL_COUNT_DAY, total_count_day1 );
	}

}

