package com.gwssi.sysmgr.priv.datapriv.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[dataobject]�Ĵ�����
 * @author Administrator
 *
 */
public class DataObject extends BaseTable
{
	public DataObject()
	{
		
	}
	
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		// ������ע���û��Զ��庯���Ĺ���
		// ��������������SQL�������ƣ����ͣ�����
		// ҵ�������ͨ�����º�������:
		// table.executeFunction( "loadDataObjectList", context, inputNode, outputNode );
		//XXX: 
		registerSQLFunction( "loadDataObjectById", DaoFunction.SQL_ROWSET, "����id��ѯ����" );
	}
	
	/**
	 * ִ��SQL���ǰ�Ĵ���
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{
		
	}
	
	/**
	 * ִ����SQL����Ĵ���
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{
		
	}
	
	/**
	 * XXX:�û��Զ����SQL���
	 * ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼���������
	 * ������������䣬ֻ��Ҫ����һ�����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	public SqlStatement loadDataObjectList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from dataobject" );
		stmt.setCountStmt( "select count(*) from dataobject" );
		return stmt;
	}
	 * @throws TxnException 
	 */
	
	public SqlStatement loadDataObjectById( TxnContext request, DataBus inputData ) throws TxnException
	{
		String objectid = inputData.getString("objectid");
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select objectsource from dataobject where objectid="+objectid );
		stmt.setCountStmt( "select count(objectsource) from dataobject where objectid="+objectid );
		return stmt;
	}
	
}

