package com.gwssi.dw.runmgr.services.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[sys_svr_config_param]的处理类
 * @author Administrator
 *
 */
public class SysSvrConfigParam extends BaseTable
{
	public SysSvrConfigParam()
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
		// table.executeFunction( "loadSysSvrConfigParamList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysSvrConfigParamList", DaoFunction.SQL_ROWSET, "获取服务配置参数列表" );
		registerSQLFunction( "deleteConfigParam", DaoFunction.SQL_DELETE, "删除共享服务配置参数" );
	}
	
	public SqlStatement deleteConfigParam( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String cfgId = dataBus.getValue("sys_svr_config_id");
		String sql =  "DELETE FROM sys_svr_config_param WHERE sys_svr_config_id IN ("+cfgId+")";
		stmt.addSqlStmt(sql);
//		stmt.setCountStmt( "select count(1) from ("+sql+")" );
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
	public SqlStatement loadSysSvrConfigParamList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_svr_config_param" );
		stmt.setCountStmt( "select count(*) from sys_svr_config_param" );
		return stmt;
	}
	 */
	
}

