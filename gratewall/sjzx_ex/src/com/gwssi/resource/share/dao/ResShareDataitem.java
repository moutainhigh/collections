package com.gwssi.resource.share.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[res_share_dataitem]的处理类
 * @author Administrator
 *
 */
public class ResShareDataitem extends BaseTable
{
	public ResShareDataitem()
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
		// table.executeFunction( "loadResShareDataitemList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadResShareDataitemList", DaoFunction.SQL_ROWSET, "获取共享数据项信息表列表" );
		registerSQLFunction( "queryCodeMusterById", DaoFunction.SQL_ROWSET, "查询代码集信息表" );

	}
	
	
	public SqlStatement queryCodeMusterById(TxnContext request,
			DataBus inputData) throws TxnException
	{
		DataBus db = request.getRecord("select-key");
		String code_table = db.getValue("code_table");
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql.append(" select g.jcsjfx_dm,g.jcsjfx_mc from gz_dm_jcdm t,gz_dm_jcdm_fx g where ")
				.append("t.jc_dm_id=g.jc_dm_id and t.jc_dm_dm='");
		sql.append(code_table);
		sql.append("' order by g.jcsjfx_dm");
		stmt.addSqlStmt(sql.toString());
		/*stmt.setCountStmt(sql.toString());*/
		stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );

//		System.out.println("---------------"+sql.toString());
		return stmt;
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
	public SqlStatement loadResShareDataitemList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from res_share_dataitem" );
		stmt.setCountStmt( "select count(*) from res_share_dataitem" );
		return stmt;
	}
	 */
	
}

