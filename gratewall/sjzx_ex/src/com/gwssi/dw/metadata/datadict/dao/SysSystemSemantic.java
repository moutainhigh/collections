package com.gwssi.dw.metadata.datadict.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_system_semantic]�Ĵ�����
 * @author Administrator
 *
 */
public class SysSystemSemantic extends BaseTable
{
	public SysSystemSemantic()
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
		// table.executeFunction( "loadSysSystemSemanticList", context, inputNode, outputNode );
		registerSQLFunction( "validateSysCanBeDelete", DaoFunction.SQL_ROWSET, "��֤ϵͳ�ܷ�ɾ��(�鿴������û�б�)" );
		registerSQLFunction( "validateSysPrimaryKey", DaoFunction.SQL_ROWSET, "��֤����" );
		registerSQLFunction( "validateSysName", DaoFunction.SQL_ROWSET, "��֤ϵͳ��" );
		registerSQLFunction( "validateSysSimpleName", DaoFunction.SQL_ROWSET, "��֤ϵͳ���" );		
		registerSQLFunction( "validateSysNo", DaoFunction.SQL_ROWSET, "��֤ϵͳ����" );
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
	
	public SqlStatement validateSysCanBeDelete( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String sys_no = request.getRecord("select-key").getValue("sys_no");
		stmt.addSqlStmt( "select table_no from sys_table_semantic where sys_id = '" + sys_no + "'" );
		stmt.setCountStmt( "select count(*) from sys_table_semantic where sys_id = '" + sys_no + "'" );
		return stmt;
	}
	
	/**
	 * У������
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement validateSysPrimaryKey( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String sys_no = request.getRecord("select-key").getValue("sys_no");
		stmt.addSqlStmt( "select sys_no from sys_system_semantic where sys_no = '" + sys_no + "'");
		stmt.setCountStmt( "select count(*) from sys_system_semantic where sys_no = '" + sys_no + "'");
		return stmt;
	}
	
		
	/**
	 * У�����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement validateSysNo( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String sys_no = request.getRecord("select-key").getValue("sys_no");
		String sys_id = request.getRecord("select-key").getValue("sys_id");
		String type = request.getRecord("select-key").getValue("type");
		String sql = "select sys_no from sys_system_semantic where sys_no = '" + sys_no + "'";
		String countSql = "select count(*) from sys_system_semantic where sys_no = '" + sys_no + "'";
		if(type != null && type.equals("modify"))
		{
			sql += " and sys_id!='"+sys_id+"'";
			countSql += " and sys_id!='"+sys_id+"'";
		}
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}	
	/**
	 * У��ϵͳ��
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement validateSysName( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String sys_name = request.getRecord("select-key").getValue("sys_name");
		String sys_id = request.getRecord("select-key").getValue("sys_id");
		String type = request.getRecord("select-key").getValue("type");
		String sql = "select sys_no from sys_system_semantic where sys_name = '" + sys_name + "'";
		String countSql = "select count(*) from sys_system_semantic where sys_name = '" + sys_name + "'";
		if(type != null && type.equals("modify"))
		{
			sql += " and sys_id!='"+sys_id+"'";
			countSql += " and sys_id!='"+sys_id+"'";
		}
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	
	public SqlStatement validateSysSimpleName( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String sys_simple = request.getRecord("select-key").getValue("sys_simple");
		String sys_id = request.getRecord("select-key").getValue("sys_id");
		String type = request.getRecord("select-key").getValue("type");
		String sql = "select sys_no from sys_system_semantic where sys_simple = '" + sys_simple + "'";
		String countSql = "select count(*) from sys_system_semantic where sys_simple = '" + sys_simple + "'";
		if(type != null && type.equals("modify"))
		{
			sql += " and sys_id!='"+sys_id+"'";
			countSql += " and sys_id!='"+sys_id+"'";
		}
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	
	/**
	 * XXX:�û��Զ����SQL���
	 * ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼���������
	 * ������������䣬ֻ��Ҫ����һ�����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	public SqlStatement loadSysSystemSemanticList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_system_semantic" );
		stmt.setCountStmt( "select count(*) from sys_system_semantic" );
		return stmt;
	}
	 */
	
}

