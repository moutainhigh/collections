package com.gwssi.dw.metadata.datadict.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[sys_table_semantic]的处理类
 * @author Administrator
 *
 */
public class SysTableSemantic extends BaseTable
{
	public SysTableSemantic()
	{
		
	}
	
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		// 以下是注册用户自定义函数的过程
		// 包括三个参数：SQL语句的名称，类型，描述
		// 业务类可以通过以下函数调用:
		// table.executeFunction( "loadSysTableSemanticList", context, inputNode, outputNode );
		registerSQLFunction( "validateTableCanBeDelete", DaoFunction.SQL_ROWSET, "校验业务表能否被删除（检查其下有无字段）" );
		registerSQLFunction( "validateTablePrimaryKey", DaoFunction.SQL_ROWSET, "校验主键" );
		registerSQLFunction( "validateTableName", DaoFunction.SQL_ROWSET, "校验表名" );
		registerSQLFunction( "validateTableNameCn", DaoFunction.SQL_ROWSET, "校验表中文名" );
	}
	
	/**
	 * 执行SQL语句前的处理
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
	 * 校验主键
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
	 * 校验表名
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
	 * 执行完SQL语句后的处理
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{
		
	}
	
	/**
	 * XXX:用户自定义的SQL语句
	 * 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句
	 * 对于其它的语句，只需要生成一个语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
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

