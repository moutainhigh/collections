package com.gwssi.dw.metadata.datadict.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[sys_system_semantic]的处理类
 * @author Administrator
 *
 */
public class SysSystemSemantic extends BaseTable
{
	public SysSystemSemantic()
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
		// table.executeFunction( "loadSysSystemSemanticList", context, inputNode, outputNode );
		registerSQLFunction( "validateSysCanBeDelete", DaoFunction.SQL_ROWSET, "验证系统能否被删除(查看其下有没有表)" );
		registerSQLFunction( "validateSysPrimaryKey", DaoFunction.SQL_ROWSET, "验证主键" );
		registerSQLFunction( "validateSysName", DaoFunction.SQL_ROWSET, "验证系统名" );
		registerSQLFunction( "validateSysSimpleName", DaoFunction.SQL_ROWSET, "验证系统简称" );		
		registerSQLFunction( "validateSysNo", DaoFunction.SQL_ROWSET, "验证系统代码" );
	}
	
	/**
	 * 执行SQL语句前的处理
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{
		
	}
	
	/**
	 * 执行完SQL语句后的处理
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
	 * 校验主键
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
	 * 校验代码
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
	 * 校验系统名
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
	 * XXX:用户自定义的SQL语句
	 * 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句
	 * 对于其它的语句，只需要生成一个语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
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

