package com.gwssi.dw.metadata.datadict.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_table_semantic]�Ĵ�����
 * @author Administrator
 *
 */
public class SysTableSemantic extends BaseTable
{
	public SysTableSemantic()
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
		// table.executeFunction( "loadSysTableSemanticList", context, inputNode, outputNode );
		registerSQLFunction( "validateTableCanBeDelete", DaoFunction.SQL_ROWSET, "У��ҵ����ܷ�ɾ����������������ֶΣ�" );
		registerSQLFunction( "validateTablePrimaryKey", DaoFunction.SQL_ROWSET, "У������" );
		registerSQLFunction( "validateTableName", DaoFunction.SQL_ROWSET, "У�����" );
		registerSQLFunction( "validateTableNameCn", DaoFunction.SQL_ROWSET, "У���������" );
	}
	
	/**
	 * ִ��SQL���ǰ�Ĵ���
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{
		
	}
	
	public SqlStatement validateTableCanBeDelete( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String table_no = request.getRecord("select-key").getValue("table_no");
		stmt.addSqlStmt( "select column_no from sys_column_semantic where table_no = '" + table_no + "'" );
		stmt.setCountStmt( "select count(*) from sys_column_semantic where table_no = '" + table_no + "'" );
		return stmt;
	}
	
	/**
	 * У������
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement validateTablePrimaryKey( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String table_no = request.getRecord("select-key").getValue("table_no");
		stmt.addSqlStmt( "select table_no from sys_table_semantic where table_no = '" + table_no + "'");
		stmt.setCountStmt( "select count(*) from sys_table_semantic where table_no = '" + table_no + "'");
		return stmt;
	}
	
	/**
	 * У�����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement validateTableName( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String sys_id = request.getRecord("select-key").getValue("sys_id");
		String table_no = request.getRecord("select-key").getValue("table_no");
		String table_name = request.getRecord("select-key").getValue("table_name");
		String type = request.getRecord("select-key").getValue("type");
		String sql = "select table_name from sys_table_semantic where table_name = '" 
				+ table_name + "' and sys_id='" + sys_id + "'";
		String countSql = "select count(*) from sys_table_semantic where table_name = '" 
				+ table_name + "' and sys_id='" + sys_id + "'";
		if (type!=null && type.equals("modify"))
		{
			sql += " and table_no !='"+table_no+"'";
			countSql += " and table_no !='"+table_no+"'";
		}
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	
	/**
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement validateTableNameCn( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String sys_id = request.getRecord("select-key").getValue("sys_id");
		String table_no = request.getRecord("select-key").getValue("table_no");
		String table_name_cn = request.getRecord("select-key").getValue("table_name_cn");
		String type = request.getRecord("select-key").getValue("type");
		String sql = "select table_name_cn from sys_table_semantic where table_name_cn = '" 
				+ table_name_cn + "' and sys_id='" + sys_id + "'";
		String countSql = "select count(*) from sys_table_semantic where table_name_cn = '" 
				+ table_name_cn + "' and sys_id='" + sys_id + "'";
		if (type!=null && type.equals("modify"))
		{
			sql += " and table_no !='"+table_no+"'";
			countSql += " and table_no !='"+table_no+"'";
		}
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
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
	public SqlStatement loadSysTableSemanticList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_table_semantic" );
		stmt.setCountStmt( "select count(*) from sys_table_semantic" );
		return stmt;
	}
	 */
	
}

