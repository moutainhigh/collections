package com.gwssi.sysmgr.priv.datapriv.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[dataaccgroup]�Ĵ�����
 * @author Administrator
 *
 */
public class Dataaccgroup extends BaseTable
{
	public Dataaccgroup()
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
		// table.executeFunction( "loadDataaccgroupList", context, inputNode, outputNode );
		//XXX: 
		registerSQLFunction( "dataaccgroupFpCheck", DaoFunction.SQL_ROWSET, "�������Ȩ�����Ƿ��Ѿ�����" );
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
	public SqlStatement loadDataaccgroupList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from dataaccgroup" );
		stmt.setCountStmt( "select count(*) from dataaccgroup" );
		return stmt;
	}
	 */
	/**
	 * �ж�����Ȩ�����Ƿ��Ѿ����䣬�������Ҫɾ�������ϵ��ſ�ɾ��
	 */
	public SqlStatement dataaccgroupFpCheck( TxnContext request, DataBus inputData ) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String dataaccgrpid = inputData.getString("dataaccgrpid");
		stmt.addSqlStmt( "select OBJECTID from DATAACCDISP where dataaccgrpid="+dataaccgrpid );
		System.out.println("select OBJECTID from DATAACCDISP where dataaccgrpid="+dataaccgrpid );
		stmt.setCountStmt( "select count(OBJECTID) from DATAACCDISP where dataaccgrpid="+dataaccgrpid );
		return stmt;
	}
	
}

