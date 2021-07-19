package com.gwssi.collect.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[etl_subject]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEtlSubject extends VoBase
{
	private static final long serialVersionUID = 201308130950380002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SUBJ_ID = "subj_id" ;			/* ����ID */
	public static final String ITEM_SUBJ_NAME = "subj_name" ;		/* ���������� */
	public static final String ITEM_SUBJ_DESC = "subj_desc" ;		/* ���������� */
	public static final String ITEM_IS_SHOW = "is_show" ;			/* �Ƿ���ʾ */
	public static final String ITEM_SUBJ_SORT = "subj_sort" ;		/* ����� */
	public static final String ITEM_START_TIME = "start_time" ;		/* ��ʼִ��ʱ�� */
	public static final String ITEM_INTEVAL = "inteval" ;			/* �������� */
	public static final String ITEM_WORKFLOW_NAME = "workflow_name" ;	/* �������� */
	public static final String ITEM_WORKFLOW_DESC = "workflow_desc" ;	/* �������� */
	public static final String ITEM_ADD_TYPE = "add_type" ;			/* ���ݴ������� */
	public static final String ITEM_WORKFLOW_ID = "workflow_id" ;	/* ����ID */
	public static final String ITEM_SCHEDULE_JSON = "schedule_json" ;			/* ��������ʵ������ */
	
	/**
	 * ���캯��
	 */
	public VoEtlSubject()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEtlSubject(DataBus value)
	{
		super(value);
	}
	
	/* ����ID : String */
	public String getSubj_id()
	{
		return getValue( ITEM_SUBJ_ID );
	}

	public void setSubj_id( String subj_id1 )
	{
		setValue( ITEM_SUBJ_ID, subj_id1 );
	}

	/* ���������� : String */
	public String getSubj_name()
	{
		return getValue( ITEM_SUBJ_NAME );
	}

	public void setSubj_name( String subj_name1 )
	{
		setValue( ITEM_SUBJ_NAME, subj_name1 );
	}

	/* ���������� : String */
	public String getSubj_desc()
	{
		return getValue( ITEM_SUBJ_DESC );
	}

	public void setSubj_desc( String subj_desc1 )
	{
		setValue( ITEM_SUBJ_DESC, subj_desc1 );
	}

	/* �Ƿ���ʾ : String */
	public String getIs_show()
	{
		return getValue( ITEM_IS_SHOW );
	}

	public void setIs_show( String is_show1 )
	{
		setValue( ITEM_IS_SHOW, is_show1 );
	}

	/* ����� : String */
	public String getSubj_sort()
	{
		return getValue( ITEM_SUBJ_SORT );
	}

	public void setSubj_sort( String subj_sort1 )
	{
		setValue( ITEM_SUBJ_SORT, subj_sort1 );
	}

	/* ��ʼִ��ʱ�� : String */
	public String getStart_time()
	{
		return getValue( ITEM_START_TIME );
	}

	public void setStart_time( String start_time1 )
	{
		setValue( ITEM_START_TIME, start_time1 );
	}

	/* �������� : String */
	public String getInteval()
	{
		return getValue( ITEM_INTEVAL );
	}

	public void setInteval( String inteval1 )
	{
		setValue( ITEM_INTEVAL, inteval1 );
	}

	/* �������� : String */
	public String getWorkflow_name()
	{
		return getValue( ITEM_WORKFLOW_NAME );
	}

	public void setWorkflow_name( String workflow_name1 )
	{
		setValue( ITEM_WORKFLOW_NAME, workflow_name1 );
	}

	/* �������� : String */
	public String getWorkflow_desc()
	{
		return getValue( ITEM_WORKFLOW_DESC );
	}

	public void setWorkflow_desc( String workflow_desc1 )
	{
		setValue( ITEM_WORKFLOW_DESC, workflow_desc1 );
	}

	/* ���ݴ������� : String */
	public String getAdd_type()
	{
		return getValue( ITEM_ADD_TYPE );
	}

	public void setAdd_type( String add_type1 )
	{
		setValue( ITEM_ADD_TYPE, add_type1 );
	}

	/* ����ID : String */
	public String getWorkflow_id()
	{
		return getValue( ITEM_WORKFLOW_ID );
	}

	public void setWorkflow_id( String workflow_id1 )
	{
		setValue( ITEM_WORKFLOW_ID, workflow_id1 );
	}

	
	/* ��������ԭʼ���� : String */
	public String getSchedule_json()
	{
		return getValue( ITEM_SCHEDULE_JSON );
	}

	public void setSchedule_json( String Schedule_json1 )
	{
		setValue( ITEM_SCHEDULE_JSON, Schedule_json1);
	}
}

