package com.gwssi.sysmgr.priv.datapriv.txn;

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

import com.gwssi.sysmgr.priv.datapriv.vo.DataaccgroupitemContext;

public class TxnDataaccgroupitem extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnDataaccgroupitem.class, DataaccgroupitemContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "dataaccgroupitem";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select dataaccgroupitem list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "${mod.function.select}";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "${mod.function.update}";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one dataaccgroupitem";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one dataaccgroupitem";
	
	/**
	 * ���캯��
	 */
	public TxnDataaccgroupitem()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ����Ȩ�޷������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103061( DataaccgroupitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDataaccgroupitemSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoDataaccgroupitem result[] = context.getDataaccgroupitems( outputNode );
	}
	
	/** �޸�����Ȩ�޷�������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103062( DataaccgroupitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoDataaccgroupitem dataaccgroupitem = context.getDataaccgroupitem( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��������Ȩ�޷�������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103063( DataaccgroupitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoDataaccgroupitem dataaccgroupitem = context.getDataaccgroupitem( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ����Ȩ�޷����������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103064( DataaccgroupitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDataaccgroupitemPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoDataaccgroupitem result = context.getDataaccgroupitem( outputNode );
	}
	
	/** ɾ������Ȩ�޷�������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103065( DataaccgroupitemContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoDataaccgroupitemPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	public void txn103066( DataaccgroupitemContext context ) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoDataaccgroupitemPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "deleteItemByAll", context, inputNode, outputNode );
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
		DataaccgroupitemContext appContext = new DataaccgroupitemContext( context );
		invoke( method, appContext );
	}
}
