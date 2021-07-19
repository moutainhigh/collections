package com.gwssi.sysmgr.priv.datapriv.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[dataaccgroup]的处理类
 * @author Administrator
 *
 */
public class Dataaccgroup extends BaseTable
{
	public Dataaccgroup()
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
		// table.executeFunction( "loadDataaccgroupList", context, inputNode, outputNode );
		//XXX: 
		registerSQLFunction( "dataaccgroupFpCheck", DaoFunction.SQL_ROWSET, "检测数据权限组是否已经分配" );
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
	public SqlStatement loadDataaccgroupList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from dataaccgroup" );
		stmt.setCountStmt( "select count(*) from dataaccgroup" );
		return stmt;
	}
	 */
	/**
	 * 判断数据权限组是否已经分配，分配后需要删除分配关系后才可删除
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

