package com.gwssi.resource.exception.dao;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.UuidGenerator;

public class ResExceptionDate extends BaseTable
{
	public ResExceptionDate()
	{

	}

	/**
	 * 注册SQL语句  
	 */
	protected void register()
	{
		registerSQLFunction("getOneByDate", DaoFunction.SQL_SELECT, "根据日期获取例外");
		registerSQLFunction("deleteByDate", DaoFunction.SQL_DELETE, "根据日期删除例外");
		registerSQLFunction("getAllSvrDate", DaoFunction.SQL_ROWSET, "获取所有例外");
		registerSQLFunction("getNowMonthSvrDate", DaoFunction.SQL_ROWSET, "获取当月的所有例外");
		registerSQLFunction("pauseAllDate", DaoFunction.SQL_UPDATE,
				"重置所有例外的有效标记");
		registerSQLFunction("updateByDeleteDate", DaoFunction.SQL_UPDATE,
				"重置所有例外的有效标记");
		registerSQLFunction("getTaskByMonth", DaoFunction.SQL_ROWSET, "获取每月的采集任务");
		// registerSQLFunction( "checkServDate", DaoFunction.SQL_SELECT,
		// "查询指定日期是否是例外" );
	}

	/**
	 * 执行SQL语句前的处理
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * 
	 * deleteByDate 根据日期参数删除例外，原则上每次只有一条数据被删除，因为同一天只有一条例外记录
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement deleteByDate(TxnContext request, DataBus inputData)
	{
		DataBus db = request.getRecord("record");
		String exception_date = db.getValue("exception_date");
		String sql = "";
		SqlStatement stmt = new SqlStatement();
		if (StringUtils.isNotEmpty(exception_date)) {
			sql = "delete from res_exception_date t where t.exception_date = '"
					+ exception_date + "'";
			stmt.addSqlStmt(sql);
		}

		return stmt;
	}

	/**
	 * 
	 * getNowMonthSvrDate 获取当月的所有例外记录
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getNowMonthSvrDate(TxnContext request, DataBus inputData)
	{
		String sql = "";
		String sqlStr = "";
		String month = request.getRecord("select-key").getValue(
				"exception_date");
		if (StringUtils.isNotEmpty(month)) {
			sql = "select t.* "
					+ "from res_exception_date t where t.is_markup ='"
					+ ExConstant.IS_MARKUP_Y + "' and t.exception_date like '"
					+ month + "%'";
			sqlStr = "select count(1) "
					+ "from res_exception_date t where t.is_markup ='"
					+ ExConstant.IS_MARKUP_Y + "' and t.exception_date like '"
					+ month + "%'";
		} else {
			sql = "select t.* "
					+ "from res_exception_date t where t.is_markup ='"
					+ ExConstant.IS_MARKUP_Y
					+ "' and t.exception_date like TO_CHAR(SYSDATE, 'YYYY-mm')||'%'";
			sqlStr = "select count(1) "
					+ "from res_exception_date t where t.is_markup ='"
					+ ExConstant.IS_MARKUP_Y
					+ "' and t.exception_date like TO_CHAR(SYSDATE, 'YYYY-mm')||'%'";
		}
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sql);
		stmt.setCountStmt(sqlStr);

		return stmt;
	}

	/**
	 * 
	 * getAllSvrDate 获取所有的例外记录
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getAllSvrDate(TxnContext request, DataBus inputData)
	{

		StringBuffer sql = new StringBuffer();
		String qMonth = request.getRecord("select-key").getValue("exception_date");
		//String qDate = request.getRecord("select-key").getValue("exception_date");
		sql.append("select t.exception_date, NVL(t.exception_desc,' ') exception_desc ")
		.append("from res_exception_date t where 1=1 ");
		if(StringUtils.isNotBlank(qMonth)){
			String[] mon = qMonth.split(",");
			if(mon.length==2){
				sql.append("and t.exception_date > '"+mon[0]+"'||'-00' and t.exception_date < '"+mon[1]+"'||'-32' ");
			}else{
				sql.append("and t.exception_date > '"+qMonth+"'||'-00' ");
			}
		}else{
			sql.append(" and t.exception_date > to_char(add_months(sysdate, -1), 'yyyy-mm') ");
			sql.append(" and t.exception_date < to_char(add_months(sysdate, 2), 'yyyy-mm') ");
		}
		/*if(StringUtils.isNotBlank(qDate)){
			sql.append("and t.exception_date = '"+qDate+"' ");
		}*/
		sql.append("and t.is_markup ='"
				+ ExConstant.IS_MARKUP_Y + "' ")
		.append("order by t.exception_date desc");
		
