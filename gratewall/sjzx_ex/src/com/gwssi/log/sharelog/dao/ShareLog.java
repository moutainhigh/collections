package com.gwssi.log.sharelog.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.Constants;
import com.gwssi.common.util.DateUtil;

public class ShareLog extends BaseTable
{
	public ShareLog()
	{

	}

	/**
	 * 注册SQL语句
	 */
	protected void register()
	{

		registerSQLFunction("queryShareLog", DaoFunction.SQL_ROWSET, "查看共享日志信息");
		registerSQLFunction("queryShareLogList", DaoFunction.SQL_ROWSET,
				"查看共享日志信息");
		registerSQLFunction("queryStatisticsShareLog", DaoFunction.SQL_ROWSET,
				"统计共享日志信息");
		registerSQLFunction("queryTargetsShareLog", DaoFunction.SQL_ROWSET,
				"统计多对象共享日志信息");
		registerSQLFunction("queryShareLogUnUse", DaoFunction.SQL_ROWSET,
				"统计未用服务信息");
		registerSQLFunction("queryShareLogExpetion", DaoFunction.SQL_ROWSET,
				"统计服务异常信息");
		registerSQLFunction("queryShareLogMonthInfo", DaoFunction.SQL_ROWSET,
				"统计服务月统计信息");
		registerSQLFunction("queryShareLogMonthMainInfo",
				DaoFunction.SQL_ROWSET, "统计服务当月统计信息");
		registerSQLFunction("queryShareLogSpread", DaoFunction.SQL_ROWSET,
				"统计服务分布信息");
		registerSQLFunction("queryShareLogExpeAndTime", DaoFunction.SQL_ROWSET,
				"查询用户某段时间的异常信息和最后访问时间");

		registerSQLFunction("getlistserobj", DaoFunction.SQL_ROWSET, "返回服务对象列");
		registerSQLFunction("queryserobj", DaoFunction.SQL_ROWSET, "根据服务对象查结果");
		registerSQLFunction("getlistmonitorobj", DaoFunction.SQL_ROWSET,
				"返回监控对象列");
		registerSQLFunction("querymonitor", DaoFunction.SQL_ROWSET, "查询运行监控日志");
		registerSQLFunction("queryMonitorLog", DaoFunction.SQL_ROWSET,
				"查询运行监控日志");
		registerSQLFunction("queryRunningInfo", DaoFunction.SQL_ROWSET,
				"查询半小时之内运行情况");
		registerSQLFunction("queryRunningInfoIndex", DaoFunction.SQL_ROWSET,
				"查询警情列表用于首页");
		registerSQLFunction("queryRunTaskInfo", DaoFunction.SQL_ROWSET,
				"查询服务对象的运行情况");
		registerSQLFunction("queryTaskWamingInfo", DaoFunction.SQL_ROWSET,
				"查询服务对象的警情信息");

		registerSQLFunction("updateShareLog", DaoFunction.SQL_UPDATE, "共享日志归档");
		registerSQLFunction("getTargetInfo", DaoFunction.SQL_ROWSET, "获取服务对象列表做查询条件");
		
	}

	public SqlStatement queryShareLog(TxnContext request, DataBus inputData)
			throws TxnException
	{
		DataBus db = request.getRecord("select-key");
		String log_id = db.getValue("log_id");

		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						" select service_targets_name,log_id,service_no,service_type,service_start_time,service_end_time,service_name,service_id,access_ip,consume_time,record_start,record_end,record_amount,patameter,service_state,return_codes,targets_type,log_type ")
				.append("from share_log where log_id='");
		sql.append(log_id);
		sql.append("'");
		stmt.addSqlStmt(sql.toString());
		// stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );

