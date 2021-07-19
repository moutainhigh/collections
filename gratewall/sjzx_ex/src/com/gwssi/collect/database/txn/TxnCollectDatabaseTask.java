package com.gwssi.collect.database.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.collect.database.vo.CollectDatabaseTaskContext;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;

public class TxnCollectDatabaseTask extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnCollectDatabaseTask.class, CollectDatabaseTaskContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "collect_database_task";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select collect_database_task list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one collect_database_task";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one collect_database_task";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one collect_database_task";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one collect_database_task";
	
	/**
	 * ���캯��
	 */
	public TxnCollectDatabaseTask()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�ɼ����ݿ��б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30501001( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCollectDatabaseTaskSelectKey selectKey = context.getSelectKey( inputNode );
		String taskId = context.getRecord("select-key").getValue("collect_task_id");
		System.out.println("���ݿ�ɼ�taskId is " + taskId);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoCollectDatabaseTask result[] = context.getCollectDatabaseTasks( outputNode );
	}
	
	/** �޸Ĳɼ����ݿ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30501002( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoCollectDatabaseTask collect_database_task = context.getCollectDatabaseTask( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���Ӳɼ����ݿ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30501003( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoCollectDatabaseTask collect_database_task = context.getCollectDatabaseTask( inputNode );
		context.getRecord("record").setValue("created_time", CalendarUtil.getCurrentDateTime());
		context.getRecord("record").setValue("database_task_id", UuidGenerator.getUUID());
		System.out.println("����context="+context);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�ɼ����ݿ������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30501004( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCollectDatabaseTaskPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		String task_name = context.getRecord("primary-key").getValue("task_name");
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoCollectDatabaseTask result = context.getCollectDatabaseTask( outputNode );
		context.getRecord("record").setValue("task_name", task_name);
	}
	
	/** ɾ���ɼ����ݿ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30501005( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoCollectDatabaseTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		this.callService("30101043", context);
	}
	
	/** У���Ƿ������ظ�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30501006( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoCollectDatabaseTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		//table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		//table.executeFunction("queryCollectTaskInfo", context, inputNode, "tableinfo");//���ݱ���Ϣ
		table.executeFunction("queryTasknum", context, inputNode, outputNode);//�������б�
	}
	/** ��ѯ�ɼ����ݿ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30501007( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoCollectDatabaseTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		//table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		//table.executeFunction("queryCollectTaskInfo", context, inputNode, "tableinfo");//���ݱ���Ϣ
		table.executeFunction("queryCollectTaskInfo", context, inputNode, outputNode);//�������б�
	}
	/** ��ѯ�ɼ����ݿ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30501009( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		//table.executeFunction("queryCollectTaskInfo", context, inputNode, "tableinfo");//���ݱ���Ϣ
		table.executeFunction("queryCollectDataBaseTaskInfo", context, inputNode, outputNode);//�������б�
	}
	
	public void txn30501010( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCollectDatabaseTaskSelectKey selectKey = context.getSelectKey( inputNode );
		String taskId = context.getRecord("select-key").getValue("collect_task_id");
		System.out.println("���ݿ�ɼ�taskId is " + taskId);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "queryCollectDatabseTreeInfo", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoCollectDatabaseTask result[] = context.getCollectDatabaseTasks( outputNode );
	}
	
	public void txn30501011( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		//table.executeFunction("queryCollectTaskInfo", context, inputNode, "tableinfo");//���ݱ���Ϣ
		table.executeFunction("queryCollectDataBaseTaskInfoForStep", context, inputNode, outputNode);//�������б�
	}
	
	/** ɾ���ɼ����ݿ���Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30501013( CollectDatabaseTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoCollectDatabaseTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		this.callService("30101042", context);
	}
		
	/**
	 * ���ظ���ķ����������滻���׽ӿڵ��������
	 * ���ú���
	 * @param funcName ��������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����"
			);
		}
		
		// ִ��
		CollectDatabaseTaskContext appContext = new CollectDatabaseTaskContext( context );
		invoke( method, appContext );
	}
}
