package com.gwssi.dw.aic.bj.exc.que.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.aic.bj.exc.que.vo.ExcQueAuthContext;

public class TxnExcQueAuth extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnExcQueAuth.class, ExcQueAuthContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "exc_que_auth";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "${mod.function.rowset}";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one exc_que_auth";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "${mod.function.insert}";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "${mod.function.delete}";
	
	/**
	 * ���캯��
	 */
	public TxnExcQueAuth()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60114011( ExcQueAuthContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoExcQueAuthSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoExcQueAuth result[] = context.getExcQueAuths( outputNode );
	}
	
	/** �޸ı����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60114012( ExcQueAuthContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoExcQueAuth exc_que_auth = context.getExcQueAuth( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӱ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60114013( ExcQueAuthContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoExcQueAuth exc_que_auth = context.getExcQueAuth( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ��������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60114014( ExcQueAuthContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoExcQueAuthPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoExcQueAuth result = context.getExcQueAuth( outputNode );
	}
	
	/** ɾ�������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60114015( ExcQueAuthContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoExcQueAuthPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		ExcQueAuthContext appContext = new ExcQueAuthContext( context );
		invoke( method, appContext );
	}
}
