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
import com.gwssi.dw.runmgr.services.vo.SysSvrConfigParamContext;

public class TxnSysSvrConfigParam extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysSvrConfigParam.class, SysSvrConfigParamContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_svr_config_param";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_svr_config_param list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_svr_config_param";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_svr_config_param";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_svr_config_param";
	
	// ɾ����¼
	private static final String DELETE_LIST_FUNCTION = "delete config sys_svr_config_param";
	
	/**
	 * ���캯��
	 */
	public TxnSysSvrConfigParam()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�������ò����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50206001( SysSvrConfigParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		// ��ѯ��¼������ VoSysSvrConfigParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysSvrConfigParam result[] = context.getSysSvrConfigParams( outputNode );
	}
	
	/** �޸ķ������ò�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50206002( SysSvrConfigParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysSvrConfigParam sys_svr_config_param = context.getSysSvrConfigParam( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӷ������ò�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50206003( SysSvrConfigParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysSvrConfigParam sys_svr_config_param = context.getSysSvrConfigParam( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ�������ò��������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50206004( SysSvrConfigParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysSvrConfigParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysSvrConfigParam result = context.getSysSvrConfigParam( outputNode );
	}
	
	/** ɾ�������������в�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50206005( SysSvrConfigParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysSvrConfigParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		
	}
	
	/** ɾ�������������в�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50206006( SysSvrConfigParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysSvrConfigParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "deleteConfigParam", context, inputNode, outputNode );
		
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
		SysSvrConfigParamContext appContext = new SysSvrConfigParamContext( context );
		invoke( method, appContext );
	}
}
