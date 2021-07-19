package com.gwssi.share.query.dao;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[sys_advanced_query]的处理类
 * 
 * @author Administrator
 * 
 */
public class SysAdvancedQuery extends BaseTable
{
	public SysAdvancedQuery()
	{

	}

	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register()
	{
		registerSQLFunction("queryList", DaoFunction.SQL_ROWSET, "查询列表");
		registerSQLFunction("getFirstLevelNode", DaoFunction.SQL_ROWSET,
				"读取第一层节点");
		registerSQLFunction("getAllTables", DaoFunction.SQL_ROWSET,
				"查询某主题下所有数据表");
		registerSQLFunction("getAllColumns", DaoFunction.SQL_ROWSET,
				"查询数据表下所有字段");
		registerSQLFunction("getAllColumnsCompare", DaoFunction.SQL_ROWSET,
				"查询数据表下所有字段(比对下载)");
		registerSQLFunction("queryTable", DaoFunction.SQL_ROWSET, "查询数据表");
		registerSQLFunction("queryColumn", DaoFunction.SQL_ROWSET, "查询字段");
		registerSQLFunction("getJCDMFX", DaoFunction.SQL_ROWSET, "查询基础代码");
		registerSQLFunction("copyOrigin", DaoFunction.SQL_ROWSET, "查询基础代码");
	}

