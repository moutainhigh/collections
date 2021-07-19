package cn.gwssi.dw.aic.bj.ecomm.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.aic.bj.ecomm.vo.EbEntCheckTaskContext;

public class TxnEbEntCheckTask extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnEbEntCheckTask.class, EbEntCheckTaskContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "eb_ent_check_task";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select eb_ent_check_task list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one eb_ent_check_task";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one eb_ent_check_task";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one eb_ent_check_task";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one eb_ent_check_task";
	
	/**
	 * ���캯��
	 */
	public TxnEbEntCheckTask()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯѲ�������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026501( EbEntCheckTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbEntCheckTaskSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoEbEntCheckTask result[] = context.getEbEntCheckTasks( outputNode );
	}
	
	/** �޸�Ѳ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026502( EbEntCheckTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoEbEntCheckTask eb_ent_check_task = context.getEbEntCheckTask( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����Ѳ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026503( EbEntCheckTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoEbEntCheckTask eb_ent_check_task = context.getEbEntCheckTask( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯѲ�����������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026504( EbEntCheckTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbEntCheckTaskPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoEbEntCheckTask result = context.getEbEntCheckTask( outputNode );
	}
	
	/** ɾ��Ѳ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026505( EbEntCheckTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoEbEntCheckTaskPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯѲ�������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026506( EbEntCheckTaskContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbEntCheckTaskSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryCheckTaskInfo", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoEbEntCheckTask result[] = context.getEbEntCheckTasks( outputNode );
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
		EbEntCheckTaskContext appContext = new EbEntCheckTaskContext( context );
		invoke( method, appContext );
	}
}
