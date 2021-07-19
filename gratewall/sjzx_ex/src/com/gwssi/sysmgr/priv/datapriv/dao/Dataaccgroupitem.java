package com.gwssi.sysmgr.priv.datapriv.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[dataaccgroupitem]的处理类
 * @author Administrator
 *
 */
public class Dataaccgroupitem extends BaseTable
{
	public Dataaccgroupitem()
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
		// table.executeFunction( "loadDataaccgroupitemList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadDataaccgroupitemList", DaoFunction.SQL_ROWSET, "获取数据权限分组项列表" );
		registerSQLFunction( "deleteItemByAll", DaoFunction.SQL_DELETE, "根据所有字段匹配并删除记录" );
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
	
	/**
	 * XXX:用户自定义的SQL语句
	 * 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句
	 * 对于其它的语句，只需要生成一个语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
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
	 * 生成SQL语句
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
	 * 根据所有条件删除数据权限项
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
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

