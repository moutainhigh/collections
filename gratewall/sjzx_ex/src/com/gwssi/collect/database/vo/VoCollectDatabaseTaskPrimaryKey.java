package com.gwssi.collect.database.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_database_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectDatabaseTaskPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201307021053290004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DATABASE_TASK_ID = "database_task_id" ;	/* �ɼ����ݿ�����ID */
	
	/**
	 * ���캯��
	 */
	public VoCollectDatabaseTaskPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectDatabaseTaskPrimaryKey(DataBus value)
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

}

