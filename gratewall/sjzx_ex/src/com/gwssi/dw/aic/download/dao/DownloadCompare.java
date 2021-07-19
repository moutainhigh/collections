package com.gwssi.dw.aic.download.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[download_compare]的处理类
 * @author Administrator
 *
 */
public class DownloadCompare extends BaseTable
{
	public DownloadCompare()
	{
		
	}
	
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		registerSQLFunction( "queryTableIsExists", DaoFunction.SQL_ROWSET, "查询表是否存在" );
		registerSQLFunction( "deleteTempTable", DaoFunction.SQL_DELETE, "删除临时表" );
		registerSQLFunction( "deleteDataInColumnTable", DaoFunction.SQL_DELETE, "删除字段表的数据" );
		registerSQLFunction( "updateRemark", DaoFunction.SQL_UPDATE, "更新备注信息" );
		registerSQLFunction( "queryResult", DaoFunction.SQL_ROWSET, "查询比对下载信息" );
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
	
	public SqlStatement queryTableIsExists( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String table_name = request.getRecord("select-key").getValue("table_name");
		String sql = "select t.object_name from user_objects t where " +
			"t.object_name = '" + table_name.toUpperCase() + "' and rownum <= 1";
		
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( "select count(*) from (" + sql + ")" );
		return stmt;
	}
	
	public SqlStatement deleteTempTable( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String table_name = request.getRecord("select-key").getValue("table_name");
		stmt.addSqlStmt( "drop table " + table_name );
		// stmt.setCountStmt( "select count(*) from download_compare" );
		return stmt;
	}
	
	public SqlStatement queryResult( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String table_name = request.getRecord("select-key").getValue("table_name");
		String create_user = request.getRecord("select-key").getValue("create_user");
		String start_date = request.getRecord("select-key").getValue("start_date");
		String end_date = request.getRecord("select-key").getValue("end_date");
		
		String sql = "select download_compare.download_compare_id, " +
				"download_compare.table_no, download_compare.table_name, " +
				"download_compare.table_name_cn, download_compare.create_user, " +
				"download_compare.create_date, download_compare.create_time, " +
				"download_compare.gen_table_name, download_compare.remark, " +
				"download_compare.download_cn_list, download_compare.download_en_list, " +
				"download_compare.bak1, download_compare.bak2, download_compare.bak3, " +
				"download_compare.bak4, download_compare.bak5, xt_zzjg_yh_new.yhxm " +
				"from download_compare, xt_zzjg_yh_new where " +
				"download_compare.create_user = xt_zzjg_yh_new.yhid_pk ";
		
		if (start_date != null && start_date.length() != 0){
			sql += " and download_compare.create_date >= '" + start_date + "'";
		}
		if (end_date != null && end_date.length() != 0){
			sql += " and download_compare.create_date <= '" + end_date + "'";
		}
		if (table_name != null && table_name.length() != 0){
			sql += " and upper(download_compare.table_name) like '%" + table_name.toUpperCase() + "%'";
		}
		if (create_user != null && create_user.length() != 0){
			sql += " and upper(xt_zzjg_yh_new.yhxm) like '%" + create_user.toUpperCase() + "%'";
		}
		
		String countSql = "select count(*) from (" + sql + ")";
		
		sql += " order by download_compare.create_date desc, download_compare.create_time desc";
		
//		System.out.println("sql:" + sql);
		
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	
	public SqlStatement deleteDataInColumnTable( TxnContext request, DataBus inputData )
	{
		String table_no = request.getRecord("select-key").getValue("table_no");
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "delete from download_column where table_no = '" + table_no + "'" );
		// stmt.setCountStmt( "select count(*) from download_compare" );
		return stmt;
	}
	
	public SqlStatement updateRemark( TxnContext request, DataBus inputData )
	{
		String download_compare_id = request.getRecord("record").getValue("download_compare_id");
		String remark = request.getRecord("record").getValue("remark");
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "update download_compare t set t.remark = '" + remark + 
				"' where t.download_compare_id = '" + download_compare_id + "'" );
		
		System.out.println("update download_compare t set t.remark = '" + remark + 
				"' where t.download_compare_id = '" + download_compare_id + "'" );
		// stmt.setCountStmt( "select count(*) from download_compare" );
		return stmt;
	}
}