	public SqlStatement copyOrigin(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		// stmt.addSqlStmt( "select DCZD_DM, DCZD_MC, DCZD_FJD, DCZD_MX,DCZD_SX
		// from gz_zb_dczd where dczd_fjd is null or dczd_fjd ='' order by
		// dczd_sx" );
		// stmt.setCountStmt( "select count(*) from gz_zb_dczd where dczd_fjd is
		// null or dczd_fjd =''" );
		stmt.addSqlStmt("SELECT * FROM sys_advanced_query");
		stmt.setCountStmt("SELECT COUNT(1) FROM sys_advanced_query");
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
	 * 获取第一层节点
	 * 
	 * @param request
	 *            交易的上下文
	 * @param inputData
	 *            生成语句的输入节点
	 * @return
	 */
	public SqlStatement getFirstLevelNode(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String dbName = request.getRecord("select-key").getValue("db_name");
		if (StringUtils.isBlank(dbName)) {
			dbName = "gwssi";
		}
		String sql="SELECT sys_no sys_id, sys_no, sys_name, sort FROM sys_rd_system WHERE isshow='1' and DB_NAME='"
			+ dbName + "' ORDER BY SORT";
		stmt.addSqlStmt(sql);
		stmt.setCountStmt("SELECT COUNT(1) FROM ("+sql+")");
		return stmt;
	}

	public SqlStatement getAllTables(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String sys_id = dataBus.getValue("sys_id");
		String downloadflag = dataBus.getValue("downloadflag");
		String queryflag = dataBus.getValue("queryflag");
		String transflag = dataBus.getValue("transflag");
		String sql = "select st.table_no, st.table_code TABLE_NAME, nvl(st.table_name,st.table_code) TABLE_NAME_CN,"
			+"st.sort TABLE_ORDER,st.sys_rd_system_id SYS_ID,st.sys_name,st.sys_no "+
			"from sys_rd_table st where st.sys_rd_system_id = '" + sys_id + "' ";

		if (StringUtils.isNotBlank(downloadflag)) {
			sql += " and st.is_download = '"+downloadflag+"' ";
		}
		if (StringUtils.isNotBlank(queryflag)) {
			sql += " and st.is_query = '"+queryflag+"' ";
		}
		if (StringUtils.isNotBlank(transflag)) {
			sql += " and st.is_trans = '"+transflag+"' ";
		}
       
		sql += " order by sort,table_code";
        //System.out.println("-----table----"+sql);
		stmt.addSqlStmt(sql);
		stmt.setCountStmt("select count(*) from (" + sql + ")");
		return stmt;
	}

	public SqlStatement getAllColumnsCompare(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String table_no = dataBus.getValue("table_no");
		stmt.setPageRows(-1);
		String sql = "select * from(select c.COLUMN_NO,c.COLUMN_CODE COLUMN_NAME,c.COLUMN_NAME COLUMN_NAME_CN," +
		"C.Alia_Name COLUMN_BYNAME,c.sys_column_type EDIT_TYPE,c.column_codeindex DEMO," +
		"c.TABLE_NO,t.table_code table_name,t.table_name table_name_cn from sys_rd_column c,"+
		"sys_rd_table t where c.sys_rd_table_id=t.sys_rd_table_id and c.use_type=0 " +
		"and c.table_no='"+table_no+"' order by to_number(c.sort))" + " UNION ALL "
				+ "select c.COLUMN_NO, c.COLUMN_NAME, c.COLUMN_NAME_CN, "
				+ "C.COLUMN_BYNAME, c.EDIT_TYPE, c.DEMO, c.TABLE_NO, "
				+ "t.table_name, t.table_name_cn from download_column c, "
				+ "download_compare t where c.table_no = '" + table_no
				+ "' AND " + "t.TABLE_NO = c.table_no";
		//System.out.println("-----比对下载-----"+sql);
		String countSql = "select count(*) from (" + sql + ")";
		//sql = "SELECT * from (" + sql + ") order by COLUMN_NAME_CN ";
		stmt.addSqlStmt(sql);
		stmt.setCountStmt(countSql);
		return stmt;
	}

	public SqlStatement getAllColumns(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String table_no = dataBus.getValue("table_no");
		String downloadflag = dataBus.getValue("downloadflag");
		String queryflag = dataBus.getValue("queryflag");
		String transflag = dataBus.getValue("transflag");
		stmt.setPageRows(-1);
		String sql="select c.COLUMN_NO,c.COLUMN_CODE COLUMN_NAME,c.COLUMN_NAME COLUMN_NAME_CN," +
		"C.Alia_Name COLUMN_BYNAME,c.sys_column_type EDIT_TYPE,c.column_codeindex DEMO," +
		"c.TABLE_NO,t.table_code table_name,t.table_name table_name_cn from sys_rd_column c,"+
		"sys_rd_table t where c.sys_rd_table_id=t.sys_rd_table_id and c.use_type=0 " +
		" and c.column_name is not null and c.table_no='"+table_no+"' order by to_number(c.sort),c.column_code";
		//System.out.println("------getAllColumns---"+sql);
		stmt.addSqlStmt(sql);
		stmt.setCountStmt("select count(*) from sys_rd_column where TABLE_NO = '"
						+ table_no + "'");
		return stmt;
	}

	public SqlStatement queryTable(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String table_no = dataBus.getValue("table_no");
		stmt
				.addSqlStmt("select t.TABLE_NO, t.TABLE_CODE AS TABLE_NAME , t.TABLE_NAME as TABLE_NAME_CN, t.sys_rd_system_id as sys_id, s.SYS_NAME, s.SYS_NO from SYS_RD_TABLE t, SYS_RD_SYSTEM s where TABLE_NO = '"
						+ table_no + "' AND t.sys_rd_system_id=s.sys_rd_system_id");
		stmt
				.setCountStmt("select count(TABLE_NO) from SYS_RD_TABLE where TABLE_NO = '"
						+ table_no + "'");
		// stmt.addSqlStmt( "select t.TABLE_NO, t.TABLE_NAME, t.TABLE_NAME_CN,
		// t.SYS_ID, s.SYS_NAME, s.SYS_NO from SYS_TABLE_SEMANTIC t,
		// SYS_SYSTEM_SEMANTIC s where TABLE_NAME = '" + table_no + "' AND
		// t.sys_id=s.sys_id" );
		// stmt.setCountStmt( "select count(TABLE_NO) from SYS_TABLE_SEMANTIC
		// where TABLE_NAME = '" + table_no + "'" );
		return stmt;
	}

	public SqlStatement queryColumn(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String column_no = dataBus.getValue("column_no");
		String sql = "select C.TABLE_NO,C.COLUMN_NO, C.column_code as COLUMN_NAME,C.column_name " +
				"as COLUMN_NAME_CN,C.ALIA_NAME as COLUMN_BYNAME,T.table_code as TABLE_NAME," +
				"T.table_name as TABLE_NAME_CN, C.SYS_COLUMN_TYPE as EDIT_TYPE,c.column_codeindex DEMO " +
				"from SYS_RD_COLUMN C, SYS_RD_TABLE T where COLUMN_NO ='" 
				+column_no + "' AND C.TABLE_NO=T.TABLE_NO";
		stmt.addSqlStmt(sql);
//		stmt.setCountStmt("select count(C.COLUMN_NO) from SYS_RD_COLUMN C,SYS_RD_TABLE T where COLUMN_NO = '"
//						+ column_no + "' AND C.TABLE_NO=T.TABLE_NO");
		return stmt;
	}

	public SqlStatement getJCDMFX(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String id = dataBus.getValue("jc_dm_id");
		String sql="select f.jcsjfx_dm,CONCAT(CONCAT(f.jcsjfx_dm, '---'), f.jcsjfx_mc) as jcsjfx_mc from gz_dm_jcdm t,gz_dm_jcdm_fx f where t.jc_dm_id = f.jc_dm_id and t.jc_dm_dm = '"
			+ id + "' order by xssx";
		stmt.addSqlStmt(sql);
		stmt.setCountStmt("select count(1) from gz_dm_jcdm t,gz_dm_jcdm_fx f where t.jc_dm_id = f.jc_dm_id and t.jc_dm_dm = '"
						+ id + "'");
		return stmt;
	}

	public SqlStatement queryList(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String id = dataBus.getValue("sys_advanced_query_id");
		String name = dataBus.getValue("name");
		String username = dataBus.getValue("username");
		String create_by = dataBus.getValue("create_by");
		String userId = dataBus.getValue("userid");
		String create_date_from = dataBus.getValue("create_date_from");
		String create_date_to = dataBus.getValue("create_date_to");

		StringBuffer sql = new StringBuffer();
		sql
				.append("select sys_advanced_query_id, name, create_by, create_date, last_exec_date, exec_total, display_columns, query_sql from sys_advanced_query where 1=1");
		if (id != null && !id.trim().equals("")) {
			sql.append(" AND sys_advanced_query_id='").append(id).append("' ");
		}
		if (name != null && !name.trim().equals("")) {
			sql.append(" AND name like '%").append(name).append("%' ");
		}
		if (create_by != null && !create_by.trim().equals("")
				&& !username.trim().equalsIgnoreCase("system")) {
			sql.append(" AND sys_svr_user_id='").append(userId).append("' ");
		}
		if (create_date_from != null && !create_date_from.trim().equals("")) {
			sql.append(" AND create_date>='").append(create_date_from).append(
					"' ");
		}
		if (create_date_to != null && !create_date_to.trim().equals("")) {
			sql.append(" AND create_date<='").append(create_date_to).append(
					"' ");
		}
		sql.append(" ORDER BY ORDER_COL DESC ");
		stmt.addSqlStmt(sql.toString());
		String countSql = "select count(1) from (" + sql + ") t";
		stmt.setCountStmt(countSql);
		return stmt;
	}

}
