package cn.gwssi.sys.feedback.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.sys.feedback.vo.SysFeedbackContext;

public class TxnSysFeedback extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysFeedback.class, SysFeedbackContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_feedback";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_feedback list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_feedback";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_feedback";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_feedback";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_feedback";
	
	/**
	 * ���캯��
	 */
	public TxnSysFeedback()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn711001( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysFeedbackSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysFeedback result[] = context.getSysFeedbacks( outputNode );
	}
	
	/** �޸����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn711002( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysFeedback sys_feedback = context.getSysFeedback( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn711003( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String uuid = com.gwssi.common.util.UuidGenerator.getUUID();
		context.getRecord(inputNode).setValue("sys_feedback_id", uuid);
		// ���Ӽ�¼������ VoSysFeedback sys_feedback = context.getSysFeedback( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn711004( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysFeedbackPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysFeedback result = context.getSysFeedback( outputNode );
	}
	
	/** ɾ�����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn711005( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysFeedbackPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��������ļ�¼��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn711006( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����������޸���־
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn711007( SysFeedbackContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.getHistoryLog( context, inputNode, outputNode );
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
		SysFeedbackContext appContext = new SysFeedbackContext( context );
		invoke( method, appContext );
	}
}
