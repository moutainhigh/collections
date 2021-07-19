package com.gwssi.collect.fileupload.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.collect.fileupload.vo.CollectFileUploadTaskContext;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;

public class TxnCollectFileUploadTask extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnCollectFileUploadTask.class, CollectFileUploadTaskContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "collect_file_upload_task";
	// ���ݱ�����
	private static final String TABLE_NAME_TASK = "collect_task";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select collect_file_upload_task list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one collect_file_upload_task";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one collect_file_upload_task";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one collect_file_upload_task";
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION_TASK = "insert one collect_task";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one collect_file_upload_task";
	
	/**
	 * ���캯��
	 */
	public TxnCollectFileUploadTask()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�ļ��ϴ��ɼ����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30301001( CollectFileUploadTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCollectFileUploadTaskSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoCollectFileUploadTask result[] = context.getCollectFileUploadTasks( outputNode );
	}
	
	/** �޸��ļ��ϴ��ɼ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30301002( CollectFileUploadTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoCollectFileUploadTask collect_file_upload_task = context.getCollectFileUploadTask( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �����ļ��ϴ��ɼ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30301003( CollectFileUploadTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		BaseTable table_task = TableFactory.getInstance().getTableObject( this, TABLE_NAME_TASK );
		// ���Ӽ�¼������ VoCollectFileUploadTask collect_file_upload_task = context.getCollectFileUploadTask( inputNode );
		
		String COLLECT_TASK_ID = UuidGenerator.getUUID();//�ɼ�����id
		String FILE_UPLOAD_TASK_ID = UuidGenerator.getUUID();//�ļ��ϴ�����ID
		context.getRecord("record").setValue("collect_task_id", COLLECT_TASK_ID);
		context.getRecord("record").setValue("file_upload_task_id", FILE_UPLOAD_TASK_ID);
		
		context.getRecord("record").setValue("created_time", CalendarUtil.getCurrentDateTime());
		context.getRecord("record").setValue("is_markup", ExConstant.IS_MARKUP_Y);//���볣��
		context.getRecord("record").setValue("creator_id", context.getRecord("oper-data").getValue("userID"));
		context.getRecord("record").setValue("task_status",ExConstant.SERVICE_STATE_Y);// ���볣��  ����
		
		table_task.executeFunction(INSERT_FUNCTION_TASK, context, inputNode, outputNode);
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�ļ��ϴ��ɼ��������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30301004( CollectFileUploadTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCollectFileUploadTaskPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoCollectFileUploadTask result = context.getCollectFileUploadTask( outputNode );
	}
	
	/** ɾ���ļ��ϴ��ɼ�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30301005( CollectFileUploadTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoCollectFileUploadTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
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
		CollectFileUploadTaskContext appContext = new CollectFileUploadTaskContext( context );
		invoke( method, appContext );
	}
}
