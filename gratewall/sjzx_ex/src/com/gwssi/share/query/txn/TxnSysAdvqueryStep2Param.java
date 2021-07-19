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

import com.gwssi.share.query.vo.SysAdvqueryStep2ParamContext;

public class TxnSysAdvqueryStep2Param extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysAdvqueryStep2Param.class, SysAdvqueryStep2ParamContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_advquery_step2_param";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_advquery_step2_param list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_advquery_step2_param";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_advquery_step2_param";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_advquery_step2_param";
	
	// ɾ����¼
	private static final String DELETE_ROWSET_FUNCTION = "delete sys_advquery_step2_param list";
	/**
	 * ���캯��
	 */
	public TxnSysAdvqueryStep2Param()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�Զ����ѯ������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6022301( SysAdvqueryStep2ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow( context, outputNode, -1 );
		// ��ѯ��¼������ VoSysAdvqueryStep2ParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysAdvqueryStep2Param result[] = context.getSysAdvqueryStep2Params( outputNode );
	}
	
	/** �޸��Զ����ѯ�������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6022302( SysAdvqueryStep2ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysAdvqueryStep2Param sys_advquery_step2_param = context.getSysAdvqueryStep2Param( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �����Զ����ѯ�������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6022303( SysAdvqueryStep2ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysAdvqueryStep2Param sys_advquery_step2_param = context.getSysAdvqueryStep2Param( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�Զ����ѯ����������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6022304( SysAdvqueryStep2ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysAdvqueryStep2ParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysAdvqueryStep2Param result = context.getSysAdvqueryStep2Param( outputNode );
	}
	
	/** ɾ���Զ����ѯ�������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6022305( SysAdvqueryStep2ParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysAdvqueryStep2ParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ɾ��ĳ�Զ����ѯ�����еĲ��������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6022306( SysAdvqueryStep2ParamContext context ) throws TxnException
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
		SysAdvqueryStep2ParamContext appContext = new SysAdvqueryStep2ParamContext( context );
		invoke( method, appContext );
	}
}
