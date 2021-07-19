package com.gwssi.resource.share.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[res_share_table]的处理类
 * @author Administrator
 *
 */
public class ResShareTable extends BaseTable
{
	public ResShareTable()
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
		// table.executeFunction( "loadResShareTableList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadResShareTableList", DaoFunction.SQL_ROWSET, "获取共享表信息表列表" );
		registerSQLFunction( "quertShareTableByTopic", DaoFunction.SQL_ROWSET, "分主题查询共享表信息表" );
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
	
	
	public SqlStatement quertShareTableByTopic( TxnContext request, DataBus inputData )
	{
		DataBus db = request.getRecord("select-key");
		String business_topics_id = db.getValue("business_topics_id");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from res_share_table t where t.business_topics_id='");
		sql.append(business_topics_id);
		sql.append("'");
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( sql.toString() );
		stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );
		return stmt;
	}
	/**
	 * XXX:用户自定义的SQL语句
	 * 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句
	 * 对于其它的语句，只需要生成一个语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	public SqlStatement loadResShareTableList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from res_share_table" );
		stmt.setCountStmt( "select count(*) from res_share_table" );
		return stmt;
	}
	 */
	
}

