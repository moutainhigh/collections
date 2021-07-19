package com.gwssi.sysmgr.priv.datapriv.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[dataaccgroupitem]�Ĵ�����
 * @author Administrator
 *
 */
public class Dataaccgroupitem extends BaseTable
{
	public Dataaccgroupitem()
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
		// table.executeFunction( "loadDataaccgroupitemList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadDataaccgroupitemList", DaoFunction.SQL_ROWSET, "��ȡ����Ȩ�޷������б�" );
		registerSQLFunction( "deleteItemByAll", DaoFunction.SQL_DELETE, "���������ֶ�ƥ�䲢ɾ����¼" );
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
	public SqlStatement loadDataaccgroupitemList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from dataaccgroupitem" );
		stmt.setCountStmt( "select count(*) from dataaccgroupitem" );
		return stmt;
	}
	 */
	
	/**
	 * ����SQL���
	 */
	private String generateDeleteSql(TxnContext request) throws TxnException{
		String inputNode = "record";
		String sql = "delete from dataaccgroupitem where dataaccgrpid = " +
		request.getRecord(inputNode).getValue("dataaccgrpid") +
		" and objectid = " + request.getRecord(inputNode).getValue("objectid") +
		" and dataaccid = '" + request.getRecord(inputNode).getValue("dataaccid") + "'";
		
		return sql;
	}
	
	/**
	 * ������������ɾ������Ȩ����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	 * @throws TxnException 
	 */
	public SqlStatement deleteItemByAll( TxnContext request, DataBus inputData ) throws TxnException
	{
		String result = generateDeleteSql(request);
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( result );
		return stmt;
	}
}