		return stmt;
	}

	public SqlStatement queryShareLogList(TxnContext request, DataBus inputData)
			throws TxnException, ParseException
	{
		DataBus db = request.getRecord("select-key");

		String created_time_start = db.getValue("created_time_start");
		String created_time_end = db.getValue("created_time_end");
		// String service_targets_id = db.getValue("service_targets_id");
		String service_targets_name = db.getValue("service_targets_name");
		String service_state = db.getValue("service_state");
		String service_type = db.getValue("service_type");
		String targets_type = db.getValue("targets_type");
		String service_no = db.getValue("service_no");

		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();

		String begin_time = "";
		String end_time = "";
		boolean isHs = false;
		// 如果开始时间、结束时间都不为空
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isBlank(created_time_start)
				&& StringUtils.isBlank(created_time_end)) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			begin_time = df.format(calendar.getTime()) + " 00:00:00";
			end_time = df.format(new Date()) + " 23:59:59";
		} else {
			if (StringUtils.isNotBlank(created_time_start)) {
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				if (df.parse(created_time_start).before(
						df.parse(year + "-01-01"))) {
					isHs = true;
				}
				begin_time = created_time_start + " 00:00:00";
			}
			if (StringUtils.isNotBlank(created_time_end)) {
				end_time = created_time_end + " 23:59:59";
			}
		}

		if (!isHs) {
			sql
					.append(" select service_targets_name,log_id,service_no,service_type,");
			sql
					.append("service_start_time,service_end_time,service_name,service_id,access_ip,");
			sql
					.append("consume_time,record_start,record_end,record_amount,patameter,service_state,");
			sql
					.append("return_codes,targets_type,log_type from share_log where 1=1");
		} else {
			sql
					.append(" select * from(select service_targets_name,log_id,service_no,service_type,");
			sql
					.append("service_start_time,service_end_time,service_name,service_id,access_ip,");
			sql
					.append("consume_time,record_start,record_end,record_amount,patameter,service_state,");
			sql
					.append("return_codes,targets_type,log_type from share_log union all ");
			sql
					.append("select service_targets_name,log_id,service_no,service_type,");
			sql
					.append("service_start_time,service_end_time,service_name,service_id,access_ip,");
			sql
					.append("to_char(consume_time),record_start,record_end,record_amount,patameter,service_state,");
			sql
					.append("return_codes,targets_type,log_type from share_log_hs) where 1=1");
		}

		if (service_targets_name != null && !"".equals(service_targets_name)) {
			sql.append(" and service_targets_name like '%"
					+ service_targets_name + "%'");
		}

		if (service_type != null && !"".equals(service_type)) {

			sql.append(" and service_type = '" + service_type + "'");
		}

		if (service_state != null && !"".equals(service_state)) {

			sql.append(" and service_state = '" + service_state + "'");
		}

		if (targets_type != null && !"".equals(targets_type)) {
			sql.append(" and targets_type = '" + targets_type + "'");
		}

		if (StringUtils.isNotBlank(service_no)) {
			sql.append(" and service_no like '%" + service_no + "%'");
		}
		
		if(begin_time!=null&&!"".equals(begin_time)){
			sql.append(" and service_start_time >= '").append(begin_time).append(
			"'");
		}
		
		if(end_time!=null&&!"".equals(end_time)){
			sql.append(" and service_end_time <= '").append(end_time).append("'");
		}
		
		
		sql.append(" and log_type = '" + ExConstant.LOG_TYPE_USER + "'");
		sql.append(" and service_state is null");

		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
		sql.append(" order by service_end_time desc");
		//System.out.println(sql.toString());
		stmt.addSqlStmt(sql.toString());

		return stmt;
	}

	public SqlStatement queryStatisticsShareLog(TxnContext request,
			DataBus inputData) throws TxnException, ParseException
	{
		DataBus db = request.getRecord("select-key");

		String service_targets_id = db.getValue("service_targets_id");
		String query_index = db.getValue("query_index");
		String startTime = db.getValue("startTime");
		String statistical_granularity = db.getValue("statistical_granularity");
		String endTime = db.getValue("endTime");

		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		System.out.println("======传入参数=====");
		System.out.println("对象ID：" + service_targets_id);
		System.out.println("起始时间：" + startTime);
		System.out.println("结束时间：" + endTime);
		System.out.println("统计粒度：" + statistical_granularity);
		System.out.println("指标：" + query_index);
		/*
		 * 统计粒度 00 日 01 周 02 月 03 季 04 半年 05 年
		 * 页面上 只有日 月 年
		 */
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer query_str= new StringBuffer("select t2.mon, nvl(t1.s, 0) s, nvl(t1.n, 0) n, nvl(t1.ts, 0) ts");
		if (StringUtils.isBlank(startTime)) {
			Calendar calendar=Calendar.getInstance();
			calendar.add(Calendar.DATE, -7);
			startTime=df.format(calendar.getTime());
			endTime=df.format(new Date());
			statistical_granularity="00";
		}
		if (statistical_granularity.equals("00")) {
			sql.append(query_str);
			sql.append(" from (select t.log_date mon, nvl(sum(t.exec_count), 0) s,");
			sql.append("nvl(sum(t.sum_record_amount), 0) n, round(sum(t.sum_CONSUME_TIME)/sum(t.exec_count), 4) ts");
			sql.append(" from share_log_statistics t where 1 = 1  ");
			if (StringUtils.isNotBlank(service_targets_id)) {
				sql.append("and service_targets_id = '").append(service_targets_id).append("'");
			}
			sql.append(" and log_date >= '").append(startTime).append("'");
			sql.append(" and log_date <= '").append(endTime).append("' group by t.log_date order by 1) t1,");
			sql.append("(select to_char(to_date('").append(startTime).append("', 'yyyy-mm-dd')");
			sql.append("+ rownum - 1,'yyyy-mm-dd') mon  from dual connect by rownum <= ");
			sql.append("(to_date('").append(endTime).append("', 'yyyy-mm-dd') -");
			sql.append("to_date('").append(startTime).append("', 'yyyy-mm-dd')) + 1) t2");
			sql.append(" where t1.mon(+) = t2.mon order by t2.mon asc");
		}else if(statistical_granularity.equals("02")){//如果为月
			sql.append(query_str);
			sql.append(" from (select substr(t.log_date,0,7) mon, nvl(sum(t.exec_count), 0) s,");
			sql.append("nvl(sum(t.sum_record_amount), 0) n, round(sum(t.sum_CONSUME_TIME)/sum(t.exec_count), 4) ts");
			sql.append(" from share_log_statistics t where 1 = 1 and service_targets_id = '");
			sql.append(service_targets_id).append("'");
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(df.parse(endTime));
			calendar.add(Calendar.MONTH, 1);
			endTime=df.format(calendar.getTime());
			sql.append(" and log_date >= '").append(startTime).append("'");
			sql.append(" and log_date < '").append(endTime).append("' group by  substr(t.log_date,0,7) order by 1) t1,");
			sql.append("(select to_char(add_months(to_date('").append(startTime);
			sql.append("', 'yyyy-MM-dd'), rownum - 1),'yyyy-MM') mon from dual connect by rownum <=");
			sql.append(" months_between(to_date('").append(endTime)
					.append("', 'yyyy-MM-dd'),to_date('");
			sql.append(startTime).append("', 'yyyy-MM-dd'))) t2");
			sql.append(" where t1.mon(+) = t2.mon order by t2.mon asc");
		}else {
			sql.append(query_str);
			sql.append(" from (select substr(t.log_date,0,4) mon, nvl(sum(t.exec_count), 0) s,");
			sql.append("nvl(sum(t.sum_record_amount), 0) n, round(sum(t.sum_CONSUME_TIME)/sum(t.exec_count), 4) ts");
			sql.append(" from share_log_statistics t where 1 = 1 and service_targets_id = '");
			sql.append(service_targets_id).append("'");
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(df.parse(endTime));
			calendar.add(Calendar.YEAR, 1);
			endTime=df.format(calendar.getTime());
			sql.append(" and log_date >= '").append(startTime).append("'");
			sql.append(" and log_date < '").append(endTime).append("' group by  substr(t.log_date,0,4) order by 1) t1,");
			sql.append("(select to_char(add_months(to_date('").append(startTime);
			sql.append("', 'yyyy-MM-dd'), 12*(rownum - 1)),'yyyy') mon from dual connect by rownum <=");
			sql.append(" months_between(to_date('").append(endTime)
					.append("', 'yyyy-MM-dd'),to_date('");
			sql.append(startTime).append("', 'yyyy-MM-dd'))/12) t2");
			sql.append(" where t1.mon(+) = t2.mon order by t2.mon asc");
		}
		System.out.println(sql.toString());
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	public SqlStatement queryTargetsShareLog(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();

		sql.append(" select u.*, nvl(l.c, 0) times, nvl(l.n, 0) nums");
		sql
				.append(" from (select t.service_targets_id,t.service_targets_name,t.service_targets_type from res_service_targets t) u,");
		sql
				.append(" ( select b.service_targets_id, count(1) c, sum(b.record_amount) n");
		sql.append(" from share_log b");
		sql.append(" group by b.service_targets_id) l");
		sql.append(" where u.service_targets_id = l.service_targets_id(+)");
		stmt.addSqlStmt(sql.toString());
		return stmt;

	}

	public SqlStatement queryShareLogUnUse(TxnContext request, DataBus inputData)
			throws TxnException
	{
		DataBus db = request.getRecord("select-key");
		//System.out.println("db---"+db);
		String query_index = db.getValue("query_index");
		String service_targets_id = db.getValue("service_targets_id");
		String service_targets_type = db.getValue("service_targets_type");
		String show_num = StringUtils.isNotBlank(db.getValue("show_num")) ? db
				.getValue("show_num") : "5";
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select * from(select service_targets_name, service_name serv_name, i.service_targets_name||'['||s.service_name||']' service_name, ");
		sql
				.append(" t.last_use_time,t.no_use_months,t.no_use_days from share_log_error_statistics t,share_service s");
		sql
				.append(" , res_service_targets i where t.service_id = s.service_id and s.service_targets_id");
		sql
				.append("  = i.service_targets_id and s.service_state = 'Y' and s.is_markup = 'Y' and t.log_type = 1");
		if (StringUtils.isNotBlank(service_targets_id)) {
			/*String[] str = service_targets_id.split(",");
			String ids = "";
			for (int i = 0; i < str.length; i++) {
				ids += ids == "" ? "'" + str[i] + "'" : ",'" + str[i] + "'";
			}
			sql.append(" and i.SERVICE_TARGETS_TYPE in(").append(ids).append(
					") ");*/
			sql.append(" and i.service_targets_id='").append(service_targets_id).append("'");
		}
		if (StringUtils.isNotBlank(service_targets_type)) {
			
			sql.append(" and i.service_targets_type='").append(service_targets_type).append("'");
		}
		String query_type = "no_use_months";
		if (StringUtils.isNotBlank(query_index)) {
			if (query_index.equals("month")) {
				query_type = "no_use_months";
			} else {
				query_type = "no_use_days";
			}
		}
		sql.append(" order by t.").append(query_type).append(
				" desc,t.no_use_days desc,i.show_order ) where rownum<=")
				.append(show_num);
		stmt.addSqlStmt(sql.toString());
		//System.out.println("未用服务情况统计SQL="+sql.toString());
		return stmt;
	}

	public static void main(String[] args)
	{
		// DateFormat df = new SimpleDateFormat("yyyy-MM");
		// System.out.println(",2013-05-01,".split(",").length);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -3);
		System.out.println(calendar.getTime().toLocaleString());
	}

	public SqlStatement queryShareLogExpetion(TxnContext request,
			DataBus inputData) throws TxnException
	{
		DataBus db = request.getRecord("select-key");
		String query_index = db.getValue("query_index");
		String service_targets_id = db.getValue("service_targets_id");
		String service_targets_type = db.getValue("service_targets_type");
		String show_num = StringUtils.isNotBlank(db.getValue("show_num")) ? db
				.getValue("show_num") : "10";
		String query_date = db.getValue("query_date");
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from(select t.error_code,sum(error_count) error_count from ")
			.append(" share_log_error_statistics t ,res_service_targets r where t.log_type=2")
			.append(" and t.service_targets_id=r.service_targets_id and r.is_markup='Y' and r.is_formal='Y'");
		//System.out.println(query_date + "----" + query_index);
		String[] time = DateUtil.getDateRegionByType(query_date, query_index);
		String startDate = time[0];
		String endDate = time[1];
		sql.append(" and t.log_date>='").append(startDate).append(
				"' and t.log_date <'").append(endDate).append("' ");
		if (StringUtils.isNotBlank(service_targets_id)) {
			sql.append(" and r.service_targets_id='").append(service_targets_id).append("'");
		}
		if (StringUtils.isNotBlank(service_targets_type)) {
			sql.append(" and r.service_targets_type='").append(service_targets_type).append("'");
		}
		sql.append(" group by t.error_code order by 2 desc) where rownum<=")
				.append(show_num);
		stmt.addSqlStmt(sql.toString());
		//System.out.println(sql.toString());
		return stmt;
	}

	public SqlStatement queryShareLogMonthInfo(TxnContext request,
			DataBus inputData) throws TxnException, ParseException
	{
		DataBus db = request.getRecord("select-key");
		String service_targets_id = db.getValue("service_targets_id");
		String query_date = db.getValue("query_date");
		String query_index = db.getValue("query_index");
		String is_last = db.getValue("is_last");
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		// 定义sql的内部
		sql
				.append("select year,month,log_date,sum(t.sum_record_amount) st,sum(t.sum_consume_time) at,sum(t.exec_count) se");
		sql.append(" from share_log_statistics t where 1=1 ");
		if (StringUtils.isNotBlank(service_targets_id)) {
			sql.append(" and t.service_targets_id = '").append(
					service_targets_id).append("' ");
		}
		String[] time = new String[2];
		// 如果为空，是默认时间段查询
		if (StringUtils.isBlank(is_last)) {
			time = DateUtil.getDateRegionByMonth(query_date, query_index);
		}// 如果是0，是查询环比时间段
		else if (is_last.equals("0")) {
			time = DateUtil.getDateMomRegion(query_date, query_index);
			System.out.println("环比：" + time[0] + "---" + time[1]);
		} // 如果是1，是查询同比时间段
		else {
			time = DateUtil.getDateMomYearRegion(query_date, query_index);
			System.out.println("同比：" + time[0] + "---" + time[1]);
		}
		String startDate = time[0];
		String endDate = time[1];
		sql.append("and t.log_date>='").append(startDate).append(
				"' and t.log_date <'").append(endDate).append("' ");
		sql.append("group by year, month, log_date");

		StringBuffer sql_all = new StringBuffer();
		// 如果是月
		if (StringUtils.isBlank(query_index) || query_index.equals("month")) {
			sql_all
					.append("select year,month,sum(st) sum_num,round(sum(at)/sum(se) , 4) avg_time,sum(se) sum_count,");
			sql_all
					.append("count(1) exec_day,round(sum(st) / count(1), 2) avg_num from (");
			sql_all.append(sql).append(") group by year, month");
		} // 如果是年,季度,半年
		else {
			sql_all
					.append("select year,'' month,sum(st) sum_num,round(sum(at)/sum(se) , 4) avg_time,sum(se) sum_count,");
			sql_all
					.append("count(1) exec_day,round(sum(st) / count(1), 2) avg_num from (");
			sql_all.append(sql).append(") group by year");
		}
		System.out.println("sql:" + sql_all.toString());
		stmt.addSqlStmt(sql_all.toString());
		return stmt;
	}

	public SqlStatement queryShareLogMonthMainInfo(TxnContext request,
			DataBus inputData) throws TxnException, ParseException
	{
		DataBus db = request.getRecord("select-key");
		String service_targets_id = db.getValue("service_targets_id");
		String query_date = db.getValue("query_date");
		String query_index = db.getValue("query_index");
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		// 得到查询时间区间
		String[] time = new String[2];
		time = DateUtil.getDateRegionByMonth(query_date, query_index);
		String startDate = time[0];
		String endDate = time[1];
		// sql公用部分
		sql.append("sum(t.sum_record_amount) st,avg(t.avg_consume_time) ");
		sql
				.append("at,sum(t.exec_count) se from share_log_statistics t where 1=1 ");
		if (StringUtils.isNotBlank(service_targets_id)) {
			sql.append(" and t.service_targets_id = '").append(
					service_targets_id).append("' ");
		}
		sql.append("and t.log_date>='").append(startDate).append(
				"' and t.log_date <'").append(endDate).append("' ");

		StringBuffer sql_all = new StringBuffer();
		// 获取连续的右关联时间段
		String sql_right = DateUtil.getTimeRegionSql(query_index, startDate,
				endDate);
		// 如果为月
		if (StringUtils.isBlank(query_index) || query_index.equals("month")) {
			sql_all
					.append(" select b.dd log_date,nvl(a.st,0) st,round(nvl(a.at,0),4) at,nvl(a.se,0) se from(");
			sql_all.append("select year,month,log_date,").append(sql);
			sql_all.append("group by year, month, log_date)a,(");
			sql_all.append(sql_right).append(
					") b where a.log_date(+)=b.dd order by b.dd");
		} else if (query_index.equals("year")) {
			sql_all
					.append(" select b.dd||'月' log_date,nvl(a.st,0) st, round(nvl(a.at,0),4) at,nvl(a.se,0) se from(");
			sql_all.append("select year,month,").append(sql);
			sql_all.append("group by year, month)a,(");
			sql_all.append(sql_right).append(
					") b where a.month(+)=b.dd order by b.dd");
		} else {
			sql_all
					.append(" select b.dd||'月' log_date,nvl(a.st,0) st, round(nvl(a.at,0),4) at,nvl(a.se,0) se from(");
			sql_all.append("select year,month,").append(sql);
			sql_all.append("group by year, month)a,(");
			sql_all.append(sql_right).append(
					") b where a.month(+)=b.dd order by b.dd");
		}
		System.out.println("main sql:" + sql_all.toString());
		stmt.addSqlStmt(sql_all.toString());
		return stmt;
	}

	public SqlStatement queryShareLogSpread(TxnContext request,
			DataBus inputData) throws TxnException
	{
		DataBus db = request.getRecord("select-key");
		String query_date = db.getValue("query_date");
		String query_type = db.getValue("query_type");
		System.out.println("query_date统计指标为=====================" + query_date
				+ "query_type统计指标为" + query_type);
		String time[] = DateUtil.getDateRegionByType(query_date, query_type);

		SqlStatement stmt = new SqlStatement();

		String query_index = db.getValue("query_index");

		StringBuffer sql = new StringBuffer();
		if (StringUtils.isBlank(query_index)) {
			query_index = "type_quantity"; // 默认显示 共享数据量(按类型分布)
		}
		if (StringUtils.isNotBlank(query_index)) {
			if (query_index.equals("type_number")) { // 响应请求次数(按类型分布)
				sql
						.append(" select sum(t1.exec_count) count, t2.service_targets_type name");
				sql
						.append("   from share_log_statistics t1, res_service_targets t2 where t1.service_targets_id =t2.service_targets_id");
				if (StringUtils.isNotBlank(time[0])) {
					sql.append("   and t1.log_date >= '").append(time[0])
							.append("' ");
				}
				if (StringUtils.isNotBlank(time[1])) {
					sql.append(" and t1.log_date< '").append(time[1]).append(
							"' ");
				}

				sql.append("  group by t2.service_targets_type");
			} else if (query_index.equals("type_quantity")) { // 共享数据量(按类型分布)
				sql
						.append(" select sum(t1.sum_record_amount) count, t2.service_targets_type name ");
				sql
						.append("   from share_log_statistics t1, res_service_targets t2 where t1.service_targets_id =t2.service_targets_id");
				if (StringUtils.isNotBlank(time[0])) {
					sql.append("   and t1.log_date >= '").append(time[0])
							.append("' ");
				}
				if (StringUtils.isNotBlank(time[1])) {
					sql.append(" and t1.log_date< '").append(time[1]).append(
							"' ");
				}

				sql
						.append("  group by t2.service_targets_type order by 1 desc");
			} else if (query_index.equals("target_number")) { // 响应请求次数(按服务对象分布)
				sql
						.append(" select sum(t1.exec_count) count, t2.service_targets_name name ");
				sql
						.append("   from share_log_statistics t1, res_service_targets t2 where t1.service_targets_id =t2.service_targets_id");
				if (StringUtils.isNotBlank(time[0])) {
					sql.append("   and t1.log_date >= '").append(time[0])
							.append("' ");
				}
				if (StringUtils.isNotBlank(time[1])) {
					sql.append(" and t1.log_date< '").append(time[1]).append(
							"' ");
				}

				sql
						.append("  group by t2.service_targets_name order by 1 desc");
			} else if (query_index.equals("target_quantity")) { // 共享数据量(按服务对象分布)
				sql
						.append(" select sum(t1.sum_record_amount) count, t2.service_targets_name name ");
				sql
						.append("   from share_log_statistics t1, res_service_targets t2 where t1.service_targets_id =t2.service_targets_id");
				if (StringUtils.isNotBlank(time[0])) {
					sql.append("   and t1.log_date >= '").append(time[0])
							.append("' ");
				}
				if (StringUtils.isNotBlank(time[1])) {
					sql.append(" and t1.log_date< '").append(time[1]).append(
							"' ");
				}

				sql
						.append("  group by t2.service_targets_name order by 1 desc");
			}
		}
		System.out.println(sql.toString());
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	public SqlStatement queryShareLogExpeAndTime(TxnContext request,
			DataBus inputData) throws TxnException, ParseException
	{
		DataBus db = request.getRecord("select-key");
		String service_targets_id = db.getValue("service_targets_id");
		String query_date = db.getValue("query_date");
		String query_index = db.getValue("query_index");
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql
				.append(" with a as(select * from share_log_error_statistics t where t.service_targets_id='");
		sql.append(service_targets_id).append(
				"') select * from(select a.log_type,");
		sql
				.append("a.service_targets_id,min(a.log_date) first_use_time,max(a.last_use_time) last_use_time,0 error_count from a where a.log_type=1");
		sql
				.append("group by a.log_type,a.service_targets_id union all select a.log_type,a.service_targets_id,'',''");
		sql
				.append(",sum(a.error_count) error_count from a where a.log_type=2 ");
		String[] time = new String[2];
		time = DateUtil.getDateRegionByMonth(query_date, query_index);
		String startDate = time[0];
		String endDate = time[1];
		sql.append("and a.log_date>='").append(startDate).append(
				"' and a.log_date <'").append(endDate).append("' ");
		sql.append(" group by a.log_type,a.service_targets_id)");
		System.out.println("expeAndTime sql:" + sql.toString());
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	public SqlStatement getlistserobj(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("select t.service_targets_id as key, t.service_targets_name as title,service_targets_type ");
		sqlBuffer.append("from res_service_targets t where t.is_markup='Y'");

		stmt.addSqlStmt(sqlBuffer.toString()
				+ " order by t.last_modify_time desc");

		return stmt;
	}

	public SqlStatement queryserobj(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		DataBus db = request.getRecord("select-key");

		String service_targets_id = db.getValue("service_targets_id");
		String created_time_start = db.getValue("created_time_start");
		String created_time_end = db.getValue("created_time_end");
		StringBuffer sql = new StringBuffer(
				"select t.* from share_log t where 1=1");
		if (StringUtils.isNotBlank(service_targets_id)) {
			sql.append(" and t.service_targets_id = '" + service_targets_id
					+ "'");
		}

		if (StringUtils.isNotBlank(created_time_start)) {
			sql.append(" and t.service_start_time >= '" + created_time_start
					+ "'");
		}
		if (StringUtils.isNotBlank(created_time_end)) {
			sql.append(" and t.created_time_end <= '" + created_time_end
					+ "'");
		}

		stmt.addSqlStmt(sql.toString() + " order by t.service_start_time desc");
		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
		return stmt;
	}

	public SqlStatement queryMonitorLog(TxnContext request, DataBus inputData)
			throws TxnException, ParseException
	{
		DataBus db = request.getRecord("select-key");
		String objectId = db.getValue("objectId");
		String start_time = db.getValue("startTime");
		String end_time = db.getValue("endTime");
		//System.out.println("db = " + db);
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from monitor_log t where 1=1 ");
		if (StringUtils.isNotBlank(objectId)) {
			sql.append(" and t.object_id = '").append(objectId).append("' ");
		}
		/*if (StringUtils.isNotBlank(propId)) {
			sql.append(" and t.index_name = '").append(propId).append("' ");
		}*/
		if (StringUtils.isNotBlank(start_time)) {
			sql.append(" and t.TASK_START_TIME >= '").append(start_time)
					.append(" 00:00:00' ");
		}
		if (StringUtils.isNotBlank(end_time)) {
			sql.append(" and t.TASK_START_TIME <= '").append(end_time).append(
					" 23:59:59' ");
		}
		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
		stmt.addSqlStmt(sql.toString() + " order by t.task_start_time desc");
		return stmt;
	}

	public SqlStatement getlistmonitorobj(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append("select cd.codename as title, cd.codevalue as key")
					.append(" from codedata cd, ").append("(select svr.")
					.append(column)

					.append(" from monitor_log svr  group by svr.").append(
							column).append(") t where cd.codetype = '").append(
							codeType).append("' and cd.codevalue = t.").append(
							column).append("(+)");
			stmt.addSqlStmt(sqlBuffer.toString());

			// stmt.setCountStmt("select count(*) from (" +
			// sqlBuffer.toString()+ ")");
		}
		return stmt;
	}

	public SqlStatement querymonitor(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		DataBus db = request.getRecord("select-key");

		String object_id = db.getValue("object_id");
		String created_time_start = db.getValue("created_time_start");
		String created_time_end = db.getValue("created_time_end");
		String created_time = db.getValue("created_time");

		StringBuffer sql = new StringBuffer(
				"select t.* from monitor_log t where 1=1");
		if (StringUtils.isNotBlank(object_id)) {
			sql.append(" and t.object_id = '" + object_id + "'");
		}

		if (StringUtils.isNotBlank(created_time_start)) {
			sql
					.append(" and t.task_start_time >= '" + created_time_start
							+ "'");
		}
		if (StringUtils.isNotBlank(created_time_end)) {
			sql.append(" and t.task_start_time <= '" + created_time_end + "'");
		}

		System.out.println(sql.toString());
		stmt.addSqlStmt(sql.toString() + " order by t.task_start_time desc");
		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
		return stmt;
	}

	/**
	 * 查询任务运行状态
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryRunningInfo(TxnContext request, DataBus inputData)
			throws TxnException, ParseException
	{
		SqlStatement stmt = new SqlStatement();
		String target_id = request.getRecord("select-key").getValue(
				"service_targets_id");
		float time = (float) Constants.RUNNING_ALARM_TIME / 24;
		String sql = "select * from (select row_number() over(partition by sl.service_id "
				+ "order by sl.service_start_time desc) sn, "
				+ "sl.service_targets_id, sl.service_id, sl.service_name, 2 share1, "
				+ "sl.service_end_time from share_log sl where sl.service_start_time > "
				+ "to_char(sysdate - "
				+ time
				+ ", 'yyyy-mm-dd HH24:mi:ss') and sl.service_targets_name is not null ";
		if (StringUtils.isNotBlank(target_id)) {
			sql += " and sl.service_targets_id = '" + target_id + "'";
		}
		sql += ") rest "
				+ "where rest.sn < 2 "
				+ "union all select * from (select row_number() over(partition by cj.task_id order  "
				+ "by cj.task_end_time desc) sn,  cj.service_targets_id, cj.task_id, "
				+ "cj.task_name,  1 collect1,  cj.task_end_time  from collect_joumal cj "
				+ "where cj.task_start_time > to_char(sysdate - " + time
				+ ", 'yyyy-mm-dd HH24:mi:ss') " + " and cj.task_name is not null";
		if (StringUtils.isNotBlank(target_id)) {
			sql += " and cj.service_targets_id = '" + target_id + "'";
		}
		sql += " ) rest where rest.sn < 2 ";
		// logger.info("运行任务列表Sql……^…^… \nsql= " + sql);
		//System.out.println("任务运行状态sql="+sql);
		stmt.addSqlStmt(sql);
		// stmt.setCountStmt("select count(1) from (" + sql + ")");
		return stmt;
	}

	
	public SqlStatement queryRunningInfoIndex(TxnContext request, DataBus inputData) throws TxnException, ParseException { 
		SqlStatement stmt = new SqlStatement(); 
		String target_id =request.getRecord("select-key").getValue("service_targets_id"); 
		float time = (float)1.0/24;
		// 开发环境使用 
		String sql = "select * from (select row_number() over(partition by me.object_relate_id " 
			+ "order by me.record_time desc) sn, me.object_target_id service_targets_id, me.object_relate_id service_id, " 
			+ "me.object_type service_type, me.waming_info patameter, me.object_name service_name from monitor_event me "
			+ "where me.record_time > to_char(sysdate - "+time+", 'yyyy-mm-dd hh:mi:ss') " 
			+ "and me.service_targets_name is not null and me.object_name is not null ";
		if (StringUtils.isNotBlank(target_id)) { 
			sql += " and me.object_target_id = '" +target_id + "'"; 
		} 
		sql += ") rest where rest.sn < 2 ";
	  
		stmt.addSqlStmt(sql); 
		  //stmt.setCountStmt("select count(1) from (" + sql +")"); 
		return stmt; 
	  }


	/**
	 * 查询服务对象的任务统计信息
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryRunTaskInfo(TxnContext request, DataBus inputData)
			throws TxnException, ParseException
	{
		SqlStatement stmt = new SqlStatement();
		String sql = "select rest.service_targets_id, rest.service_targets_name, sum(decode(rest.tp, 1, rest.ad, 0 ))"
				+ " colt, sum(decode(rest.tp, 2, rest.ad, 0)) she,  sum(decode(rest.tp, 3, rest.ad, 0 )) etls"
				+ " from (select rst.service_targets_name, "
				+ "rst.service_targets_id, count(sv.service_id) ad, 1 tp from res_service_targets rst, share_service sv "
				+ "where rst.service_targets_id = sv.service_targets_id group by rst.service_targets_id, "
				+ "rst.service_targets_name union all select rst.service_targets_name, rst.service_targets_id,"
				+ " count(ct.collect_task_id) ad, 2 tp from res_service_targets rst, collect_task ct where "
				+ "rst.service_targets_id = ct.service_targets_id group by rst.service_targets_id, rst.service_targets_name"
				+ " union all select rst.service_targets_name, rst.service_targets_id, count(es.etl_id) ad, "
				+ "3 tp from res_service_targets rst, etl_subject es where rst.service_targets_id = es.res_target_id "
				+ "group by rst.service_targets_id, rst.service_targets_name )"
				+ " rest group by rest.service_targets_id, rest.service_targets_name";

		stmt.addSqlStmt(sql);
		// stmt.setCountStmt("select count(1) from (" + sql + ")");
		return stmt;
	}

	/**
	 * 查询服务对象的任务警情信息
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryTaskWamingInfo(TxnContext request,
			DataBus inputData) throws TxnException, ParseException
	{
		SqlStatement stmt = new SqlStatement();
		float time = (float) Constants.RUNNING_ALARM_TIME / 24;
		String sql = "select * from ( "
				+ "select row_number() over (partition by me.object_target_id, me.object_type "
				+ "order by me.record_time desc) sn, me.service_targets_name, me.waming_info,  "
				+ "me.object_type tp, me.object_target_id, me.record_time from monitor_event me "
				+ "where me.waming_state = 0 and me.service_targets_name is not null "
				+ "and me.record_time >= to_char(sysdate - "
				+ time
				+ ", 'yyyy-mm-dd HH24:mi:ss') " + ") rest where rest.sn < 2";

		stmt.addSqlStmt(sql);
		//System.out.println("警情sql="+sql);
		// stmt.setCountStmt("select count(1) from (" + sql + ")");
		return stmt;
	}

	public SqlStatement updateShareLog(TxnContext request, DataBus inputData)
			throws TxnException, ParseException
	{
		SqlStatement stmt = new SqlStatement();

		DataBus db = request.getRecord("record");
		String time_start = db.getValue("created_time_start");
		time_start = time_start + " " + "00:00:00";
		String time_end = db.getValue("created_time_end");
		time_end = time_end + " " + "23:59:59";
		
		StringBuffer sql = new StringBuffer();
		sql
				.append("update share_log t set t.service_state = '02' where t.service_start_time between ");
		sql.append("'");
		sql.append(time_start);
		sql.append("'");
		sql.append(" and ");
		sql.append("'");
		sql.append(time_end);
		sql.append("'");
		sql.append(" and t.service_state is null and t.log_type = '02' and t.service_targets_name is not null");
		//System.out.println("updatesharelog="+sql.toString());
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
	
	/**
	 * 
	 * getTargetInfo 获取服务对象列表做查询条件"
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getTargetInfo(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql.append("with tmp as (select r.service_targets_id   key,")
		.append(" r.service_targets_name title,r.service_targets_type,")
		.append(" show_order from res_service_targets r")
		.append(" where r.is_markup = 'Y' group by r.service_targets_id,") 
		.append(" r.service_targets_type,r.service_targets_name, show_order)") 
		.append(" select * from (select * from tmp where service_targets_type = '000' order by show_order)")            
		.append(" union all select *  from (select * from tmp where service_targets_type <> '000' order by service_targets_type, title)");     
		stmt.addSqlStmt(sql.toString());
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

}
