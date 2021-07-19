package com.gwssi.share.query.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.share.query.vo.SysAdvqueryStep1ParamContext;

public class TxnSysAdvqueryStep1Param extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysAdvqueryStep1Param.class, SysAdvqueryStep1ParamContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_advquery_step1_param";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_advquery_step1_param list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_advquery_step1_param";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_advquery_step1_param";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_advquery_step1_param";
	
	// ɾ����¼
	private static final String DELETE_ROWSET_FUNCTION = "delete sys_advquery_step1_param list";
	
	/**
	 * ���캯��
	 */
	public TxnSysAdvqueryStep1Param()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�Զ����ѯ����һ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6022201( SysAdvqueryStep1ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		// ��ѯ��¼������ VoSysAdvqueryStep1ParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysAdvqueryStep1Param result[] = context.getSysAdvqueryStep1Params( outputNode );
	}
	
	/** �޸��Զ����ѯ����һ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6022202( SysAdvqueryStep1ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysAdvqueryStep1Param sys_advquery_step1_param = context.getSysAdvqueryStep1Param( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �����Զ����ѯ����һ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6022203( SysAdvqueryStep1ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysAdvqueryStep1Param sys_advquery_step1_param = context.getSysAdvqueryStep1Param( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�Զ����ѯ����һ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6022204( SysAdvqueryStep1ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysAdvqueryStep1ParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysAdvqueryStep1Param result = context.getSysAdvqueryStep1Param( outputNode );
	}
	
	/** ɾ���Զ����ѯ����һ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6022205( SysAdvqueryStep1ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysAdvqueryStep1ParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ɾ��ĳ�Զ����ѯ�����еĲ���һ����
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6022206( SysAdvqueryStep1ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysAdvqueryStep1ParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_ROWSET_FUNCTION, context, inputNode, outputNode );
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
		SysAdvqueryStep1ParamContext appContext = new SysAdvqueryStep1ParamContext( context );
		invoke( method, appContext );
	}
}
