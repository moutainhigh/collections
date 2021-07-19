package com.gwssi.sysmgr.download.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[download_purv]的处理类
 * @author Administrator
 *
 */
public class DownloadPurv extends BaseTable
{
	public DownloadPurv()
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
		// table.executeFunction( "loadDownloadPurvList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadDownloadPurvList", DaoFunction.SQL_ROWSET, "获取下载设置列表" );
		registerSQLFunction( "getOrgName", DaoFunction.SQL_ROWSET, "获取机构名称" );
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
	 * 获取机构名称
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
	 * XXX:用户自定义的SQL语句
	 * 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句
	 * 对于其它的语句，只需要生成一个语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
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

