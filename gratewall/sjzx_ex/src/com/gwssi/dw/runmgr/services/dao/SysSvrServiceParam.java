package com.gwssi.dw.runmgr.services.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[sys_svr_service_param]的处理类
 * @author Administrator
 *
 */
public class SysSvrServiceParam extends BaseTable
{
	public SysSvrServiceParam()
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
		// table.executeFunction( "loadSysSvrServiceParamList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysSvrServiceParamList", DaoFunction.SQL_ROWSET, "获取共享服务参数列表" );
		registerSQLFunction( "deleteAll", DaoFunction.SQL_DELETE, "删除所有服务参数" );
	}
	
	public SqlStatement deleteAll(TxnContext request, DataBus inputData){
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("record");
		String sys_svr_service_id = dataBus.getValue("sys_svr_service_id");
		StringBuffer newId = new StringBuffer();
		if(sys_svr_service_id != null && !sys_svr_service_id.equals("")){
			String[] ids = sys_svr_service_id.split(",");
			for(int i = 0; i < ids.length; i++){
				newId.append("'").append(ids[i]).append("'");
				if(i != ids.length - 1){
					newId.append(", ");
				}
			}
			
		}
		stmt.addSqlStmt( "DELETE FROM sys_svr_service_param where SYS_SVR_SERVICE_ID IN (" + newId + ")" );
		//stmt.setCountStmt( "select count(SYS_SVR_SERVICE_ID) from SYS_SVR_CONFIG where SYS_SVR_SERVICE_ID IN (" + newId + ")" );
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
	public SqlStatement loadSysSvrServiceParamList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_svr_service_param" );
		stmt.setCountStmt( "select count(*) from sys_svr_service_param" );
		return stmt;
	}
	 */
	
}

