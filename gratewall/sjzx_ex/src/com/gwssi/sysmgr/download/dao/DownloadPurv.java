package com.gwssi.sysmgr.download.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[download_purv]�Ĵ�����
 * @author Administrator
 *
 */
public class DownloadPurv extends BaseTable
{
	public DownloadPurv()
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
		// table.executeFunction( "loadDownloadPurvList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadDownloadPurvList", DaoFunction.SQL_ROWSET, "��ȡ���������б�" );
		registerSQLFunction( "getOrgName", DaoFunction.SQL_ROWSET, "��ȡ��������" );
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
	 * ��ȡ��������
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getOrgName( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String jgid_pk = request.getRecord("select-key").getValue("jgid_pk");
		String sql = "select jgmc from XT_ZZJG_JG where jgid_pk='" + jgid_pk + "'";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( "select count(1) from (" + sql + ")" );
		return stmt;
	}
	/**
	 * XXX:�û��Զ����SQL���
	 * ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼���������
	 * ������������䣬ֻ��Ҫ����һ�����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	public SqlStatement loadDownloadPurvList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from download_purv" );
		stmt.setCountStmt( "select count(*) from download_purv" );
		return stmt;
	}
	 */
	
}

