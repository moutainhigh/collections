package com.gwssi.dw.runmgr.etl.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_etl_dbsource]�Ĵ�����
 * @author Administrator
 *
 */
public class SysEtlDbsource extends BaseTable
{
	public SysEtlDbsource()
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
		// table.executeFunction( "loadSysEtlDbsourceList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysEtlDbsourceList", DaoFunction.SQL_ROWSET, "��ȡ������Դ�����б�" );
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
	public SqlStatement loadSysEtlDbsourceList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_etl_dbsource" );
		stmt.setCountStmt( "select count(*) from sys_etl_dbsource" );
		return stmt;
	}
	 */
	
}

