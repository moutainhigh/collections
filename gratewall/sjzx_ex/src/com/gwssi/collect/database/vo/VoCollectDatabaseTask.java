package com.gwssi.collect.database.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_database_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectDatabaseTask extends VoBase
{
	private static final long serialVersionUID = 201307021053290002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DATABASE_TASK_ID = "database_task_id" ;	/* �ɼ����ݿ�����ID */
	public static final String ITEM_COLLECT_TASK_ID = "collect_task_id" ;	/* �ɼ�����ID */
	
	/**
	 * ���캯��
	 */
	public VoCollectDatabaseTask()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectDatabaseTask(DataBus value)
	{
		super(value);
	}
	
	/* �ɼ����ݿ�����ID : String */
	public String getDatabase_task_id()
	{
		return getValue( ITEM_DATABASE_TASK_ID );
	}

	public void setDatabase_task_id( String database_task_id1 )
	{
		setValue( ITEM_DATABASE_TASK_ID, database_task_id1 );
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

