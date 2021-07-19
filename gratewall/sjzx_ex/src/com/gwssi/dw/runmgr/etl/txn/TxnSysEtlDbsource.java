package com.gwssi.dw.runmgr.etl.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.gwssi.dw.runmgr.etl.vo.SysEtlDbsourceContext;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

public class TxnSysEtlDbsource extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysEtlDbsource.class, SysEtlDbsourceContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_etl_dbsource";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_etl_dbsource list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_etl_dbsource";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_etl_dbsource";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_etl_dbsource";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_etl_dbsource";
	
	/**
	 * ���캯��
	 */
	public TxnSysEtlDbsource()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ������Դ�����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn501020001( SysEtlDbsourceContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �޸�������Դ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn501020002( SysEtlDbsourceContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysEtlDbsource sys_etl_dbsource = context.getSysEtlDbsource( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����������Դ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn501020003( SysEtlDbsourceContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoSysEtlDbsource sys_etl_dbsource = context.getSysEtlDbsource( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ������Դ���������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn501020004( SysEtlDbsourceContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysEtlDbsourcePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysEtlDbsource result = context.getSysEtlDbsource( outputNode );
	}
	
	/** ɾ��������Դ������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn501020005( SysEtlDbsourceContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysEtlDbsourcePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		SysEtlDbsourceContext appContext = new SysEtlDbsourceContext( context );
		invoke( method, appContext );
	}
}
