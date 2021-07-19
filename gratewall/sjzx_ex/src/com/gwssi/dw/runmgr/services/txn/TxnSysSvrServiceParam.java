package com.gwssi.dw.runmgr.services.txn;

import java.lang.reflect.Method;
import java.util.HashMap;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.dw.runmgr.services.vo.SysSvrServiceParamContext;

public class TxnSysSvrServiceParam extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysSvrServiceParam.class, SysSvrServiceParamContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_svr_service_param";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_svr_service_param list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_svr_service_param";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_svr_service_param";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_svr_service_param";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete sys_svr_service_param list";
	
	// ɾ����¼
	private static final String DELETE_ONE = "delete one sys_svr_service_param";
	
	// ��ѯ��¼
	private static final String SELECT_ORDER_FUNCTION = "select next_order sys_svr_service_param";
	
	/**
	 * ���캯��
	 */
	public TxnSysSvrServiceParam()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�����������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50205001( SysSvrServiceParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		// ��ѯ��¼������ VoSysSvrServiceParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysSvrServiceParam result[] = context.getSysSvrServiceParams( outputNode );
	}
	
	/** �޸Ĺ�����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50205002( SysSvrServiceParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysSvrServiceParam sys_svr_service_param = context.getSysSvrServiceParam( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӹ�����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50205003( SysSvrServiceParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysSvrServiceParam sys_svr_service_param = context.getSysSvrServiceParam( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ���������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50205004( SysSvrServiceParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysSvrServiceParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysSvrServiceParam result = context.getSysSvrServiceParam( outputNode );
	}
	
	/** ɾ��������������Ϣ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50205005( SysSvrServiceParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysSvrServiceParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "deleteAll", context, inputNode, outputNode );
	}
	
	/** ɾ��������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50205006( SysSvrServiceParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysSvrServiceParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_ONE, context, inputNode, outputNode );
	}
	
	/** ��ѯ����������˳��
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50205007( SysSvrServiceParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysSvrServiceParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( SELECT_ORDER_FUNCTION, context, inputNode, outputNode );
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
		SysSvrServiceParamContext appContext = new SysSvrServiceParamContext( context );
		invoke( method, appContext );
	}
}
