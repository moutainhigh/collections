package com.gwssi.sysmgr.priv.datapriv.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.sysmgr.priv.datapriv.vo.FuncdataobjectContext;

public class TxnFuncdataobject extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnFuncdataobject.class, FuncdataobjectContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "funcdataobject";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select funcdataobject list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "${mod.function.select}";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one funcdataobject";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one funcdataobject";
	
	/**
	 * ���캯��
	 */
	public TxnFuncdataobject()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ���ù��ܹ�ϵ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103021( FuncdataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoFuncdataobjectSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoFuncdataobject result[] = context.getFuncdataobjects( outputNode );
		System.out.println("");
	}
	
	/** �޸����ù��ܹ�ϵ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103022( FuncdataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoFuncdataobject funcdataobject = context.getFuncdataobject( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �������ù��ܹ�ϵ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103023( FuncdataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoFuncdataobject funcdataobject = context.getFuncdataobject( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ���ù��ܹ�ϵ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103024( FuncdataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoFuncdataobjectPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoFuncdataobject result = context.getFuncdataobject( outputNode );
	}
	
	/** ɾ�����ù��ܹ�ϵ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103025( FuncdataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoFuncdataobjectPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, "record", outputNode );
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
		FuncdataobjectContext appContext = new FuncdataobjectContext( context );
		invoke( method, appContext );
	}
}
