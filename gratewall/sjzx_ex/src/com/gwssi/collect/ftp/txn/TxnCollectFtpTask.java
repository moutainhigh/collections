package com.gwssi.collect.ftp.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.collect.ftp.vo.CollectFtpTaskContext;

public class TxnCollectFtpTask extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnCollectFtpTask.class, CollectFtpTaskContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "collect_ftp_task";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select collect_ftp_task list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one collect_ftp_task";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one collect_ftp_task";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one collect_ftp_task";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one collect_ftp_task";
	
	/**
	 * ���캯��
	 */
	public TxnCollectFtpTask()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯftp�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30201001( CollectFtpTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCollectFtpTaskSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoCollectFtpTask result[] = context.getCollectFtpTasks( outputNode );
	}
	
	/** �޸�ftp������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30201002( CollectFtpTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoCollectFtpTask collect_ftp_task = context.getCollectFtpTask( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����ftp������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30201003( CollectFtpTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoCollectFtpTask collect_ftp_task = context.getCollectFtpTask( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯftp���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30201004( CollectFtpTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCollectFtpTaskPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoCollectFtpTask result = context.getCollectFtpTask( outputNode );
		
		String service_targets_id=context.getRecord("primary-key").getValue("service_targets_id");//�������ID
		if(service_targets_id!=null&&!"".equals(service_targets_id)){
			context.getRecord("record").setValue("service_targets_id", service_targets_id);
		}
	}
	
	/** ɾ��ftp������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30201005( CollectFtpTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoCollectFtpTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "deleteFile", context, inputNode, outputNode );
		
		this.callService("30101013", context);
	}
	
	/** ��ѯftp�������ڲ鿴
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30201006( CollectFtpTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCollectFtpTaskPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoCollectFtpTask result = context.getCollectFtpTask( outputNode );
	
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
		CollectFtpTaskContext appContext = new CollectFtpTaskContext( context );
		invoke( method, appContext );
	}
}