		String sqlstr = "select count(1) from (" + sql.toString() + " )";
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt(sqlstr);

		return stmt;
	}

	/**
	 * 
	 * pauseAllDate 重置例外状态 全部启用或者全部停用
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement pauseAllDate(TxnContext request, DataBus inputData)
	{
		String sql = "";
		String is_markup = request.getRecord("record").getValue("is_markup");
		if (StringUtils.isNotEmpty(is_markup)) {
			if (!is_markup.equals(ExConstant.IS_MARKUP_N)
					&& !is_markup.equals(ExConstant.IS_MARKUP_Y))
				is_markup = ExConstant.IS_MARKUP_Y;
			sql = "update res_exception_date set is_markup = '" + is_markup
					+ "'";
		}
		SqlStatement stmt = new SqlStatement();

		stmt.addSqlStmt(sql);

		return stmt;
	}

	/**
	 * getOneByDate 根据日期查询例外
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getOneByDate(TxnContext request, DataBus inputData)
	{
		StringBuffer sql = new StringBuffer();
		String date = request.getRecord("record").getValue("exception_date");

		sql.append("select * from res_exception_date ").append(
				"where exception_date = '").append(date).append(
				"' and rownum = 1 and is_markup = '").append(
				ExConstant.IS_MARKUP_Y).append("'");

		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sql.toString());

		return stmt;
	}
	
	/**
	 * 查询调度任务表，在日历中显示用
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getTaskByMonth(TxnContext request, DataBus inputData)
	{
		StringBuffer sql = new StringBuffer();
		String qMonth = request.getRecord("select-key").getValue("qmonth");
		String qDate = request.getRecord("select-key").getValue("qdate");
		if(StringUtils.isNotBlank(qDate)){
			qMonth = qDate.substring(0, qDate.lastIndexOf("-"));
		}

		sql.append("with ta as ").append("(select ct.collect_task_id, ")
				.append("ct.task_name, cts.start_time, ")
				.append("cts.end_time, cts.scheduling_type, ")
				.append("cts.scheduling_week, cts.scheduling_day ")
				.append("from collect_task_scheduling cts, collect_task ct ")
				.append("where cts.is_markup = 'Y' ")
				.append("and ct.is_markup = 'Y' ")
				.append("and cts.collect_task_id = ct.collect_task_id) ");
		sql.append("select ta.collect_task_id, ")
				.append("ta.task_name, ta.start_time, ta.end_time, ")
				.append("nds.ac ndate, ")
				.append("nds.n1 nday from ta, ")
				.append("(SELECT to_char(wdate, 'yyyy-mm-dd') ac, ")
				.append("case nd - 1 when 0 then 7 else nd - 1 end n1 ");
		if (StringUtils.isNotBlank(qDate)) {
			sql.append(
					"FROM (SELECT to_char(to_date('" + qDate
							+ "', 'yyyy-mm-dd'), 'iw') sweek, ")
					.append("to_char(to_date('" + qDate
							+ "', 'yyyy-mm-dd') - 7 + LEVEL, 'd') nd, ")
					.append("to_char(to_date('" + qDate
							+ "', 'yyyy-mm-dd') - 7 + LEVEL, 'iw') wweek, ")
					.append("to_date('" + qDate
							+ "', 'yyyy-mm-dd') - 7 + LEVEL wdate ");
		} else {
			sql.append("FROM (SELECT to_char(SYSDATE, 'iw') sweek, ")
					.append("to_char(SYSDATE - 7 + LEVEL, 'd') nd, ")
					.append("to_char(SYSDATE - 7 + LEVEL, 'iw') wweek, ")
					.append("SYSDATE - 7 + LEVEL wdate ");
		}
		sql.append("FROM dual CONNECT BY LEVEL <= 14) ")
				.append("WHERE sweek = wweek) nds ")
				.append("where ta.scheduling_type = '02' ")
				.append("and instr(ta.scheduling_week,  nds.n1, 1) > 0 ");
		/*sql.append("and to_char(nds.ac, 'yyyy-mm-dd') not in ")
				.append("(select t.exception_date ")
				.append("from res_exception_date t ");
		if (StringUtils.isNotBlank(qDate)) {
			sql.append(
					"where t.exception_date > to_char(to_date('" + qDate
							+ "', 'yyyy-mm-dd') - 7, 'yyyy-mm-dd') ")
					.append("and t.exception_date < to_char(to_date('" + qDate
							+ "', 'yyyy-mm-dd') + 7, 'yyyy-mm-dd')) ");
		} else {
			sql.append(
					"where t.exception_date > to_char(sysdate - 7, 'yyyy-mm-dd') ")
					.append("and t.exception_date < to_char(sysdate + 7, 'yyyy-mm-dd')) ");
		}*/
		
		sql.append("union all ");
		sql.append("select ta.collect_task_id, ")
				.append("ta.task_name, ta.start_time, ta.end_time, ")
				.append("to_char(daya.ac, 'yyyy-mm-dd') ndate, -1 nday ")
				.append("from ta, (select t1.ac ")
				.append("from (select to_date(");
		if (StringUtils.isNotBlank(qMonth)) {
			sql.append("'" + qMonth + "'");
		} else {
			sql.append("to_char(sysdate, 'yyyy-mm')");
		}
		sql.append(", 'yyyy-mm') + ").append("(rownum - 1) ac from dual ")
				.append("connect by rownum < (select last_day(sysdate) - ")
				.append("last_day(add_months(sysdate, -1)) ")
				.append("from dual)) t1) daya ")
				.append("where ta.scheduling_type = '01' ");
		/*sql.append("and to_char(daya.ac, 'yyyy-mm-dd') not in ")
				.append("(select t.exception_date ")
				.append("from res_exception_date t ");
		if (StringUtils.isNotBlank(qMonth)) {
			sql.append(
					"where t.exception_date > to_char(to_date('"+qMonth+"', 'yyyy-mm'), 'yyyy-mm')||'-00' ")
				.append("and t.exception_date < to_char(to_date('"+qMonth+"', 'yyyy-mm'), 'yyyy-mm')||'-32') ");
		} else {
			sql.append(
					"where t.exception_date > to_char(sysdate, 'yyyy-mm') || '-00' ")
					.append("and t.exception_date < to_char(sysdate, 'yyyy-mm') || '-32') ");
		}*/
		sql.append(" union all ");
		sql.append("select ta.collect_task_id, ")
				.append("ta.task_name, ta.start_time, ta.end_time, ")
				.append("daya.ac || '-' || lpad(ta.scheduling_day, 2, '0') ndate, ")
				.append("-1 nday from ta, ")
				.append("(select to_char(sysdate, 'yyyy') || '-' || lpad(level, 2, '0') ac ")
				.append("from dual connect by level <= 12) daya ")
				.append("where ta.scheduling_type = '03' ");

		SqlStatement stmt = new SqlStatement();
		//System.out.println("query taskList for calendar \n" + sql.toString());
		stmt.addSqlStmt(sql.toString());

		return stmt;
	}

	/**
	 * 
	 * updateByDeleteDate 连续的例外被打断后，更新为两段连续的例外
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement updateByDeleteDate(TxnContext request, DataBus inputData)
	{
		DataBus db = request.getRecord("record");
		String exception_date = db.getValue("exception_date");
		String exception_id = request.getValue("exception_id");
		StringBuffer sql = new StringBuffer();
		SqlStatement stmt = new SqlStatement();

		sql.append("update res_exception_date set exception_id = '").append(
				UuidGenerator.getUUID()).append("' where exception_date > '")
				.append(exception_date).append("' and exception_id = '")
				.append(exception_id).append("' and is_markup = '").append(
						ExConstant.IS_MARKUP_Y).append("'");

		stmt.addSqlStmt(sql.toString());

		return stmt;
	}

	/*
	 * public SqlStatement checkServDate( TxnContext request, DataBus inputData ) {
	 * System.out.println(request); DataBus db = request.getRecord("record");
	 * String exception_date = db.getValue("exception_date"); String sql = "";
	 * SqlStatement stmt = new SqlStatement( );
	 * if(StringUtils.isNotEmpty(exception_date)){ sql = "select * from
	 * res_exception_date t where t.exception_date = '"+exception_date+"'";
	 * stmt.addSqlStmt( sql ); } System.out.println("ddd"+stmt.getCountStmt());
	 * return stmt; }
	 */

	/**
	 * 执行完SQL语句后的处理
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

}
