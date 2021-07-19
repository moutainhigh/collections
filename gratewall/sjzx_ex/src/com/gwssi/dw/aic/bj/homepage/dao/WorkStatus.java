package com.gwssi.dw.aic.bj.homepage.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[work_status]的处理类
 * @author Administrator
 *
 */
public class WorkStatus extends BaseTable
{
	public WorkStatus()
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
		// table.executeFunction( "loadWorkStatusList", context, inputNode, outputNode );
		registerSQLFunction( "getRoleList", DaoFunction.SQL_ROWSET, "获取用户权限列表" );
		registerSQLFunction( "queryTotalData", DaoFunction.SQL_ROWSET, "获取待办任务列表" );
		registerSQLFunction( "queryTotalDataCount", DaoFunction.SQL_ROWSET, "获取待办任务列表个数" );
		registerSQLFunction( "getFuncList", DaoFunction.SQL_ROWSET, "获取功能点列表" );
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
	 * 获取用户权限列表
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getRoleList( TxnContext request, DataBus inputData )
	{
		String roleList = request.getRecord("select-key").getValue("roleList");
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select t.funclist from operrole_new t where t.status='1' and t.roleid in (" + roleList + ")" );
		stmt.setCountStmt( "select count(*) from operrole_new t where t.status='1' and t.roleid in (" + roleList + ")" );
		return stmt;
	}
	
	/**
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getFuncList( TxnContext request, DataBus inputData )
	{
		String funcList = request.getRecord("select-key").getValue("funcList");
		SqlStatement stmt = new SqlStatement( );
		String sql = "select t.txncode from functxn_new t, funcinfo_new i where t.funccode = i.funccode" +
				" and t.status = '1' and i.status = '1' and" +
				" t.funccode in (" + funcList + ")";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( "select count(*) from (" + sql + ")" );
//		System.out.println("查询SQL:" + sql);
		return stmt;
	}
	
	/**
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryTotalData( TxnContext request, DataBus inputData )
	{
		String funclist = request.getRecord("ws-query").getValue("funclist");
		String scour_table_name = request.getRecord("ws-query").getValue("scour_table_name");
		String scour_table_key_col = request.getRecord("ws-query").getValue("scour_table_key_col");
		
		String sql = "select * from work_status t where t.isvalid='1' ";
		if (scour_table_name != null && !scour_table_name.equals("")){
			sql += " and t.scour_table_name = '" + scour_table_name + "'";
		}
		if (scour_table_key_col != null && !scour_table_key_col.equals("")){
			sql += " and t.scour_table_key_col = '" + scour_table_key_col + "'";
		}
		
		sql += " and t.txncode in (" + funclist + ")";
		
		try {
			sql += " and " + getCheckPurvPostfix(request);
		} catch (TxnException e) {
			e.printStackTrace();
			sql += " and 1=2"; 
		}
		
		String countSql = "select count(*) from (" + sql + ")";
		sql += " order by t.initiator_date desc, t.initiator_time desc";
//		System.out.println("sql:" + sql);
//		System.out.println("countSql:" + countSql);
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	
	public SqlStatement queryTotalDataCount( TxnContext request, DataBus inputData )
	{
		String funclist = request.getRecord("ws-query").getValue("funclist");
		String scour_table_name = request.getRecord("ws-query").getValue("scour_table_name");
		String scour_table_key_col = request.getRecord("ws-query").getValue("scour_table_key_col");
		
		String sql = "select count(1) apply_count from work_status t where t.isvalid='1' ";
		if (scour_table_name != null && !scour_table_name.equals("")){
			sql += " and t.scour_table_name = '" + scour_table_name + "'";
		}
		if (scour_table_key_col != null && !scour_table_key_col.equals("")){
			sql += " and t.scour_table_key_col = '" + scour_table_key_col + "'";
		}
		
		sql += " and t.txncode in (" + funclist + ")";
		
		try {
			sql += " and " + getCheckPurvPostfix(request);
		} catch (TxnException e) {
			e.printStackTrace();
			sql += " and 1=2"; 
		}
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( sql );
		return stmt;
	}
	
	public static String getCheckPurvPostfix(TxnContext request) throws TxnException{
		// String result = "(t1.apply_count = '-' or ";
		String result = "(";
		Recordset rs = request.getRecordset("downloadCheckPurv");
		if (rs.size() == 0){
			return " (1 = 2) ";
		}
		for (int i = 0; i < rs.size(); i++){
			if (i != 0){
				result += " or ";
			}
			DataBus db = rs.get(i);
			String min_size = db.getValue("min_size");
			String max_size = db.getValue("max_size");
			String agency_id = db.getValue("agency_id");
			String multi_download_check = db.getValue("multi_download_check");
			result += "(" +
				"t.initiator_agen_id = '" + agency_id + "'" + 
				" and ((t.apply_count >= " + min_size + 
				" and t.apply_count <= " + max_size + 
				" and t.bak_col_1='0')";
			if (multi_download_check != null && multi_download_check.equals("1")){
				result += "or t.bak_col_1='1'";
			}
			result += "))";
		}
		
		result += ")";
//		System.out.println("result:" + result);
		return result;
	}
}

