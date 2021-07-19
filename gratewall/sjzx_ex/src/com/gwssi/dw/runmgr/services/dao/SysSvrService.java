package com.gwssi.dw.runmgr.services.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[sys_svr_service]的处理类
 * 
 * @author Administrator
 * 
 */
public class SysSvrService extends BaseTable
{
	public SysSvrService()
	{

	}

	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register()
	{
		registerSQLFunction("getFirstLevelNode", DaoFunction.SQL_ROWSET,
				"读取第一层节点");
		// registerSQLFunction( "getSecondLevelNode", DaoFunction.SQL_ROWSET,
		// "读取第二层及以后节点" );
		registerSQLFunction("getAllTables", DaoFunction.SQL_ROWSET,
				"查询某主题下所有数据表");
		registerSQLFunction("getAllColumns", DaoFunction.SQL_ROWSET,
				"查询数据表下所有字段");
		registerSQLFunction("haveAssigned", DaoFunction.SQL_ROWSET,
				"检查此服务是否已被使用");
		registerSQLFunction("queryTable", DaoFunction.SQL_ROWSET, "查询数据表");
		registerSQLFunction("queryColumn", DaoFunction.SQL_ROWSET, "查询字段");
		registerSQLFunction("deleteService", DaoFunction.SQL_DELETE, "删除服务");
		registerSQLFunction("queryList", DaoFunction.SQL_ROWSET, "查询共享服务列表");
		registerSQLFunction("queryUserList", DaoFunction.SQL_ROWSET,
				"查询共享服务配置的用户列表");
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

	public SqlStatement queryList(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		// String id = dataBus.getValue("sys_advanced_query_id");
		String svr_code = dataBus.getValue("svr_code");
		String sys_svr_service_id = dataBus.getValue("sys_svr_service_id");
		String create_by = dataBus.getValue("create_by");
		String create_date_from = dataBus.getValue("create_date_from");
		String create_date_to = dataBus.getValue("create_date_to");

		StringBuffer sql = new StringBuffer();
		sql
				.append("select sys_svr_service_id,svr_name,table_no,column_no,create_date,create_by,svr_desc,svr_code,svr_type from sys_svr_service where 1=1");
		if (svr_code != null && !svr_code.trim().equals("")) {
			sql.append(" AND svr_code like '%").append(svr_code).append("%' ");
		}
		if (sys_svr_service_id != null && !sys_svr_service_id.trim().equals("")) {
			sql.append(" AND sys_svr_service_id = '")
					.append(sys_svr_service_id).append("' ");
		}
		if (create_by != null && !create_by.trim().equals("")) {
			sql.append(" AND create_by like '%").append(create_by)
					.append("%' ");
		}
		if (create_date_from != null && !create_date_from.trim().equals("")) {
			sql.append(" AND create_date>='").append(create_date_from).append(
					"' ");
		}
		if (create_date_to != null && !create_date_to.trim().equals("")) {
			sql.append(" AND create_date<='").append(create_date_to).append(
					"' ");
		}
		sql.append(" ORDER BY SVR_ORDER DESC ");
		stmt.addSqlStmt(sql.toString());
		String countSql = "select count(1) from (" + sql + ") t";
		stmt.setCountStmt(countSql);
		return stmt;
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
		stmt.addSqlStmt("select sys_rd_system_id sys_id,sys_no,sys_name,sort sys_order  from sys_rd_system s order by to_number(sort) ");
		stmt.setCountStmt("SELECT COUNT(1) FROM sys_rd_system");
		return stmt;
	}

	public SqlStatement queryTable(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String table_no = dataBus.getValue("table_no");
		stmt
				.addSqlStmt("select t.TABLE_NO, t.TABLE_NAME, t.TABLE_NAME_CN, t.SYS_ID, s.SYS_NAME, s.SYS_NO from SYS_TABLE_SEMANTIC t, SYS_SYSTEM_SEMANTIC s where TABLE_NO = '"
						+ table_no + "' AND t.sys_id=s.sys_id");
		stmt
				.setCountStmt("select count(TABLE_NO) from SYS_TABLE_SEMANTIC where TABLE_NO = '"
						+ table_no + "'");
		return stmt;
	}

	public SqlStatement queryColumn(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String column_no = dataBus.getValue("column_no");
		stmt
				.addSqlStmt("select C.TABLE_NO, C.COLUMN_NO, C.COLUMN_NAME, C.COLUMN_NAME_CN, C.COLUMN_BYNAME, T.TABLE_NAME, T.TABLE_NAME_CN from SYS_COLUMN_SEMANTIC C,SYS_TABLE_SEMANTIC T where COLUMN_NO = '"
						+ column_no + "' AND C.TABLE_NO=T.TABLE_NO");
		stmt
				.setCountStmt("select count(C.COLUMN_NO) from SYS_COLUMN_SEMANTIC C,SYS_TABLE_SEMANTIC T where COLUMN_NO = '"
						+ column_no + "' AND C.TABLE_NO=T.TABLE_NO");
		return stmt;
	}

	public SqlStatement getAllTables(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String sys_id = dataBus.getValue("sys_id");
		stmt
				.addSqlStmt("select t.TABLE_NO, t.TABLE_NAME, t.TABLE_NAME_CN,t.TABLE_ORDER, t.SYS_ID, s.SYS_NAME, s.SYS_NO from SYS_TABLE_SEMANTIC t, SYS_SYSTEM_SEMANTIC s where t.sys_id = '"
						+ sys_id
						+ "' AND t.sys_id=s.sys_id order by t.TABLE_NAME");
		stmt
				.setCountStmt("select count(1) from SYS_TABLE_SEMANTIC where DCZD_DM = '"
						+ sys_id + "'");
		return stmt;
	}

	public SqlStatement getAllColumns(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String table_no = dataBus.getValue("table_no");
		stmt.setPageRows(-1);
		stmt
				.addSqlStmt("select c.COLUMN_NO, c.COLUMN_NAME, c.COLUMN_NAME_CN, C.COLUMN_BYNAME, c.EDIT_TYPE, c.DEMO, c.TABLE_NO, t.table_name, t.table_name_cn from SYS_COLUMN_SEMANTIC c, SYS_TABLE_SEMANTIC t where c.TABLE_NO = '"
						+ table_no
						+ "' AND c.table_no=t.table_no order by c.COLUMN_NAME");
		stmt
				.setCountStmt("select count(*) from SYS_COLUMN_SEMANTIC where TABLE_NO = '"
						+ table_no + "'");
		return stmt;
	}

	public SqlStatement haveAssigned(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String sys_svr_service_id = dataBus.getValue("sys_svr_service_id");
		StringBuffer newId = new StringBuffer();
		if (sys_svr_service_id != null && !sys_svr_service_id.equals("")) {
			String[] ids = sys_svr_service_id.split(",");
			for (int i = 0; i < ids.length; i++) {
				newId.append("'").append(ids[i]).append("'");
				if (i != ids.length - 1) {
					newId.append(", ");
				}
			}

		}
		stmt
				.addSqlStmt("select SYS_SVR_SERVICE_ID from SYS_SVR_CONFIG where SYS_SVR_SERVICE_ID IN ("
						+ newId + ")");
		stmt
				.setCountStmt("select count(SYS_SVR_SERVICE_ID) from SYS_SVR_CONFIG where SYS_SVR_SERVICE_ID IN ("
						+ newId + ")");
		return stmt;
	}

	public SqlStatement getJCDMFX(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String id = dataBus.getValue("jc_dm_id");
		stmt
				.addSqlStmt("select f.jcsjfx_dm,CONCAT(CONCAT(f.jcsjfx_dm, '---'), f.jcsjfx_mc) as jcsjfx_mc from gz_dm_jcdm t,gz_dm_jcdm_fx f where t.jc_dm_id = f.jc_dm_id and t.jc_dm_dm = '"
						+ id + "' order by xssx");
		stmt
				.setCountStmt("select count(1) from gz_dm_jcdm t,gz_dm_jcdm_fx f where t.jc_dm_id = f.jc_dm_id and t.jc_dm_dm = '"
						+ id + "'");
		return stmt;
	}

	public SqlStatement deleteService(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String sys_svr_service_id = dataBus.getValue("sys_svr_service_id");
		StringBuffer newId = new StringBuffer();
		if (sys_svr_service_id != null && !sys_svr_service_id.equals("")) {
			String[] ids = sys_svr_service_id.split(",");
			for (int i = 0; i < ids.length; i++) {
				newId.append("'").append(ids[i]).append("'");
				if (i != ids.length - 1) {
					newId.append(", ");
				}
			}

		}
		stmt
				.addSqlStmt("DELETE FROM sys_svr_service where SYS_SVR_SERVICE_ID IN ("
						+ newId + ")");
		// stmt.setCountStmt( "select count(SYS_SVR_SERVICE_ID) from
		// SYS_SVR_CONFIG where SYS_SVR_SERVICE_ID IN (" + newId + ")" );
		return stmt;
	}

	public SqlStatement queryUserList(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String id = dataBus.getValue("sys_svr_service_id");
		String sql = "select s.sys_svr_user_id,s.login_name,user_type,s.password,"
				+ "s.user_name from sys_svr_user s where sys_svr_user_id in(select sys_svr_user_id "
				+ "from sys_svr_config where sys_svr_service_id = '" + id + "')";
		stmt.addSqlStmt(sql);
		stmt.setCountStmt("select count(1) from (" + sql + ")");
		return stmt;
	}
}
