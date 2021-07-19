package com.gwssi.log.report.dao;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.DateUtil;

public class LogReportCreate extends BaseTable
{
	public LogReportCreate()
	{

	}

	/**
	 * 注册SQL语句
	 */
	protected void register()
	{
		registerSQLFunction("queryLogReportCreate", DaoFunction.SQL_ROWSET,
				"查询系统使用情况报告");
		registerSQLFunction("addLogReport", DaoFunction.SQL_INSERT, "生成系统报告");
		registerSQLFunction("upLoadLogReport", DaoFunction.SQL_INSERT,
				"上传日志系统报告");
		registerSQLFunction("publishReport", DaoFunction.SQL_UPDATE, "发布报告");
		registerSQLFunction("returnReport", DaoFunction.SQL_UPDATE, "退回报告");
		registerSQLFunction("modifyReport", DaoFunction.SQL_UPDATE, "修改报告");
		registerSQLFunction("addReportUse", DaoFunction.SQL_INSERT,"增加日至报告使用记录");
		registerSQLFunction("checkReportName", DaoFunction.SQL_ROWSET,"验证是否存在报告名称");
		registerSQLFunction("updateReportState", DaoFunction.SQL_UPDATE,"修改使用报告状态");
		registerSQLFunction("queryreport", DaoFunction.SQL_ROWSET,"查询系统使用报告日志列");
		registerSQLFunction("getStateSet", DaoFunction.SQL_ROWSET,"获取报告状态用于查询条件");

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

	public SqlStatement queryreport(TxnContext request,
			DataBus inputData) throws TxnException{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append("select cd.codename as title, cd.codevalue as key ")
					.append("from codedata cd, ")
					.append("(select svr.")
					.append(column)
					
					.append(" from LOG_REPORT_CREATE svr group by svr.")
					.append(column).append(") t where cd.codetype = '")
					.append(codeType).append("' and cd.codevalue = t.")
					.append(column).append("(+)");
			stmt.addSqlStmt(sqlBuffer.toString());
			System.out.println("sql语句的"+sqlBuffer);
		}
		return stmt;
		
		
	}
	
	
	
	
	/**
	 * 查询系统使用情况报告
	 */
	public SqlStatement queryLogReportCreate(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		
		String state = request.getRecord("select-key").getValue("state");
		String create_date = request.getRecord("select-key").getValue("created_time");
		

		StringBuffer querySql = new StringBuffer(
				"select t.log_report_create_id,t.report_name,t.create_date,t.last_mender,t.publish_date,t.publish_person,t.state,t.operate,t.filename,t.path,t.timestamp,t.creator,t.report_type from LOG_REPORT_CREATE t"
						+ " where t.state<2 ");
		
		if (StringUtils.isNotBlank(create_date)) {
			String[] times = DateUtil.getDateRegionByDatePicker(create_date,
					false);
			if (StringUtils.isNotBlank(times[0])) {
				querySql.append(" and t.create_date >= '" + times[0] + "'");
				}
			if (StringUtils.isNotBlank(times[1])) {
				querySql.append(" and t.create_date <= '" + times[1] + "'");
			}
		}
		


		if (state != null && !"".equals(state)) {
			querySql.append(" and t.state = '" + state + "'");
		}

		//querySql.append(" order by t.report_name desc");
		querySql.append(" order by t.timestamp desc");
		System.out.println(querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;

	}
	
	/**
	 * 查询系统使用情况报告
	 */
	public SqlStatement getStateSet(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String sql="select codevalue key,codename title from codedata c " +
				"where c.codetype='报告状态' and c.status='1'";
		stmt.addSqlStmt(sql);
		return stmt;

	}
	
	/**
	 * 生成日志报告
	 */
	public SqlStatement addLogReport(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("record");
		// 获取日志报告ID
		String log_report_create_id = data.getValue("log_report_create_id");
		String report_name = data.getValue("report_name");
		// 获取报告类型
		String report_type = data.getValue("report_type");
		// 获取当前日期和时间
		String create_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();

		String filename = data.getValue("filename");
		String path = data.getValue("path");

		VoUser user = request.getOperData();
		String creator = user.getOperName();
		StringBuffer insertSql = new StringBuffer(
				"insert into log_report_create(log_report_create_id,report_name,creator,"
						+ "create_date,last_mender,publish_date,"
						+ "publish_person,state,operate,"
						+ "filename,path,report_type," + "timestamp)"
						+ "values ('" + log_report_create_id + "','"
						+ report_name + "','" + creator + "'," + "'"
						+ create_date + "','"+creator+"',''," + "'','0','下载'," + "'"
						+ filename + "','" + path + "','" + report_type + "','"
						+ timestamp + "')");
		//System.out.println("生成日志sql:"+insertSql.toString());
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}

	/**
	 * 生成日志报告
	 */
	public SqlStatement upLoadLogReport(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("record");
		// 获取日志报告ID
		String log_report_create_id = data.getValue("log_report_create_id");
		String report_name = data.getValue("report_name");
		// 获取报告类型
		String report_type = data.getValue("report_type");
		// 获取当前日期和时间
		String create_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();

		String filename = data.getValue("filename");
		String path = data.getValue("path");

		VoUser user = request.getOperData();
		String creator = user.getOperName();
		StringBuffer insertSql = new StringBuffer(
				"insert into log_report_create(log_report_create_id,report_name,creator,"
						+ "create_date,last_mender,publish_date,"
						+ "publish_person,state,operate,"
						+ "filename,path,report_type," + "timestamp)"
						+ "values ('" + log_report_create_id + "','"
						+ report_name + "','" + creator + "'," + "'"
						+ create_date + "','',''," + "'','0','下载'," + "'"
						+ filename + "','" + path + "','" + report_type + "','"
						+ timestamp + "')");
		// System.out.println("上传日志sql:"+insertSql.toString());
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}

	/**
	 * 发布日志报告
	 */
	public SqlStatement publishReport(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("select-key");
		// 获取日志报告ID
		String log_report_create_id = data.getValue("log_report_create_id");

		// 获取当前日期和时间
		String create_date = CalendarUtil.getCurrentDate();
		// String timestamp = CalendarUtil.getCurrentDateTime();

		VoUser user = request.getOperData();
		String creator = user.getOperName();
		StringBuffer insertSql = new StringBuffer(
				"update log_report_create set publish_date='" + create_date
						+ "',publish_person='" + creator
						+ "',state='1' where log_report_create_id='"
						+ log_report_create_id + "'");

		// System.out.println("发布报告sql:"+insertSql.toString());
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}

	/**
	 * 退回日志报告
	 */
	public SqlStatement returnReport(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("select-key");
		// 获取日志报告ID
		String log_report_create_id = data.getValue("log_report_create_id");

		// 获取当前日期和时间
		String create_date = CalendarUtil.getCurrentDate();
		// String timestamp = CalendarUtil.getCurrentDateTime();

		VoUser user = request.getOperData();
		String creator = user.getOperName();
		StringBuffer insertSql = new StringBuffer(
				"update log_report_create set publish_date='',publish_person='',state='0' where log_report_create_id='"
						+ log_report_create_id + "'");

		// System.out.println("退回报告sql:"+insertSql.toString());
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}
	
	/**
	 * 修改使用报告状态
	 */
	public SqlStatement updateReportState(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("primary-key");
		// 获取日志报告ID
		String log_report_create_id = data.getValue("log_report_create_id");
		StringBuffer insertSql = new StringBuffer(
				"update log_report_create set state='2' where log_report_create_id='"
						+ log_report_create_id + "'");
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}

	/**
	 * 修改日志报告
	 */
	public SqlStatement modifyReport(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("record");
		// 获取日志报告ID
		String log_report_create_id = data.getValue("log_report_create_id");

		// 获取当前日期和时间
		// String create_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();

		VoUser user = request.getOperData();
		String creator = user.getOperName();
		StringBuffer updateSql = new StringBuffer(
				"update log_report_create set timestamp='" + timestamp
						+ "',last_mender='" + creator
						+ "' where log_report_create_id='"
						+ log_report_create_id + "'");

		// System.out.println("修改日志报告sql:"+updateSql.toString());
		stmt.addSqlStmt(updateSql.toString());
		return stmt;
	}

	/**
	 * 生成日志报告
	 */
	public SqlStatement addReportUse(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("select-key");
		// 获取日志报告ID
		String log_report_create_id = data.getValue("log_report_create_id");
		// 获取当前日期和时间
		String create_date = CalendarUtil.getCurrentDate();
		String timestamp = CalendarUtil.getCurrentDateTime();

		VoUser user = request.getOperData();
		String browser_person = user.getOperName();
		StringBuffer insertSql = new StringBuffer(
				"insert into log_report_use(log_report_use_id,log_report_create_id,browser_person,browser_date,"
						+ "operate,filename,path,"
						+ "timestamp)"
						+ "select sys_guid(),'"
						+ log_report_create_id
						+ "','"
						+ browser_person
						+ "','"
						+ create_date
						+ "',"
						+ "'下载',filename,path,'"
						+ timestamp
						+ "' from log_report_create where log_report_create_id='"
						+ log_report_create_id + "'");
		// System.out.println("上传日志sql:"+insertSql.toString());
		stmt.addSqlStmt(insertSql.toString());
		return stmt;
	}

	/**
	 * 查询报告名称
	 */
	public SqlStatement checkReportName(TxnContext request, DataBus inputData)
			throws TxnException
	{

		SqlStatement stmt = new SqlStatement();
		String report_name = request.getRecord("select-key").getValue(
				"report_name");

		StringBuffer querySql = new StringBuffer(
				"select t.* from log_report_create t where state<2");

		if (report_name != null && !"".equals(report_name)) {
			querySql.append(" and t.report_name = '" + report_name + "'");
		}

		// System.out.println("查询报告名称:"+querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		//stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");

		return stmt;
	}

}
