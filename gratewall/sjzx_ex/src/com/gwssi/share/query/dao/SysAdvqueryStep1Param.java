package com.gwssi.share.query.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_advquery_step1_param]�Ĵ�����
 * @author Administrator
 *
 */
public class SysAdvqueryStep1Param extends BaseTable
{
	public SysAdvqueryStep1Param()
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
		// table.executeFunction( "loadSysAdvqueryStep1ParamList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysAdvqueryStep1ParamList", DaoFunction.SQL_ROWSET, "��ȡ�Զ����ѯ����һ�б�" );
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
	public SqlStatement loadSysAdvqueryStep1ParamList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_advquery_step1_param" );
		stmt.setCountStmt( "select count(*) from sys_advquery_step1_param" );
		return stmt;
	}
	 */
	
}

