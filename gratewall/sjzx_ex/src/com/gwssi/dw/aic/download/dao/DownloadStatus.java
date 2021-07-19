package com.gwssi.dw.aic.download.dao;

import java.io.UnsupportedEncodingException;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[download_status]的处理类
 * @author Administrator
 *
 */
public class DownloadStatus extends BaseTable
{
	public DownloadStatus()
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
		// table.executeFunction( "loadDownloadStatusList", context, inputNode, outputNode );
		registerSQLFunction( "testSql", DaoFunction.SQL_ROWSET, "测试sql语句是否正确" );
		registerSQLFunction( "downloadStatusApply", DaoFunction.SQL_ROWSET, "申请" );
		registerSQLFunction( "downloadStatusCheck", DaoFunction.SQL_ROWSET, "审核" );
		registerSQLFunction( "updateDownloadStatus", DaoFunction.SQL_UPDATE, "更新审批信息" );
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
	 * 测试SQL语句是否正确
	 * @param request
	 * @param inputData
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public SqlStatement testSql( TxnContext request, DataBus inputData ) throws UnsupportedEncodingException
	{
		String testSql = request.getRecord("select-key").getValue("testsql");
		testSql = java.net.URLDecoder.decode(testSql, "UTF-8");
		SqlStatement stmt = new SqlStatement( );
//		System.out.println("testSql:" + testSql);
//		
//		System.out.println("testCountSql:" + ("select count(*) from (" + testSql + ")"));
		stmt.addSqlStmt( testSql );
		stmt.setCountStmt( "select count(*) from (" + testSql + ")" );
		return stmt;
	}	
	
	/**
	 * 更新审批信息
	 * @param request
	 * @param inputData
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public SqlStatement updateDownloadStatus( TxnContext request, DataBus inputData ) throws UnsupportedEncodingException
	{
		DataBus db = request.getRecord("record");
		String download_status_id = db.getValue("download_status_id");
		String status = db.getValue("status");
		String check_user = db.getValue("check_user");
		String check_date = db.getValue("check_date");
		String check_idea = db.getValue("check_idea");
		
		String sql = "update download_status set check_user = '" + check_user + 
			"', status = '" + status + "', check_date= '" + check_date + "'," +
			" check_idea = '" + check_idea + "' where download_status_id = '" + 
			download_status_id + "'";
		
//		System.out.println("sql:" + sql);
		
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( sql );
//		stmt.setCountStmt( "select count(*) from (" + testSql + ")" );
		return stmt;
	}
	/**
	 * 申请
	 * @param request
	 * @param inputData
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public SqlStatement downloadStatusApply( TxnContext request, DataBus inputData ) throws UnsupportedEncodingException
	{
		DataBus db = request.getRecord("select-key");
		SqlStatement stmt = new SqlStatement( );
		// String sql = "select t1.*, t2.jgid_fk, t2.yhxm from download_status t1, xt_zzjg_yh_new t2 where t1.apply_user = t2.yhid_pk and t1.has_gener = '1'";
		String sql = "select t1.* from download_status t1 where t1.has_gener = '0'";
		String apply_name = db.getValue("apply_name");
		if (apply_name != null && !apply_name.equals("")){
			sql += " and t1.apply_name like '%" + apply_name + "%'";
		}
		String apply_date_start = db.getValue("apply_date_start");
		if (apply_date_start != null && !apply_date_start.equals("")){
			sql += " and t1.apply_date >= '" + apply_date_start + "'";
		}
		String apply_date_end = db.getValue("apply_date_end");
		if (apply_date_end != null && !apply_date_end.equals("")){
			sql += " and t1.apply_date <= '" + apply_date_end + "'";
		}
		String apply_user = db.getValue("apply_user");
		if (apply_user != null && !apply_user.equals("")){
			sql += " and t1.apply_user = '" + apply_user + "'";
		}
		String status = db.getValue("status");
		if (status != null && !status.equals("")){
			sql += " and t1.status = '" + status + "'";
		}
		
		// 添加排序
		sql += " order by t1.apply_date desc, t1.status";
		System.out.println(sql);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( "select count(*) from (" + sql + ")" );
		return stmt;
	}
	
	/**
	 * 审核
	 * @param request
	 * @param inputData
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public SqlStatement downloadStatusCheck( TxnContext request, DataBus inputData ) throws UnsupportedEncodingException
	{
		DataBus db = request.getRecord("select-key");
		SqlStatement stmt = new SqlStatement( );
		String sql = "select t1.*, t2.jgid_fk, t2.yhxm from download_status t1, xt_zzjg_yh_new t2 where t1.apply_user = t2.yhid_pk and t1.status != '5'";
		
		String apply_name = db.getValue("apply_name");
		if (apply_name != null && !apply_name.equals("")){
			sql += " and t1.apply_name like '%" + apply_name + "%'";
		}
		String apply_date_start = db.getValue("apply_date_start");
		if (apply_date_start != null && !apply_date_start.equals("")){
			sql += " and t1.apply_date >= '" + apply_date_start + "'";
		}
		String apply_date_end = db.getValue("apply_date_end");
		if (apply_date_end != null && !apply_date_end.equals("")){
			sql += " and t1.apply_date <= '" + apply_date_end + "'";
		}
		String jgid_fk = db.getValue("jgid_fk");
		if (jgid_fk != null && !jgid_fk.equals("")){
			sql += " and t2.jgid_fk = '" + jgid_fk + "'";
		}
		String status = db.getValue("status");
		if (status != null && !status.equals("")){
			sql += " and t1.status = '" + status + "'";
		}
		
		try {
			sql += " and " + getCheckPurvPostfix(request);
		} catch (TxnException e) {
			e.printStackTrace();
		}
		
		// 添加排序
		sql += " order by t1.apply_date desc, t1.status desc";
		//System.out.println(sql);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( "select count(*) from (" + sql + ")" );
		return stmt;
	}
	
	public static String getCheckPurvPostfix(TxnContext request) throws TxnException{
		// String result = "(t1.apply_count = '-' or ";
		String result = "(";
		Recordset rs = request.getRecordset("downloadCheckPurv");
		if (rs.size() == 0){
			return " 1 = 2 ";
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
				"t2.jgid_fk = '" + agency_id + "'" + 
				" and ((t1.apply_count >= " + min_size + 
				" and t1.apply_count <= " + max_size + 
				" and t1.is_mutil_download='0')";
			if (multi_download_check != null && multi_download_check.equals("1")){
				result += "or t1.is_mutil_download='1'";
			}
			result += "))";
		}
		
		result += ")";
//		System.out.println("result:" + result);
		return result;
	}
	
	public static String addParameter(String sql, String condition){
		if(sql.indexOf(" where ") >= 0){
			sql += " and " + condition; 
		}else{
			sql += " where " + condition;
		}
		return sql;
	}
}

