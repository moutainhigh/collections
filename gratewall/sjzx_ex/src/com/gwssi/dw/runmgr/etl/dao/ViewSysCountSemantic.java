package com.gwssi.dw.runmgr.etl.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[view_sys_count_semantic]的处理类
 * @author Administrator
 *
 */
public class ViewSysCountSemantic extends BaseTable
{
	public ViewSysCountSemantic()
	{
		
	}
	
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		this.registerSQLFunction("queryCountList", DaoFunction.SQL_ROWSET, "获取日增量查询列表");
		this.registerSQLFunction("queryHisCountList", DaoFunction.SQL_ROWSET, "获取历史增量查询列表");
		this.registerSQLFunction("queryIncreValue", DaoFunction.SQL_ROWSET, "获取数据表预警信息列表");
		this.registerSQLFunction("querySysCountIncre", DaoFunction.SQL_ROWSET, "获取数据表预警信息列表");
	}
	
	public SqlStatement queryCountList(TxnContext request, DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String sysName = request.getRecord("select-key").getValue("sys_name");
		String tableNameCn = request.getRecord("select-key").getValue("table_name_cn");
		String countDate = request.getRecord("select-key").getValue("count_date");
		String classState = request.getRecord("select-key").getValue("class_state");
		StringBuffer sql = new StringBuffer("SELECT V.TABLE_CLASS_ID,V.SYS_NAME,TABLE_NAME_CN,V.TABLE_NAME,V.COUNT_DATE,V.CLASS_SORT,V.CLASS_STATE,V.COUNT_INCRE,V.COUNT_FULL,I.MIN_VALUE,I.MAX_VALUE,I.IN_OUT_SIGN FROM VIEW_SYS_COUNT_LOG V LEFT JOIN sys_incre_value_semantic I ON V.TABLE_CLASS_ID=I.TABLE_CLASS_ID WHERE 1=1");
		if(sysName != null && !sysName.trim().equals("")){
			sql.append(" AND V.SYS_NAME='" + sysName + "'");
		}
		if(tableNameCn != null && !tableNameCn.trim().equals("")){
			sql.append(" AND V.TABLE_NAME_CN='" + tableNameCn + "'");
		}
		if(classState != null && !classState.trim().equals("")){
			sql.append(" AND V.CLASS_STATE='" + classState + "'");
		}
		
		sql.append(" AND V.COUNT_DATE='" + countDate + "' ORDER BY V.SYS_ORDER, V.TABLE_ORDER, V.SORT_ORDER, V.STATE_ORDER ");
		System.out.println("SQL:  "+sql);
		stmt.addSqlStmt(sql.toString());
		String countSql = "select count(1) from (" + sql + ") t";
		stmt.setCountStmt(countSql);

		return stmt;
	}
	
	
	public SqlStatement queryHisCountList(TxnContext request, DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String sysName = request.getRecord("select-key").getValue("sys_name");
		String tableNameCn = request.getRecord("select-key").getValue("table_name_cn");
		String from = request.getRecord("select-key").getValue("count_year_from");
		String to = request.getRecord("select-key").getValue("count_year_to");
		String classState = request.getRecord("select-key").getValue("class_state");
		StringBuffer sql = new StringBuffer("SELECT V.TABLE_CLASS_ID,V.SYS_NAME,V.TABLE_NAME_CN,V.TABLE_NAME,V.COUNT_DATE,V.CLASS_SORT,V.CLASS_STATE,V.COUNT_INCRE,V.COUNT_FULL,I.MIN_VALUE,I.MAX_VALUE FROM VIEW_SYS_COUNT_SEMANTIC V LEFT JOIN sys_incre_value_semantic I ON V.TABLE_CLASS_ID=I.TABLE_CLASS_ID WHERE 1=1");
		if(sysName != null && !sysName.trim().equals("")){
			sql.append(" AND V.SYS_NAME='" + sysName + "'");
		}
		if(tableNameCn != null && !tableNameCn.trim().equals("")){
			sql.append(" AND V.TABLE_NAME_CN='" + tableNameCn + "'");
		}
		if(classState != null && !classState.trim().equals("")){
			sql.append(" AND V.CLASS_STATE='" + classState + "'");
		}
		
		sql.append(" AND V.COUNT_DATE>='").append(from).append("' AND V.COUNT_DATE<='").append(to).append("' ORDER BY V.SYS_ORDER, V.TABLE_ORDER, V.SORT_ORDER, V.STATE_ORDER ");
		
		//System.out.println("SQL:  "+sql);
		stmt.addSqlStmt(sql.toString());
		String countSql = "select count(1) from (" + sql + ") t";
		stmt.setCountStmt(countSql);

		return stmt;
	}
	
	public SqlStatement queryIncreValue(TxnContext request, DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String sysName = request.getRecord("select-key").getValue("sys_name");
		String tableNameCn = request.getRecord("select-key").getValue("table_name_cn");
		StringBuffer sql = new StringBuffer("SELECT distinct(V.TABLE_NAME_CN),V.TABLE_CLASS_ID,V.TABLE_NAME,V.CLASS_SORT,V.CLASS_STATE,I.VALUE_ID,I.MIN_VALUE,I.MAX_VALUE FROM VIEW_SYS_COUNT_SEMANTIC V LEFT JOIN sys_incre_value_semantic I ON V.TABLE_CLASS_ID=I.TABLE_CLASS_ID WHERE 1=1");
		if(sysName != null && !sysName.trim().equals("")){
			sql.append(" AND V.SYS_NAME='" + sysName + "'");
		}
		if(tableNameCn != null && !tableNameCn.trim().equals("")){
			sql.append(" AND V.TABLE_NAME_CN='" + tableNameCn + "'");
		}
		
		sql.append(" ORDER BY V.TABLE_NAME_CN ");
		
		//System.out.println("SQL:  "+sql);
		stmt.addSqlStmt(sql.toString());
		String countSql = "select count(1) from (" + sql + ") t";
		stmt.setCountStmt(countSql);

		return stmt;
	}
	
	public SqlStatement querySysCountIncre(TxnContext request, DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String id = request.getRecord("primary-key").getValue("table_class_id");
		StringBuffer sql = new StringBuffer("SELECT V.TABLE_NAME_CN,V.TABLE_NAME,V.TABLE_CLASS_ID,V.CLASS_STATE,I.VALUE_ID,I.MIN_VALUE,I.MAX_VALUE FROM VIEW_SYS_COUNT_SEMANTIC V LEFT JOIN sys_incre_value_semantic I ON V.TABLE_CLASS_ID=I.TABLE_CLASS_ID WHERE V.TABLE_CLASS_ID='").append(id).append("'");
		
		//System.out.println("SQL:  "+sql);
		stmt.addSqlStmt(sql.toString());
		String countSql = "select count(1) from (" + sql + ") t";
		stmt.setCountStmt(countSql);

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
	public SqlStatement loadViewSysCountSemanticList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from view_sys_count_semantic" );
		stmt.setCountStmt( "select count(*) from view_sys_count_semantic" );
		return stmt;
	}
	 */
	
}

