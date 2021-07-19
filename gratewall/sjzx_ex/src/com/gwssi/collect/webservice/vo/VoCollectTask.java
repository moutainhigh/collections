package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectTask extends VoBase
{
	private static final long serialVersionUID = 201304101123030002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLLECT_TASK_ID = "collect_task_id" ;	/* �ɼ�����ID */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* ����������� */
	public static final String ITEM_DATA_SOURCE_ID = "data_source_id" ;	/* ����Դ */
	public static final String ITEM_TASK_NAME = "task_name" ;		/* �������� */
	public static final String ITEM_COLLECT_TYPE = "collect_type" ;	/* �ɼ����� */
	public static final String ITEM_TASK_DESCRIPTION = "task_description" ;	/* �������� */
	public static final String ITEM_RECORD = "record" ;				/* ����˵�� */
	public static final String ITEM_TASK_STATUS = "task_status" ;	/* ����״̬ */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* ��Ч��� */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* ������ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* ����޸���ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* ����޸�ʱ�� */
	public static final String ITEM_FJ_FK = "fj_fk" ;				/* ����id */
	public static final String ITEM_FJMC = "fjmc" ;					/* �������� */
	public static final String ITEM_DELNAMES = "delNAMEs";			/* delNAMEs */
	public static final String ITEM_DELIDS = "delIDs";			/* delIDs */
	
	/**
	 * ���캯��
	 */
	public VoCollectTask()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectTask(DataBus value)
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

	/* ����������� : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* ����Դ : String */
	public String getData_source_id()
	{
		return getValue( ITEM_DATA_SOURCE_ID );
	}

	public void setData_source_id( String data_source_id1 )
	{
		setValue( ITEM_DATA_SOURCE_ID, data_source_id1 );
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

	/* �ɼ����� : String */
	public String getCollect_type()
	{
		return getValue( ITEM_COLLECT_TYPE );
	}

	public void setCollect_type( String collect_type1 )
	{
		setValue( ITEM_COLLECT_TYPE, collect_type1 );
	}

	/* �������� : String */
	public String getTask_description()
	{
		return getValue( ITEM_TASK_DESCRIPTION );
	}

	public void setTask_description( String task_description1 )
	{
		setValue( ITEM_TASK_DESCRIPTION, task_description1 );
	}

	/* ����˵�� : String */
	public String getRecord()
	{
		return getValue( ITEM_RECORD );
	}

	public void setRecord( String record1 )
	{
		setValue( ITEM_RECORD, record1 );
	}

	/* ����״̬ : String */
	public String getTask_status()
	{
		return getValue( ITEM_TASK_STATUS );
	}

	public void setTask_status( String task_status1 )
	{
		setValue( ITEM_TASK_STATUS, task_status1 );
	}

	/* ��Ч��� : String */
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

