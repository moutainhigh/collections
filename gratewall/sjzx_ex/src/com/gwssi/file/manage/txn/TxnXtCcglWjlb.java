package com.gwssi.file.manage.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.file.manage.vo.XtCcglWjlbContext;

public class TxnXtCcglWjlb extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnXtCcglWjlb.class, XtCcglWjlbContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "xt_ccgl_wjlb";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select xt_ccgl_wjlb list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one xt_ccgl_wjlb";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one xt_ccgl_wjlb";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one xt_ccgl_wjlb";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one xt_ccgl_wjlb";
	
	/**
	 * ���캯��
	 */
	public TxnXtCcglWjlb()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�ļ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn604050101( XtCcglWjlbContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoXtCcglWjlbSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoXtCcglWjlb result[] = context.getXtCcglWjlbs( outputNode );
	}
	
	/** �޸��ļ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn604050102( XtCcglWjlbContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoXtCcglWjlb xt_ccgl_wjlb = context.getXtCcglWjlb( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �����ļ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn604050103( XtCcglWjlbContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoXtCcglWjlb xt_ccgl_wjlb = context.getXtCcglWjlb( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�ļ���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn604050104( XtCcglWjlbContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoXtCcglWjlbPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoXtCcglWjlb result = context.getXtCcglWjlb( outputNode );
	}
	
	/** ɾ���ļ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn604050105( XtCcglWjlbContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoXtCcglWjlbPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		XtCcglWjlbContext appContext = new XtCcglWjlbContext( context );
		invoke( method, appContext );
	}
}
