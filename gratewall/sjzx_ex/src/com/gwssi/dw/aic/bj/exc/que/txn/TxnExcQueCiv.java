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
import com.gwssi.dw.aic.bj.exc.que.vo.ExcQueCivContext;

public class TxnExcQueCiv extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnExcQueCiv.class, ExcQueCivContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "exc_que_civ";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "${mod.function.rowset}";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one exc_que_civ";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "${mod.function.insert}";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "${mod.function.delete}";
	
	/**
	 * ���캯��
	 */
	public TxnExcQueCiv()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��ҵ��λ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60114021( ExcQueCivContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoExcQueCivSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoExcQueCiv result[] = context.getExcQueCivs( outputNode );
	}
	
	/** �޸���ҵ��λ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60114022( ExcQueCivContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoExcQueCiv exc_que_civ = context.getExcQueCiv( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ������ҵ��λ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60114023( ExcQueCivContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoExcQueCiv exc_que_civ = context.getExcQueCiv( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ��ҵ��λ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60114024( ExcQueCivContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoExcQueCivPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoExcQueCiv result = context.getExcQueCiv( outputNode );
	}
	
	/** ɾ����ҵ��λ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60114025( ExcQueCivContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoExcQueCivPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		ExcQueCivContext appContext = new ExcQueCivContext( context );
		invoke( method, appContext );
	}
}
